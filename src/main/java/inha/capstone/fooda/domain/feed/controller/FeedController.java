package inha.capstone.fooda.domain.feed.controller;

import inha.capstone.fooda.domain.common.response.DataResponse;
import inha.capstone.fooda.domain.feed.dto.*;
import inha.capstone.fooda.domain.feed.service.FeedService;
import inha.capstone.fooda.security.FoodaPrinciple;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Tag(name = "Feed")
@RequiredArgsConstructor
@RequestMapping("/api/feed")
@RestController
public class FeedController {
    private final FeedService feedService;

    @Operation(
            summary = "음식 기록 API",
            description = "<p>음식을 기록합니다.</p>"
    )
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DataResponse<PostFeedResDto>> feed(
            @Parameter(hidden = true) @AuthenticationPrincipal FoodaPrinciple principle,
            @Valid PostFeedReqDto postFeedReqDto
    ) throws IOException {
        UploadFeedDto uploadFeedDto = feedService.uploadFeed(principle.getMemberId(), postFeedReqDto.getOpen(), postFeedReqDto.getMeal(), postFeedReqDto.getImg());
        return new ResponseEntity<>(
                new DataResponse<>(new PostFeedResDto(uploadFeedDto)),
                HttpStatus.OK
        );
    }

    @Operation(
            summary = "음식 조회 API",
            description = "<p>음식을 조회합니다.</p>"
    )
    @PostMapping("/list")
    public ResponseEntity<DataResponse<PostSelectFeedResDto>> selectFeed(
            @Parameter(hidden = true) @AuthenticationPrincipal FoodaPrinciple principle,
            @Valid @RequestBody PostSelectFeedReqDto postSelectFeedReqDto
    ) throws IOException {
        List<FeedDto> feedDtoList = feedService.selectFeed(principle.getMemberId(), postSelectFeedReqDto.getStart(), postSelectFeedReqDto.getEnd());
        return new ResponseEntity<>(
                new DataResponse<>(new PostSelectFeedResDto(feedDtoList)),
                HttpStatus.OK
        );
    }

    @Operation(
            summary = "피드 조회 API",
            description = "<p>피드을 조회합니다.</p>"
    )
    @GetMapping("/list/following")
    public ResponseEntity<DataResponse<GetSelectFeedFollowingResDto>> selectFeedFollowing(
            @Parameter(hidden = true) @AuthenticationPrincipal FoodaPrinciple principle
    ) throws IOException {
        List<FeedDto> feedDtoList = feedService.selectFeedByFollowing(principle.getMemberId());
        return new ResponseEntity<>(
                new DataResponse<>(new GetSelectFeedFollowingResDto(feedDtoList)),
                HttpStatus.OK
        );
    }
}