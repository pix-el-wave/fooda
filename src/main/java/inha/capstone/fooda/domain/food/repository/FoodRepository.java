package inha.capstone.fooda.domain.food.repository;

import inha.capstone.fooda.domain.food.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Long> {

}
