package inha.capstone.fooda.utils;

import inha.capstone.fooda.domain.food.dto.NutrientDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class FoodAnalyzeReqDto {
    private BigDecimal calcium0;
    private BigDecimal carbs0;
    private BigDecimal energy0;
    private BigDecimal fat0;
    private BigDecimal protein0;
    private BigDecimal salt0;
    private BigDecimal calcium1;
    private BigDecimal carbs1;
    private BigDecimal energy1;
    private BigDecimal fat1;
    private BigDecimal protein1;
    private BigDecimal salt1;

    public static FoodAnalyzeReqDto from(NutrientDto now, NutrientDto to) {
        return new FoodAnalyzeReqDto(now.getCalcium(), now.getCarbs(), now.getEnergy(), now.getFat(), now.getProtein(), now.getSalt(),
                                    to.getCalcium(), to.getCarbs(), to.getEnergy(), to.getFat(), to.getProtein(), to.getSalt());
    }
}