package inha.capstone.fooda.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostModifyInfoMemberReqDto {
    @NotNull(message = "몸무게를 입력해주세요")
    @Schema(example = "75", description = "몸무게")
    private Integer weight;

    @NotNull(message = "키를 입력해주세요")
    @Schema(example = "185", description = "키")
    private Integer height;

    @NotNull(message = "나이를 입력해주세요")
    @Schema(example = "26", description = "나이")
    private Integer age;

    @NotNull(message = "목표체중을 입력해주세요")
    @Schema(example = "70", description = "목표체중")
    private Integer targetWeight;

    @NotNull(message = "목표칼로리를 입력해주세요")
    @Schema(example = "2500", description = "목표칼로리")
    private Integer targetKcal;

    public MemberDto toDto() {
        return MemberDto.of(getWeight(), getHeight(), getAge(), getTargetWeight(), getTargetKcal());
    }
}
