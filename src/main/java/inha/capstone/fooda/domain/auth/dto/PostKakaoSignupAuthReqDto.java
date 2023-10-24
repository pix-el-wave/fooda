package inha.capstone.fooda.domain.auth.dto;

import inha.capstone.fooda.domain.member.dto.MemberDto;
import inha.capstone.fooda.domain.member.entity.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class PostKakaoSignupAuthReqDto {

    @NotBlank
    @Schema(
            example = "ZKvMpru4xZOETYhWeyc2Ved0q4AUdXUFr4KBzh7DtQSKNKVvb4FquEyb",
            description = "Kakao server에서 전달받은 access token"
    )
    private String kakaoAccessToken;

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

    public static PostKakaoSignupAuthReqDto of(String kakaoAccessToken) {
        return new PostKakaoSignupAuthReqDto(kakaoAccessToken, null, null, null, null, null, null);
    }

    public MemberDto toDto(String username, String kakaoEmail) {
        return MemberDto.of(
                username,
                username,
                username,
                getGender(),
                getWeight(),
                getHeight(),
                getAge(),
                getTargetWeight(),
                getTargetKcal(),
                kakaoEmail
        );
    }
}
