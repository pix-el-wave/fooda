package inha.capstone.fooda.domain.feed.dto;

import inha.capstone.fooda.domain.feed.entity.Feed;
import inha.capstone.fooda.utils.FoodListResDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
public class FeedDto {
    @Schema(example = "3", description = "피드 ID")
    private Long id;

    @Schema(description = "생성 날짜")
    private LocalDate createdAt;

    private List<FoodListResDto> foods;

    public static FeedDto from(Feed feed) {
        FeedDto feedDto = new FeedDto();
        feedDto.setId(feed.getId());
        feedDto.setCreatedAt(feed.getCreatedAt().toLocalDate());
        List<FoodListResDto> foodDTOs = feed.getFoods().stream()
                .map(FoodListResDto::from)
                .toList();
        feedDto.setFoods(foodDTOs);

        return feedDto;
    }
}
