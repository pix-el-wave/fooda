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

    @Schema(example = "[{\"no\":1,\"name\":\"Pizza\",\"size\":200,\"energy\":250.5,\"protein\":10.2,\"province\":5.5,\"carbohydrate\":30.0,\"calcium\":50.2,\"salt\":1.5}, {\"no\":2,\"name\":\"Burger\",\"size\":150,\"energy\":300.0,\"protein\":12.5,\"province\":6.0,\"carbohydrate\":25.0,\"calcium\":40.5,\"salt\":1.8}]", description = "이미지에서 추출된 음식 목록")
    List<FoodListResDto> foodList;
}
