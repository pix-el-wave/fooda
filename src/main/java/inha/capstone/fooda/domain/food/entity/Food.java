package inha.capstone.fooda.domain.food.entity;

import inha.capstone.fooda.domain.common.entity.BaseEntity;
import inha.capstone.fooda.domain.feed.entity.Feed;
import inha.capstone.fooda.utils.FoodListResDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Food extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id")
    private Feed feed;

    @Column(name = "food_name", nullable = false)
    private String foodName;

    @Column(name = "food_energy", nullable = false)
    private BigDecimal energy;

    @Column(name = "food_carbs", nullable = false)
    private BigDecimal carbs;

    @Column(name = "food_protein", nullable = false)
    private BigDecimal protein;

    @Column(name = "food_fat", nullable = false)
    private BigDecimal fat;

    @Column(name = "food_calcium", nullable = false)
    private BigDecimal calcium;

    @Column(name = "food_salt", nullable = false)
    private BigDecimal salt;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Food)) {
            return false;
        }
        Food that = (Food) o;
        return this.getId() != null && this.getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

    @Builder
    public Food(Long id, Feed feed, String foodName, BigDecimal energy, BigDecimal carbs, BigDecimal protein,
                BigDecimal fat, BigDecimal calcium, BigDecimal salt) {
        this.id = id;
        this.feed = feed;
        this.foodName = foodName;
        this.energy = energy;
        this.carbs = carbs;
        this.protein = protein;
        this.fat = fat;
        this.calcium = calcium;
        this.salt = salt;
    }

    public static Food from(Feed feed, FoodListResDto foodListResDto) {
        return Food.builder()
                .feed(feed)
                .foodName(foodListResDto.getName())
                .energy(foodListResDto.getEnergy())
                .carbs(foodListResDto.getCarbs())
                .protein(foodListResDto.getProtein())
                .fat(foodListResDto.getFat())
                .calcium(foodListResDto.getCalcium())
                .salt(foodListResDto.getSalt())
                .build();
    }
}
