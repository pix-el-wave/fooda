package inha.capstone.fooda.domain.feed.dto;

import inha.capstone.fooda.domain.feed.entity.Feed;
import inha.capstone.fooda.domain.feed_image.dto.FeedImageDto;
import inha.capstone.fooda.domain.feed_image.entity.FeedImage;
import inha.capstone.fooda.utils.FoodListResDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class FeedDto {
    @Schema(example = "3", description = "피드 ID")
    private Long id;

    @Schema(example = "26", description = "좋아요 수")
    private Long likeCount;

    @Schema(description = "피드 작성자 별명")
    private String name;

    @Schema(description = "피드 작성자 ID")
    private String username;

    @Schema(description = "생성 날짜")
    private LocalDateTime createdAt;

    @Schema(description = "포함된 영양소")
    private List<FoodListResDto> foods;

    @Schema(description = "포함된 이미지")
    private List<FeedImageDto> feedImages;

    @Builder
    public FeedDto(Long id, Long likeCount, LocalDateTime createdAt, List<FoodListResDto> foods, List<FeedImageDto> feedImages, String name, String username) {
        this.id = id;
        this.likeCount = likeCount;
        this.createdAt = createdAt;
        this.foods = foods;
        this.feedImages = feedImages;
        this.name = name;
        this.username = username;
    }

    public static FeedDto from(Feed feed) {
        return FeedDto.builder()
                .id(feed.getId())
                .createdAt(feed.getCreatedAt())
                .foods(feed.getFoods().stream()
                        .map(FoodListResDto::from)
                        .toList())
                .feedImages(feed.getFeedImages().stream()
                        .map(FeedImageDto::from)
                        .toList())
                .likeCount(feed.getLikeCount())
                .name(feed.getMember().getName())
                .username(feed.getMember().getUsername())
                .build();
    }
}
