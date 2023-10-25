package inha.capstone.fooda.domain.friend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DeleteUnfollowMemberResDto {
    @Schema(example = "true", description = "팔로우 취소 요청 성공 여부")
    boolean result;

    public static DeleteUnfollowMemberResDto of(boolean result) {
        return new DeleteUnfollowMemberResDto(result);
    }
}
