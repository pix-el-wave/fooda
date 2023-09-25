package inha.capstone.fooda.domain.food;

import inha.capstone.fooda.domain.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Food extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal kcal;

    @Column(nullable = false)
    private BigDecimal carbs;

    @Column(nullable = false)
    private BigDecimal protein;

    @Column(nullable = false)
    private BigDecimal fat;

    @Column(nullable = false)
    private BigDecimal vitaminA;

    @Column(nullable = false)
    private BigDecimal vitaminB1;

    @Column(nullable = false)
    private BigDecimal vitaminB2;

    @Column(nullable = false)
    private BigDecimal vitaminB12;

    @Column(nullable = false)
    private BigDecimal vitaminC;

    @Builder
    public Food(String name, BigDecimal kcal, BigDecimal carbs, BigDecimal protein, BigDecimal fat, BigDecimal vitaminA, BigDecimal vitaminB1, BigDecimal vitaminB2, BigDecimal vitaminB12, BigDecimal vitaminC) {
        this.name = name;
        this.kcal = kcal;
        this.carbs = carbs;
        this.protein = protein;
        this.fat = fat;
        this.vitaminA = vitaminA;
        this.vitaminB1 = vitaminB1;
        this.vitaminB2 = vitaminB2;
        this.vitaminB12 = vitaminB12;
        this.vitaminC = vitaminC;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Food)) return false;
        Food that = (Food) o;
        return this.getId() != null && this.getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
