package inha.capstone.fooda.domain.auth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import inha.capstone.fooda.domain.common.response.DataResponse;
import inha.capstone.fooda.domain.auth.dto.GetGetKakaoUserInfoAuthResDto;
import inha.capstone.fooda.domain.auth.dto.PostConnectKakaoAuthReqDto;
import inha.capstone.fooda.domain.auth.dto.PostConnectKakaoAuthResDto;
import inha.capstone.fooda.domain.auth.dto.PostKakaoSigninAuthReqDto;
import inha.capstone.fooda.domain.auth.dto.PostKakaoSigninAuthResDto;
import inha.capstone.fooda.domain.auth.dto.PostKakaoSignupAuthReqDto;
import inha.capstone.fooda.domain.auth.dto.PostKakaoSignupAuthResDto;
import inha.capstone.fooda.domain.auth.dto.PostSigninAuthReqDto;
import inha.capstone.fooda.domain.auth.dto.PostSigninAuthResDto;
import inha.capstone.fooda.domain.auth.dto.PostSignupAuthReqDto;
import inha.capstone.fooda.domain.auth.dto.PostSignupAuthResDto;
import inha.capstone.fooda.domain.auth.service.AuthService;
import inha.capstone.fooda.domain.member.service.MemberService;
import inha.capstone.fooda.security.FoodaPrinciple;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth")
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class AuthController {
    private final AuthService authService;
    private final MemberService memberService;
    private final KakaoOauthController kakaoOauthController;

    @Operation(summary = "회원가입 API", description = "<p>회원 정보를 바탕으로 회원 가입을 진행합니다.</p>"
            + "<p>회원가입에 성공하면 회원가입된 사용자의 pk 를 응답합니다.</p>")
    @PostMapping(value = "/local/new")
    public ResponseEntity<DataResponse<PostSignupAuthResDto>> signUp(
            @Validated @RequestBody PostSignupAuthReqDto postSignupAuthReqDto) {
        Long memberId = authService.signUp(postSignupAuthReqDto.toDto());
        return ResponseEntity.status(HttpStatus.OK).body(new DataResponse<>(PostSignupAuthResDto.of(memberId)));
    }

    @Operation(summary = "JWT 로그인 요청 API", description = "<p>로그인하려는 아이디와 패스워드로 서버에 로그인합니다.</p>"
            + "<p>로그인에 성공하면 jwt access token 을 응답합니다.</p>"
            + "<p><strong>이후 로그인 권한이 필요한 API를 호출할 때는 HTTP header에 다음과 같이 access token을 담아서 요청해야 합니다.</strong></p>"
            + "<ul><li><code>Authorization: Bearer 서버로부터_받은_액세스_토큰</code></li></ul>")
    @PostMapping("/local")
    public ResponseEntity<DataResponse<PostSigninAuthResDto>> signIn(
            @Valid @RequestBody PostSigninAuthReqDto postSigninAuthReqDto) {
        String jwtToken = authService.signIn(postSigninAuthReqDto.getUsername(),
                postSigninAuthReqDto.getPassword());

        PostSigninAuthResDto response = PostSigninAuthResDto.of(jwtToken);

        return new ResponseEntity<>(new DataResponse<>(response), HttpStatus.OK);
    }

    @Operation(summary = "카카오 회원가입", description =
            "<p>Kakao에서 전달받은 access token(<code>kakaoAccessToken</code>)으로 server에 로그인합니다.</p>"
                    + "<p>회원가입에 성공하면 jwt access token을 응답합니다.</p>"
                    + "<p><strong>이후 로그인 권한이 필요한 API를 호출할 때는 HTTP header에 다음과 같이 access token을 담아서 요청해야 합니다.</strong></p>"
                    + "<ul><li><code>Authorization: Bearer 서버로부터_받은_액세스_토큰</code></li></ul>")
    @PostMapping("/kakao/new")
    public ResponseEntity<DataResponse<PostKakaoSignupAuthResDto>> kakaoSignup(
            @Valid @RequestBody PostKakaoSignupAuthReqDto request) throws JsonProcessingException {
        GetGetKakaoUserInfoAuthResDto userInfo = kakaoOauthController.getKakaoUserInfo(request.getKakaoAccessToken());

        // 회원이 존재하지 않을 경우 회원가입 처리
        if (!memberService.existsMemberByKakaoEmail(userInfo.getEmail())) {
            authService.signUp(request.toDto(userInfo.getNickname(), userInfo.getEmail()));
        }

        // jwt 토큰 생성
        String jwtToken = authService.createToken(userInfo.getNickname());

        return ResponseEntity.status(HttpStatus.OK).body(new DataResponse<>(PostKakaoSignupAuthResDto.of(jwtToken)));
    }

    @Operation(summary = "카카오 로그인", description =
            "<p>Kakao에서 전달받은 access token(<code>kakaoAccessToken</code>)으로 server에 로그인합니다.</p>"
                    + "<p>로그인에 성공하면 jwt access token을 응답합니다.</p>"
                    + "<p><strong>이후 로그인 권한이 필요한 API를 호출할 때는 HTTP header에 다음과 같이 access token을 담아서 요청해야 합니다.</strong></p>"
                    + "<ul><li><code>Authorization: Bearer 서버로부터_받은_액세스_토큰</code></li></ul>"
                    + "<p><strong>로그인이 실패할 경우, isUserSignedUp이 false입니다. 카카오 회원가입 API로 요청해주세요.</strong></p>")
    @PostMapping("/kakao")
    public ResponseEntity<DataResponse<PostKakaoSigninAuthResDto>> kakaoSignin(
            @Valid @RequestBody PostKakaoSigninAuthReqDto request) throws JsonProcessingException {
        GetGetKakaoUserInfoAuthResDto userInfo = kakaoOauthController.getKakaoUserInfo(request.getKakaoAccessToken());

        // 해당 카카오 유저의 회원가입 여부를 설정
        boolean isUserSignedUp = memberService.existsMemberByKakaoEmail(userInfo.getEmail());

        // jwt 토큰 생성
        String jwtToken = isUserSignedUp ? authService.createToken(userInfo.getNickname()) : null;

        return ResponseEntity.status(HttpStatus.OK)
                .body(new DataResponse<>(PostKakaoSigninAuthResDto.of(jwtToken, isUserSignedUp)));
    }

    @Operation(
            summary = "카카오 계정 연결",
            description =
                    "<p><strong>이미 로컬로 로그인한 유저</strong>가 Kakao access token(<code>kakaoAccessToken</code>)으로 계정을 연결합니다.</p>"
                            + "<p>성공하면 해당 유저는 로컬 로그인 및 카카오 로그인 방식으로 로그인할 수 있습니다.</p>")
    @PostMapping("/kakao/local")
    public ResponseEntity<DataResponse<PostConnectKakaoAuthResDto>> connectKakao(
            @Validated @RequestBody PostConnectKakaoAuthReqDto request,
            @Parameter(hidden = true) @AuthenticationPrincipal FoodaPrinciple principle)
            throws JsonProcessingException {
        GetGetKakaoUserInfoAuthResDto userInfo = kakaoOauthController.getKakaoUserInfo(request.getKakaoAccessToken());

        authService.connectWithKakao(principle.getUsername(), userInfo.getEmail());

        return ResponseEntity.status(HttpStatus.OK).body(new DataResponse<>(PostConnectKakaoAuthResDto.of(true)));
    }
}
