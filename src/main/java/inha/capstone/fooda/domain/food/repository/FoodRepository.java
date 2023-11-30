package inha.capstone.fooda.domain.food.repository;

import inha.capstone.fooda.domain.feed.entity.Feed;
import inha.capstone.fooda.domain.food.dto.NutrientDto;
import inha.capstone.fooda.domain.food.entity.Food;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {

    @Query(value = "SELECT new inha.capstone.fooda.domain.food.dto.NutrientDto(" +
            "SUM(f.calcium) as calcium, SUM(f.carbs) as carbs, SUM(f.energy) as energy, " +
            "SUM(f.fat) as fat, SUM(f.protein) as protein, SUM(f.salt) as salt) " +
            "FROM Food f " +
            "LEFT JOIN f.feed feed " +
            "LEFT JOIN feed.member member " +
            "WHERE member.id = :memberId " +
            "AND feed.createdAt BETWEEN :startDate AND :endDate")
    public NutrientDto findSumOfCalDay(@Param("memberId") Long memberId,
                                       @Param("startDate") LocalDateTime startDate,
                                       @Param("endDate") LocalDateTime endDate);
}
