package inha.capstone.fooda.domain.member.controller;

import inha.capstone.fooda.domain.common.response.DataResponse;
import inha.capstone.fooda.domain.feed.service.FeedService;
import inha.capstone.fooda.domain.feed_image.dto.FeedImageDto;
import inha.capstone.fooda.domain.feed_image.service.FeedImageService;
import inha.capstone.fooda.domain.friend.service.FriendService;
import inha.capstone.fooda.domain.member.dto.GetFindInfoMemberResDto;
import inha.capstone.fooda.domain.member.dto.GetFindProfileInfoMemberResDto;
import inha.capstone.fooda.domain.member.dto.MemberDto;
import inha.capstone.fooda.domain.member.dto.PostModifyInfoMemberReqDto;
import inha.capstone.fooda.domain.member.dto.PostModifyInfoMemberResDto;
import inha.capstone.fooda.domain.member.service.MemberService;
import inha.capstone.fooda.security.FoodaPrinciple;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Member")
@RequiredArgsConstructor
@RequestMapping("/api/member")
@RestController
public class MemberController {
    private final MemberService memberService;
    private final FeedService feedService;
    private final FeedImageService feedImageService;
    private final FriendService friendService;

    @Operation(
            summary = "사용자 프로필 화면 정보 조회 API",
            description = "<p>로그인한 사용자의 프로필 화면의 정보를 조회합니다.</p>"
    )
    @GetMapping(value = "/profile")
    public ResponseEntity<DataResponse<GetFindProfileInfoMemberResDto>> findProfileInfo(
            @Parameter(hidden = true) @AuthenticationPrincipal FoodaPrinciple principle
    ) {
        MemberDto memberDto = memberService.findMemberByUsername(principle.getUsername());
        Long feedCount = feedService.countFeed(principle.getUsername());
        Long followerMemberCount = friendService.findFollowerMemberCount(principle.getUsername());
        Long followingMemberCount = friendService.findFollowingMemberCount(principle.getUsername());
        List<FeedImageDto> feedImageList = feedImageService.getFirstFeedImageList(principle.getUsername());

        GetFindProfileInfoMemberResDto response = GetFindProfileInfoMemberResDto.from(
                memberDto.getName(),
                feedCount,
                followerMemberCount,
                followingMemberCount,
                feedImageList);

        return new ResponseEntity<>(
                new DataResponse<>(response),
                HttpStatus.OK
        );
    }

    @Operation(
            summary = "사용자 프로필 편집 화면 정보 조회 API",
            description = "<p>로그인한 사용자의 프로필 편집 화면의 정보를 조회합니다.</p>"
    )
    @GetMapping(value = "/profile/settings")
    public ResponseEntity<DataResponse<GetFindInfoMemberResDto>> findInfo(
            @Parameter(hidden = true) @AuthenticationPrincipal FoodaPrinciple principle
    ) {
        MemberDto memberDto = memberService.findMemberByUsername(principle.getUsername());

        GetFindInfoMemberResDto response = GetFindInfoMemberResDto.from(memberDto);

        return new ResponseEntity<>(
                new DataResponse<>(response),
                HttpStatus.OK
        );
    }

    @Operation(
            summary = "사용자 프로필 편집 화면 정보 변경 API",
            description = "<p>로그인한 사용자의 프로필 편집 화면의 정보를 변경합니다.</p>"
    )
    @PostMapping(value = "/profile/settings")
    public ResponseEntity<DataResponse<PostModifyInfoMemberResDto>> modifyInfo(
            @Validated @RequestBody PostModifyInfoMemberReqDto postModifyInfoMemberReqDto,
            @Parameter(hidden = true) @AuthenticationPrincipal FoodaPrinciple principle
    ) {
        MemberDto memberDto = memberService.modifyMemberByUsername(principle.getUsername(),
                postModifyInfoMemberReqDto.toDto());

        PostModifyInfoMemberResDto response = PostModifyInfoMemberResDto.from(memberDto);

        return new ResponseEntity<>(
                new DataResponse<>(response),
                HttpStatus.OK
        );
    }
}
