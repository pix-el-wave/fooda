package inha.capstone.fooda.domain.food.controller;

import inha.capstone.fooda.domain.common.response.DataResponse;
import inha.capstone.fooda.domain.food.dto.PostAnalyzeReqDto;
import inha.capstone.fooda.domain.food.dto.PostAnalyzeResDto;
import inha.capstone.fooda.domain.food.dto.PostFoodReqDto;
import inha.capstone.fooda.domain.food.dto.PostFoodResDto;
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
        foodService.uploadFood(postFoodReqDto.getFeedId(), postFoodReqDto.getFoodList());

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
}