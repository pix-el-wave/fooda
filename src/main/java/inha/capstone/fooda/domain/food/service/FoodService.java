package inha.capstone.fooda.domain.food.service;

import inha.capstone.fooda.domain.feed.entity.Feed;
import inha.capstone.fooda.domain.feed.repository.FeedRepository;
import inha.capstone.fooda.domain.food.dto.NutrientDto;
import inha.capstone.fooda.domain.food.entity.Food;
import inha.capstone.fooda.domain.food.repository.FoodRepository;
import inha.capstone.fooda.utils.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
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
    public void uploadFood(Long feedId, List<FoodListResDto> foodList) throws IOException {
        Feed feed = feedRepository.getReferenceById(feedId);

        List<Food> foods = foodList.stream()
                .map(dto -> Food.from(feed, dto))
                .toList();

        foodRepository.saveAll(foods);
    }

    public String analyzeFood(Long memberId, LocalDate date) throws IOException {
        String s = date.atStartOfDay().toString();
        String s2 = date.plusDays(1).atStartOfDay().toString();
        NutrientDto now = foodRepository.findSumOfCalDay(memberId, date.atStartOfDay(), date.plusDays(1).atStartOfDay());
        NutrientDto to = NutrientDto.baseNutrient();

        FoodAnalyzeReqDto foodAnalyzeReqDto = FoodAnalyzeReqDto.from(now, to);

        return aiCommunicationUtils.requestFoodAnalyze(foodAnalyzeReqDto).getData().getAnalyze();
    }
}
