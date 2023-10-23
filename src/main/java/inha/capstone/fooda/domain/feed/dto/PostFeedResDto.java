package inha.capstone.fooda.domain.feed.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class PostFeedResDto {
    @Schema(example = "3", description = "업로드한 피드의 ID")
    private Long id;
}
