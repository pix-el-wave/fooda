package inha.capstone.fooda.domain.feed.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor
public class GetSelectFeedAllResDto {

    @Schema(description = "피드에 포함된 음식 목록")
    List<FeedDto> feedDtoList;
}