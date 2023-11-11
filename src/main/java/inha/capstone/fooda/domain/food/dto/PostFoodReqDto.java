package inha.capstone.fooda.domain.food.dto;

import inha.capstone.fooda.utils.FoodListResDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostFoodReqDto {

    @NotNull(message = "업데이트 할 피드 Id를 보내주세요.")
    @Schema(example = "3", description = "업데이트 할 피드의 Id")
    private Long feedId;

    List<FoodListResDto> foodList;
}
