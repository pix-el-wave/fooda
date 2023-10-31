package inha.capstone.fooda.domain.feed.dto;

import inha.capstone.fooda.domain.feed.entity.Feed;
import inha.capstone.fooda.domain.feed_image.dto.FeedImageDto;
import inha.capstone.fooda.domain.feed_image.entity.FeedImage;
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

    @Schema(description = "포함된 영양소")
    private List<FoodListResDto> foods;

    @Schema(description = "포함된 이미지")
    private List<FeedImageDto> feedImages;

    public static FeedDto from(Feed feed) {
        FeedDto feedDto = new FeedDto();
        feedDto.setId(feed.getId());
        feedDto.setCreatedAt(feed.getCreatedAt().toLocalDate());
        List<FoodListResDto> foodDtoList = feed.getFoods().stream()
                .map(FoodListResDto::from)
                .toList();
        feedDto.setFoods(foodDtoList);
        List<FeedImageDto> feedImageDtoList = feed.getFeedImages().stream()
                .map(FeedImageDto::from)
                .toList();
        feedDto.setFeedImages(feedImageDtoList);

        return feedDto;
    }
}
