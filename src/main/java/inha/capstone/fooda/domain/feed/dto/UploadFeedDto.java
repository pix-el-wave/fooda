package inha.capstone.fooda.domain.feed.dto;

import inha.capstone.fooda.utils.FoodListResDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class UploadFeedDto {
    private Long feedId;
    private List<FoodListResDto> foodList;
}
