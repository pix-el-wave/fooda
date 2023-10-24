package inha.capstone.fooda.domain.member.dto;

import inha.capstone.fooda.domain.member.entity.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class PostKakaoLoginMemberReqDto {

    @NotBlank
    @Schema(
            example = "ZKvMpru4xZOETYhWeyc2Ved0q4AUdXUFr4KBzh7DtQSKNKVvb4FquEyb",
            description = "Kakao server에서 전달받은 access token"
    )
    private String kakaoAccessToken;

    /**
     * 이미 회원가입된 카카오 회원일 경우 아래의 요청 값들은 무시된다.
     */
    @Nullable
    @Schema(example = "MALE", description = "성별")
    private Gender gender;

    @Nullable
    @Schema(example = "60", description = "몸무게")
    private Integer weight;

    @Nullable
    @Schema(example = "180", description = "키")
    private Integer height;

    @Nullable
    @Schema(example = "25", description = "나이")
    private Integer age;

    @Nullable
    @Schema(example = "60", description = "목표체중")
    private Integer targetWeight;

    @Nullable
    @Schema(example = "2000", description = "목표칼로리")
    private Integer targetKcal;

    public static PostKakaoLoginMemberReqDto of(String kakaoAccessToken) {
        return new PostKakaoLoginMemberReqDto(kakaoAccessToken, null, null, null, null, null, null);
    }

    public MemberDto toDto(String username) {
        return MemberDto.of(
                username,
                username,
                username,
                getGender(),
                getWeight(),
                getHeight(),
                getAge(),
                getTargetWeight(),
                getTargetKcal()
        );
    }
}
