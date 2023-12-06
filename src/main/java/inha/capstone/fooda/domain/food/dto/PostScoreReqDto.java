package inha.capstone.fooda.domain.food.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostScoreReqDto {

    @NotNull(message = "영양소 분석 날짜를 보내주세요.")
    @Schema(example = "2020-03-23", description = "영양소 분석할 날짜")
    private LocalDate date;
}
