package inha.capstone.fooda.domain.friend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeleteUnfollowMemberReqDto {
    @NotBlank(message = "아이디(닉네임)을 입력해주세요.")
    @Size(min = 2, message = "아이디가 너무 짧습니다.")
    @Schema(example = "gildong", description = "사용자의 아이디(닉네임)")
    String username;
}
