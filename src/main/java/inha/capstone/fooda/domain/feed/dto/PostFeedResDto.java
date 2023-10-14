package inha.capstone.fooda.domain.feed.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class PostFeedResDto {
    @Schema(example = "ok", description = "성공")
    private String ok;
}
