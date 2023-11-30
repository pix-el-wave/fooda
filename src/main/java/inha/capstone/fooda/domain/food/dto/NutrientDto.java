package inha.capstone.fooda.domain.food.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Optional;

@Getter
public class NutrientDto {
    private BigDecimal calcium;
    private BigDecimal carbs;
    private BigDecimal energy;
    private BigDecimal fat;
    private BigDecimal protein;
    private BigDecimal salt;

    public NutrientDto(BigDecimal calcium, BigDecimal carbs, BigDecimal energy, BigDecimal fat, BigDecimal protein, BigDecimal salt) {
        this.calcium = Optional.ofNullable(calcium).orElse(BigDecimal.ZERO);
        this.carbs = Optional.ofNullable(carbs).orElse(BigDecimal.ZERO);
        this.energy = Optional.ofNullable(energy).orElse(BigDecimal.ZERO);
        this.fat = Optional.ofNullable(fat).orElse(BigDecimal.ZERO);
        this.protein = Optional.ofNullable(protein).orElse(BigDecimal.ZERO);
        this.salt = Optional.ofNullable(salt).orElse(BigDecimal.ZERO);
    }
    public static NutrientDto baseNutrient() {
        return new NutrientDto(new BigDecimal("700.0"), new BigDecimal("324.0"), new BigDecimal("1800.0"), new BigDecimal("54.0"), new BigDecimal("55.0"), new BigDecimal("2000.0"));
    }
}
