package inha.capstone.fooda.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostKakaoSigninMemberReqDto {
    @NotBlank
    @Schema(
            example = "ZKvMpru4xZOETYhWeyc2Ved0q4AUdXUFr4KBzh7DtQSKNKVvb4FquEyb",
            description = "Kakao server에서 전달받은 access token"
    )
    private String kakaoAccessToken;
}
