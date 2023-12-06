package inha.capstone.fooda.domain.food.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class PostNutrientResDto {
    private NutrientDto nutrient;
}
