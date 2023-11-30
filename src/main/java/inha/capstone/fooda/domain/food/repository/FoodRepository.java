package inha.capstone.fooda.domain.food.repository;

import inha.capstone.fooda.domain.food.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

public interface FoodRepository extends JpaRepository<Food, Long> {

    @Modifying
    @Query("DELETE FROM Food f WHERE f.feed.id = :feedId")
    void deleteByFeedId(@Param("feedId") Long feedId);
}
