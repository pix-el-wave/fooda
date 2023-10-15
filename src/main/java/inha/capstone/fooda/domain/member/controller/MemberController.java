package inha.capstone.fooda.domain.member.controller;

import inha.capstone.fooda.domain.common.response.DataResponse;
import inha.capstone.fooda.domain.member.dto.GetFindProfileInfoMemberResDto;
import inha.capstone.fooda.domain.member.dto.MemberDto;
import inha.capstone.fooda.domain.member.dto.PostModifyInfoMemberReqDto;
import inha.capstone.fooda.domain.member.dto.PostModifyInfoMemberResDto;
import inha.capstone.fooda.domain.member.service.MemberService;
import inha.capstone.fooda.security.FoodaPrinciple;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Member")
@RequiredArgsConstructor
@RequestMapping("/api/member")
@RestController
public class MemberController {
    private final MemberService memberService;

    @Operation(
            summary = "사용자 프로필 편집 화면 정보 조회 API",
            description = "<p>로그인한 사용자의 프로필 편집 화면의 정보를 조회합니다.</p>"
    )
    @GetMapping(value = "/profile/settings")
    public ResponseEntity<DataResponse<GetFindProfileInfoMemberResDto>> findInfo(
            @Parameter(hidden = true) @AuthenticationPrincipal FoodaPrinciple principle
    ) {
        MemberDto memberDto = memberService.findMemberByUsername(principle.getUsername());

        GetFindProfileInfoMemberResDto response = GetFindProfileInfoMemberResDto.from(memberDto);

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
        MemberDto memberDto = memberService.modifyMemberByUsername(principle.getUsername(), postModifyInfoMemberReqDto.toDto());

        PostModifyInfoMemberResDto response = PostModifyInfoMemberResDto.from(memberDto);

        return new ResponseEntity<>(
                new DataResponse<>(response),
                HttpStatus.OK
        );
    }
}
