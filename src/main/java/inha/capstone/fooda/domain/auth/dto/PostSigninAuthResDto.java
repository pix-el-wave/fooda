package inha.capstone.fooda.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostSigninAuthResDto {
    @Schema(example = "sfadi15i0sdifj23320dsfj", description = "로그인한 사용자의 jwt 액세스 토큰")
    private String jwtAccessToken;

    public static PostSigninAuthResDto of(String jwtAccessToken) {
        return new PostSigninAuthResDto(jwtAccessToken);
    }
}
