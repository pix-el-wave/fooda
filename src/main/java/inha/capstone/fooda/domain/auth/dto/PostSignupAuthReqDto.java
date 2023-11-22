package inha.capstone.fooda.domain.auth.dto;

import inha.capstone.fooda.domain.member.dto.MemberDto;
import inha.capstone.fooda.domain.member.entity.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostSignupAuthReqDto {
    @NotBlank(message = "이름을 입력해주세요.")
    @Size(min = 2, message = "이름이 너무 짧습니다.")
    @Schema(example = "홍길동", description = "사용자의 실명")
    private String name;

    @NotBlank(message = "아이디(닉네임)을 입력해주세요.")
    @Size(min = 2, message = "아이디가 너무 짧습니다.")
    @Schema(example = "gildong", description = "사용자의 아이디(닉네임)")
    private String username;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Schema(example = "pwd1234!", description = "비밀번호")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    private String password;

    @NotNull(message = "성별(MALE 또는 FEMALE)을 입력해주세요")
    @Schema(example = "MALE", description = "성별")
    private Gender gender;

    @NotNull(message = "몸무게를 입력해주세요")
    @Schema(example = "60", description = "몸무게")
    private Integer weight;

    @NotNull(message = "키를 입력해주세요")
    @Schema(example = "180", description = "키")
    private Integer height;

    @NotNull(message = "나이를 입력해주세요")
    @Schema(example = "25", description = "나이")
    private Integer age;

    @NotNull(message = "목표체중을 입력해주세요")
    @Schema(example = "60", description = "목표체중")
    private Integer targetWeight;

    @NotNull(message = "목표칼로리를 입력해주세요")
    @Schema(example = "2000", description = "목표칼로리")
    private Integer targetKcal;

    public MemberDto toDto() {
        return MemberDto.of(getName(), getUsername(), getPassword(), getGender(), getWeight(), getHeight(), getAge(), getTargetWeight(), getTargetKcal());
    }
}
