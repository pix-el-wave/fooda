package inha.capstone.fooda.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FoodListResDto {
    private Integer no;
    private String name;
    private Integer size;
    private BigDecimal energy;
    private BigDecimal protein;
    private BigDecimal fat;
    private BigDecimal carbs;
    private BigDecimal calcium;
    private BigDecimal salt;
}
