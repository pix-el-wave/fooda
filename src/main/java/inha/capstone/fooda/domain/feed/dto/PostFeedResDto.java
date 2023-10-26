package inha.capstone.fooda.domain.feed.dto;

import inha.capstone.fooda.utils.FoodListResDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class PostFeedResDto {

    @Schema(example = "3", description = "업로드한 피드의 ID")
    private Long feedId;

    List<FoodListResDto> foodList;

    public PostFeedResDto(UploadFeedDto uploadFeedDto) {
        this.feedId = uploadFeedDto.getFeedId();
        this.foodList = uploadFeedDto.getFoodList();
    }
}
