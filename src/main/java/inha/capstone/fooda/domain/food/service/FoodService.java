package inha.capstone.fooda.domain.food.service;

import inha.capstone.fooda.domain.feed.entity.Feed;
import inha.capstone.fooda.domain.feed.repository.FeedRepository;
import inha.capstone.fooda.domain.food.entity.Food;
import inha.capstone.fooda.domain.food.repository.FoodRepository;
import inha.capstone.fooda.utils.FoodListResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FoodService {
    private final FoodRepository foodRepository;
    private final FeedRepository feedRepository;

    @Transactional
    public void uploadFood(Long feedId, List<FoodListResDto> foodList) throws IOException {
        Feed feed = feedRepository.getReferenceById(feedId);

        List<Food> foods = foodList.stream()
                .map(dto -> Food.from(feed, dto))
                .toList();

        foodRepository.saveAll(foods);
    }
}
