package inha.capstone.fooda.domain.feed_image.dto;

import inha.capstone.fooda.domain.feed_image.entity.FeedImage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FeedImageDto {
    @Schema(example = "3", description = "피드 이미지 ID")
    private Long id;

    @Schema(example = "3", description = "피드 이미지 URL")
    private String url;

    @Builder
    public FeedImageDto(Long id, String url) {
        this.id = id;
        this.url = url;
    }

    public static FeedImageDto from(FeedImage feedImage) {
        return FeedImageDto.builder()
                .id(feedImage.getId())
                .url(feedImage.getUrl())
                .build();
    }
}
