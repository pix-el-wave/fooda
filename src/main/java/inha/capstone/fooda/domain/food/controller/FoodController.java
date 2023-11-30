package inha.capstone.fooda.domain.food.controller;

import inha.capstone.fooda.domain.common.response.DataResponse;
import inha.capstone.fooda.domain.food.dto.*;
import inha.capstone.fooda.domain.food.service.FoodService;
import inha.capstone.fooda.security.FoodaPrinciple;
import inha.capstone.fooda.utils.FoodAnalyzeResDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;

@Tag(name = "Food")
@RequiredArgsConstructor
@RequestMapping("/api/food")
@RestController
public class FoodController {
    private final FoodService foodService;

    @Operation(
            summary = "음식 목록 기록 API",
            description = "<p>음식 목록을 기록합니다.</p>"
    )
    @PostMapping()
    public ResponseEntity<DataResponse<PostFoodResDto>> food(
            @Parameter(hidden = true) @AuthenticationPrincipal FoodaPrinciple principle,
            @Valid @RequestBody PostFoodReqDto postFoodReqDto
    ) throws IOException {
        foodService.uploadFood(postFoodReqDto.getFeedId(), postFoodReqDto.getFoodList(), principle.getMemberId());

        return new ResponseEntity<>(
                new DataResponse<>(new PostFoodResDto(true)),
                HttpStatus.OK
        );
    }

    @Operation(
            summary = "일일 영양소 분석 API",
            description = "<p>영양소 섭취량과 기준치를 분석하여 제공합니다.</p>"
    )
    @PostMapping("/analyze")
    public ResponseEntity<DataResponse<PostAnalyzeResDto>> analyze(
            @Parameter(hidden = true) @AuthenticationPrincipal FoodaPrinciple principle,
            @Valid @RequestBody PostAnalyzeReqDto postAnalyzeReqDto
    ) throws IOException {
        String analyze = foodService.analyzeFood(principle.getMemberId(), postAnalyzeReqDto.getDate());

        return new ResponseEntity<>(
                new DataResponse<>(new PostAnalyzeResDto(analyze)),
                HttpStatus.OK
        );
    }

    @Operation(
            summary = "그날 먹은 영양소 API",
            description = "<p>그날 먹은 영양소 섭취량은 반환합니다.</p>"
    )
    @PostMapping("/nutrient")
    public ResponseEntity<DataResponse<PostNutrientResDto>> nutrient(
            @Parameter(hidden = true) @AuthenticationPrincipal FoodaPrinciple principle,
            @Valid @RequestBody PostNutrientReqDto postAnalyzeReqDto
    ) throws IOException {
        NutrientDto nutrientDto = foodService.nutrient(principle.getMemberId(), postAnalyzeReqDto.getDate());

        return new ResponseEntity<>(
                new DataResponse<>(new PostNutrientResDto(nutrientDto)),
                HttpStatus.OK
        );
    }

    @Operation(
            summary = "영양소 점수 API",
            description = "<p>영양소 섭취량에 따른 점수를 제공합니다.</p>"
    )
    @PostMapping("/score")
    public ResponseEntity<DataResponse<PostScoreResDto>> score(
            @Parameter(hidden = true) @AuthenticationPrincipal FoodaPrinciple principle,
            @Valid @RequestBody PostScoreReqDto postAnalyzeReqDto
    ) throws IOException {
        BigDecimal score = foodService.score(principle.getMemberId(), postAnalyzeReqDto.getDate());

        return new ResponseEntity<>(
                new DataResponse<>(new PostScoreResDto(score)),
                HttpStatus.OK
        );
    }
}