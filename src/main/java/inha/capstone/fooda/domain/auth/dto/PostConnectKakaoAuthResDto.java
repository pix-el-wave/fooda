package inha.capstone.fooda.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class PostConnectKakaoAuthResDto {
    @Schema(example = "true", description = "카카오 계정 등록 성공 여부")
    private Boolean isConnectedWithKakao;

    public static PostConnectKakaoAuthResDto of(Boolean isConnectedWithKakao) {
        return new PostConnectKakaoAuthResDto(isConnectedWithKakao);
    }
}
