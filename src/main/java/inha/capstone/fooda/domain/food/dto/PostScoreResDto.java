package inha.capstone.fooda.domain.food.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class PostScoreResDto {
    private BigDecimal score;
}
