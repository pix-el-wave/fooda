package inha.capstone.fooda.domain.food.entity;

import inha.capstone.fooda.domain.common.entity.BaseEntity;
import inha.capstone.fooda.domain.feed.entity.Feed;
import inha.capstone.fooda.domain.member.dto.GetFindProfileInfoMemberResDto;
import inha.capstone.fooda.domain.member.dto.MemberDto;
import inha.capstone.fooda.utils.FoodListResDto;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id")
    private Feed feed;

    @Column(nullable = false)
    private String foodName;

    @Column(nullable = false)
    private BigDecimal energy;

    @Column(nullable = false)
    private BigDecimal carbs;

    @Column(nullable = false)
    private BigDecimal protein;

    @Column(nullable = false)
    private BigDecimal fat;

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

    @Builder
    public Food(Long id, Feed feed, String foodName, BigDecimal energy, BigDecimal carbs, BigDecimal protein, BigDecimal fat) {
        this.id = id;
        this.feed = feed;
        this.foodName = foodName;
        this.energy = energy;
        this.carbs = carbs;
        this.protein = protein;
        this.fat = fat;
    }

    public static Food from(Feed feed, FoodListResDto foodListResDto) {
        return Food.builder()
                .feed(feed)
                .foodName(foodListResDto.getName())
                .energy(foodListResDto.getEnergy())
                .carbs(foodListResDto.getCarbs())
                .protein(foodListResDto.getProtein())
                .fat(foodListResDto.getFat())
                .build();
    }
}
