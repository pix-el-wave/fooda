package inha.capstone.fooda.domain.member.controller;

import inha.capstone.fooda.domain.common.response.DataResponse;
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

    @Operation(
            summary = "사용자 프로필 편집 화면 정보 변경 API",
            description = "<p>로그인한 사용자의 프로필 편집 화면의 정보를 변경합니다.</p>"
    )
    @PostMapping(value = "/member/profile/settings")
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