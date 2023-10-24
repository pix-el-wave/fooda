package inha.capstone.fooda.domain.member.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import inha.capstone.fooda.domain.common.response.DataResponse;
import inha.capstone.fooda.domain.member.dto.GetGetKakaoUserInfoMemberResDto;
import inha.capstone.fooda.domain.member.dto.PostKakaoSigninMemberReqDto;
import inha.capstone.fooda.domain.member.dto.PostKakaoSigninMemberResDto;
import inha.capstone.fooda.domain.member.dto.PostKakaoSignupMemberReqDto;
import inha.capstone.fooda.domain.member.dto.PostKakaoSignupMemberResDto;
import inha.capstone.fooda.domain.member.dto.PostSigninMemberReqDto;
import inha.capstone.fooda.domain.member.dto.PostSigninMemberResDto;
import inha.capstone.fooda.domain.member.dto.PostSignupMemberReqDto;
import inha.capstone.fooda.domain.member.dto.PostSignupMemberResDto;
import inha.capstone.fooda.domain.member.service.AuthService;
import inha.capstone.fooda.domain.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Login & SignUp")
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class AuthController {
    private final AuthService authService;
    private final MemberService memberService;
    private final KakaoOauthController kakaoOauthController;

    @Operation(
            summary = "회원가입 API",
            description = "<p>회원 정보를 바탕으로 회원 가입을 진행합니다.</p>" +
                    "<p>회원가입에 성공하면 회원가입된 사용자의 pk 를 응답합니다.</p>"
    )
    @PostMapping(value = "/local/new")
    public ResponseEntity<DataResponse<PostSignupMemberResDto>> signUp(
            @Validated @RequestBody PostSignupMemberReqDto postSignupMemberReqDto) {
        Long memberId = authService.signUp(postSignupMemberReqDto.toDto());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new DataResponse<>(
                        PostSignupMemberResDto.of(memberId)
                ));
    }

    @Operation(
            summary = "JWT 로그인 요청 API",
            description = "<p>로그인하려는 아이디와 패스워드로 서버에 로그인합니다.</p>" +
                    "<p>로그인에 성공하면 jwt access token 을 응답합니다.</p>" +
                    "<p><strong>이후 로그인 권한이 필요한 API를 호출할 때는 HTTP header에 다음과 같이 access token을 담아서 요청해야 합니다.</strong></p>"
                    +
                    "<ul><li><code>Authorization: Bearer 서버로부터_받은_액세스_토큰</code></li></ul>"
    )
    @PostMapping("/local")
    public ResponseEntity<DataResponse<PostSigninMemberResDto>> signIn(
            @Valid @RequestBody PostSigninMemberReqDto postSigninMemberReqDto
    ) {
        String jwtToken = authService.signIn(postSigninMemberReqDto.getUsername(),
                postSigninMemberReqDto.getPassword());

        PostSigninMemberResDto response = PostSigninMemberResDto.of(jwtToken);

        return new ResponseEntity<>(
                new DataResponse<>(response),
                HttpStatus.OK
        );
    }

    @Operation(
            summary = "카카오 회원가입",
            description = "<p>Kakao에서 전달받은 access token(<code>kakaoAccessToken</code>)으로 server에 로그인합니다.</p>"
                    + "<p>회원가입에 성공하면 jwt access token을 응답합니다.</p>"
                    + "<p><strong>이후 로그인 권한이 필요한 API를 호출할 때는 HTTP header에 다음과 같이 access token을 담아서 요청해야 합니다.</strong></p>"
                    + "<ul><li><code>Authorization: Bearer 서버로부터_받은_액세스_토큰</code></li></ul>"
    )
    @PostMapping("/kakao/new")
    public ResponseEntity<DataResponse<PostKakaoSignupMemberResDto>> kakaoSignup(
            @Valid @RequestBody PostKakaoSignupMemberReqDto request) throws JsonProcessingException {
        GetGetKakaoUserInfoMemberResDto userInfo = kakaoOauthController.getKakaoUserInfo(request.getKakaoAccessToken());

        // 회원이 존재하지 않을 경우 회원가입 처리
        String username = userInfo.getNickname();
        if (!memberService.existsMemberByUsername(username)) {
            authService.signUp(request.toDto(username));
        }

        // jwt 토큰 생성
        String jwtToken = authService.createToken(username);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new DataResponse<>(
                        PostKakaoSignupMemberResDto.of(
                                jwtToken
                        ))
                );
    }

    @Operation(
            summary = "카카오 로그인",
            description = "<p>Kakao에서 전달받은 access token(<code>kakaoAccessToken</code>)으로 server에 로그인합니다.</p>"
                    + "<p>로그인에 성공하면 jwt access token을 응답합니다.</p>"
                    + "<p><strong>이후 로그인 권한이 필요한 API를 호출할 때는 HTTP header에 다음과 같이 access token을 담아서 요청해야 합니다.</strong></p>"
                    + "<ul><li><code>Authorization: Bearer 서버로부터_받은_액세스_토큰</code></li></ul>"
                    + "<p><strong>로그인이 실패할 경우, isUserSignedUp이 false입니다. 카카오 회원가입 API로 요청해주세요.</strong></p>"
    )
    @PostMapping("/kakao")
    public ResponseEntity<DataResponse<PostKakaoSigninMemberResDto>> kakaoSignin(
            @Valid @RequestBody PostKakaoSigninMemberReqDto request) throws JsonProcessingException {
        GetGetKakaoUserInfoMemberResDto userInfo = kakaoOauthController.getKakaoUserInfo(request.getKakaoAccessToken());

        String username = userInfo.getNickname();

        // 해당 카카오 유저의 회원가입 여부를 설정
        boolean isUserSignedUp = memberService.existsMemberByUsername(username);

        // jwt 토큰 생성
        String jwtToken = isUserSignedUp ? authService.createToken(username) : null;

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new DataResponse<>(
                        PostKakaoSigninMemberResDto.of(
                                jwtToken,
                                isUserSignedUp
                        ))
                );
    }

}
