package inha.capstone.fooda.utils;

import inha.capstone.fooda.domain.food.entity.Food;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FoodListResDto {
    @Schema(example = "Pizza", description = "음식 이름")
    private String name;
    @Schema(example = "980", description = "칼로리")
    private BigDecimal energy;
    @Schema(example = "9.4", description = "단백질")
    private BigDecimal protein;
    @Schema(example = "3.4", description = "지방")
    private BigDecimal fat;
    @Schema(example = "14.9", description = "탄수화물")
    private BigDecimal carbs;
    @Schema(example = "3", description = "칼슘")
    private BigDecimal calcium;
    @Schema(example = "6", description = "나트륨")
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
