package inha.capstone.fooda.domain.friend.controller;

import inha.capstone.fooda.domain.common.response.DataResponse;
import inha.capstone.fooda.domain.friend.dto.GetFindFriendInfoResDto;
import inha.capstone.fooda.domain.friend.service.FriendService;
import inha.capstone.fooda.domain.member.dto.MemberDto;
import inha.capstone.fooda.security.FoodaPrinciple;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Friend")
@RequiredArgsConstructor
@RequestMapping("/api/friend")
@RestController
public class FriendController {
    private final FriendService friendService;

    @Operation(
            summary = "사용자 팔로워, 팔로잉 목록 조회 API",
            description = "<p>로그인한 사용자의 팔로워, 팔로잉 목록을 조회합니다.</p>"
    )
    @GetMapping(value = "/friend")
    public ResponseEntity<DataResponse<GetFindFriendInfoResDto>> findFriendInfo(
            @Parameter(hidden = true) @AuthenticationPrincipal FoodaPrinciple principle
    ) {
        List<MemberDto> followingMembers = friendService.findFollowingMembers(principle.getUsername());
        List<MemberDto> followerMembers = friendService.findFollowerMembers(principle.getUsername());

        GetFindFriendInfoResDto response = GetFindFriendInfoResDto.from(followingMembers, followerMembers);

        return new ResponseEntity<>(
                new DataResponse<>(response),
                HttpStatus.OK
        );
    }
}
