package inha.capstone.fooda.domain.food.service;

import inha.capstone.fooda.domain.feed.entity.Feed;
import inha.capstone.fooda.domain.feed.repository.FeedRepository;
import inha.capstone.fooda.domain.food.dto.NutrientDto;
import inha.capstone.fooda.domain.food.entity.Food;
import inha.capstone.fooda.domain.food.exception.NotFoundFeedException;
import inha.capstone.fooda.domain.food.repository.FoodRepository;
import inha.capstone.fooda.domain.food.exception.MemberNotEqualFeedMemberException;
import inha.capstone.fooda.domain.member.entity.Gender;
import inha.capstone.fooda.domain.member.entity.Member;
import inha.capstone.fooda.domain.member.repository.MemberRepository;
import inha.capstone.fooda.utils.FoodListResDto;
import inha.capstone.fooda.utils.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FoodService {
    private final FoodRepository foodRepository;
    private final FeedRepository feedRepository;
    private final MemberRepository memberRepository;
    private final AICommunicationUtils aiCommunicationUtils;

    @Transactional
    public void uploadFood(Long feedId, List<FoodListResDto> foodList, Long memberId) throws IOException {
        try {
            Feed feed = feedRepository.getReferenceById(feedId);
            if (!feed.getMember().getId().equals(memberId)) {
                throw new MemberNotEqualFeedMemberException(memberId + "는 피드 작성자 와 달라서 접근할 수 없습니다.");
            }
            List<Food> foods = foodList.stream()
                    .map(dto -> Food.from(feed, dto))
                    .toList();

            foodRepository.deleteByFeedId(feed.getId());
            foodRepository.saveAll(foods);
        } catch (EntityNotFoundException e) {
            throw new NotFoundFeedException(feedId + "인 피드가 존재하지 않습니다.");
        }
    }

    public String analyzeFood(Long memberId, LocalDate date) throws IOException {
        NutrientDto now = foodRepository.findSumOfCalDay(memberId, date.atStartOfDay(), date.plusDays(1).atStartOfDay());

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new UsernameNotFoundException("유저가 존재하지 않습니다."));

        // 일일 에너지 요구량 계산
        double bmr = 0;
        if (member.getGender().equals(Gender.MALE)) {
            bmr = 10 * member.getWeight() + 6.25 * member.getHeight() - 5 * member.getAge() + 5;
        } else {
            bmr = 10 * member.getWeight() + 6.25 * member.getHeight() - 5 * member.getAge() - 161;
        }

        // 예를 들어, 활동 수준을 중간으로 가정 (1.55)
        double dailyCalories = bmr * 1.55;

        // 영양소 권장량 계산
        double protein = member.getWeight() * 0.8; // 단백질: 체중 1kg 당 0.8g
        double fat = dailyCalories * 0.25 / 9; // 지방: 총 칼로리의 25%
        double carbs = dailyCalories * 0.5 / 4; // 탄수화물: 총 칼로리의 50%
        double calcium = 1000; // 칼슘: 일반적으로 1000mg
        double salt = 6; // 소금: 하루 6g 미만

        NutrientDto to = NutrientDto.builder()
            .calcium(BigDecimal.valueOf(calcium))
            .carbs(BigDecimal.valueOf(carbs))
            .energy(BigDecimal.valueOf(dailyCalories))
            .fat(BigDecimal.valueOf(fat))
            .protein(BigDecimal.valueOf(protein))
            .salt(BigDecimal.valueOf(salt))
            .build();

        FoodAnalyzeReqDto foodAnalyzeReqDto = FoodAnalyzeReqDto.from(now, to);

        return aiCommunicationUtils.requestFoodAnalyze(foodAnalyzeReqDto).getData().getAnalyze();
    }

    public BigDecimal score(Long memberId, LocalDate date) throws IOException {
        NutrientDto now = foodRepository.findSumOfCalDay(memberId, date.atStartOfDay(), date.plusDays(1).atStartOfDay());
        NutrientDto to = NutrientDto.baseNutrient();


        BigDecimal score = getScore(now, to);

        return score.setScale(2, RoundingMode.HALF_UP).max(BigDecimal.ZERO);
    }

    private static BigDecimal getScore(NutrientDto now, NutrientDto to) {
        BigDecimal calciumDiff = now.getCalcium().subtract(to.getCalcium()).pow(2);
        BigDecimal carbsDiff = now.getCarbs().subtract(to.getCarbs()).pow(2);
        BigDecimal energyDiff = now.getEnergy().subtract(to.getEnergy()).pow(2);
        BigDecimal fatDiff = now.getFat().subtract(to.getFat()).pow(2);
        BigDecimal proteinDiff = now.getProtein().subtract(to.getProtein()).pow(2);
        BigDecimal saltDiff = now.getSalt().subtract(to.getSalt()).pow(2);

        BigDecimal sumOfSquares = calciumDiff.add(carbsDiff).add(energyDiff).add(fatDiff).add(proteinDiff).add(saltDiff);

        return BigDecimal.valueOf(100).subtract(BigDecimal.valueOf(0.06435).multiply(sumOfSquares.sqrt(new MathContext(4))));
    }
}
