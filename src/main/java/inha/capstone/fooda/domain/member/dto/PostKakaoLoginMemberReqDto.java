package inha.capstone.fooda.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class PostKakaoLoginMemberReqDto {

    @Schema(
            example = "ZKvMpru4xZOETYhWeyc2Ved0q4AUdXUFr4KBzh7DtQSKNKVvb4FquEyb",
            description = "Kakao server에서 전달받은 access token"
    )
    @NotBlank
    private String kakaoAccessToken;

    public static PostKakaoLoginMemberReqDto of(String kakaoAccessToken) {
        return new PostKakaoLoginMemberReqDto(kakaoAccessToken);
    }
}
