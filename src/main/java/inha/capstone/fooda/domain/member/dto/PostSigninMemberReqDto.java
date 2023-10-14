package inha.capstone.fooda.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostSigninMemberReqDto {
    @NotBlank(message = "아이디(닉네임)을 입력해주세요")
    @Size(min = 2, message = "아이디(닉네임)이 너무 짧습니다.")
    @Schema(example = "gildong", description = "사용자의 아이디(닉네임)")
    private String username;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Schema(example = "pwd1234!", description = "비밀번호")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    private String password;
}
