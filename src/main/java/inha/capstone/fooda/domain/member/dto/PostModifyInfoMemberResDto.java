package inha.capstone.fooda.domain.member.dto;

import inha.capstone.fooda.domain.member.entity.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostModifyInfoMemberResDto {
    @Schema(example = "홍길동", description = "사용자의 실명")
    private String name;

    @Schema(example = "gildong", description = "사용자의 아이디(닉네임)")
    private String username;

    @Schema(example = "MALE", description = "성별")
    private Gender gender;

    @Schema(example = "70", description = "몸무게")
    private Integer weight;

    @Schema(example = "180", description = "키")
    private Integer height;

    @Schema(example = "25", description = "나이")
    private Integer age;

    @Schema(example = "65", description = "목표몸무게")
    private Integer targetWeight;

    @Schema(example = "2000", description = "목표칼로리")
    private Integer targetKcal;

    public static PostModifyInfoMemberResDto from(MemberDto memberDto) {
        return new PostModifyInfoMemberResDto(
                memberDto.getName(),
                memberDto.getUsername(),
                memberDto.getGender(),
                memberDto.getWeight(),
                memberDto.getHeight(),
                memberDto.getAge(),
                memberDto.getTargetWeight(),
                memberDto.getTargetKcal()
        );
    }
}
