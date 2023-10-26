package inha.capstone.fooda.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class PostKakaoSignupAuthResDto {
    @Schema(
            example = "35Vu7VBqtRgBQ9GjdIt9onYS03yXuAP28R2HLRMxKVO9wrEdJ5Fj8y0r3b6I7QJYjApZG3JuOA28YUDjwkdYqOxj2s3f94DdQjww",
            description = "Jwt access token"
    )
    private String accessToken;

    public static PostKakaoSignupAuthResDto of(String accessToken) {
        return new PostKakaoSignupAuthResDto(accessToken);
    }
}
