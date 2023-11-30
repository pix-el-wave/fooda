package inha.capstone.fooda.domain.food.service;

import inha.capstone.fooda.domain.feed.entity.Feed;
import inha.capstone.fooda.domain.feed.repository.FeedRepository;
import inha.capstone.fooda.domain.food.dto.NutrientDto;
import inha.capstone.fooda.domain.food.entity.Food;
import inha.capstone.fooda.domain.food.repository.FoodRepository;
import inha.capstone.fooda.domain.food.exception.MemberNotEqualFeedMemberException;
import inha.capstone.fooda.utils.FoodListResDto;
import inha.capstone.fooda.utils.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FoodService {
    private final FoodRepository foodRepository;
    private final FeedRepository feedRepository;
    private final AICommunicationUtils aiCommunicationUtils;

    @Transactional
    public void uploadFood(Long feedId, List<FoodListResDto> foodList, Long memberId) throws IOException {
        Feed feed = feedRepository.getReferenceById(feedId);

        if (!feed.getMember().getId().equals(memberId)) {
            throw new MemberNotEqualFeedMemberException(memberId + "는 피드 작성자 와 달라서 접근할 수 없습니다.");
        }

        List<Food> foods = foodList.stream()
                .map(dto -> Food.from(feed, dto))
                .toList();

        foodRepository.deleteByFeedId(feed.getId());
        foodRepository.saveAll(foods);
    }

    public String analyzeFood(Long memberId, LocalDate date) throws IOException {
        NutrientDto now = foodRepository.findSumOfCalDay(memberId, date.atStartOfDay(), date.plusDays(1).atStartOfDay());
        NutrientDto to = NutrientDto.baseNutrient();

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
