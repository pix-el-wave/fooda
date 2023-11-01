package inha.capstone.fooda.utils;

import inha.capstone.fooda.domain.food.entity.Food;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FoodListResDto {
    private String name;
    private BigDecimal energy;
    private BigDecimal protein;
    private BigDecimal fat;
    private BigDecimal carbs;
    private BigDecimal calcium;
    private BigDecimal salt;

    public static FoodListResDto from(Food food) {
        FoodListResDto dto = new FoodListResDto();
        dto.setName(food.getFoodName());
        dto.setEnergy(food.getEnergy());
        dto.setProtein(food.getProtein());
        dto.setFat(food.getFat());
        dto.setCarbs(food.getCarbs());
        dto.setCalcium(food.getCalcium());
        dto.setSalt(food.getSalt());
        return dto;
    }
}
