package inha.capstone.fooda.domain.food.controller;

import inha.capstone.fooda.domain.common.response.DataResponse;
import inha.capstone.fooda.domain.food.dto.PostFoodReqDto;
import inha.capstone.fooda.domain.food.dto.PostFoodResDto;
import inha.capstone.fooda.domain.food.service.FoodService;
import inha.capstone.fooda.security.FoodaPrinciple;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}