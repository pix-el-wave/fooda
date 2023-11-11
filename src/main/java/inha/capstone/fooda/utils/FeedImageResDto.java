package inha.capstone.fooda.utils;

import inha.capstone.fooda.domain.feed_image.dto.FeedImageDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FeedImageResDto {
    @Schema(example = "3", description = "피드 이미지 ID")
    private Long id;

    @Schema(example = "3", description = "피드 이미지 URL")
    private String url;

    public static FeedImageResDto from(FeedImageDto feedImageDto) {
        return new FeedImageResDto(feedImageDto.getId(), feedImageDto.getUrl());
    }
}
