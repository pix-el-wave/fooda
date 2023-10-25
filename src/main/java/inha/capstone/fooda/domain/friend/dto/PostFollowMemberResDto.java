package inha.capstone.fooda.domain.friend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostFollowMemberResDto {
    @Schema(example = "1", description = "저장한 팔로우 요청 데이터 PK")
    Long friendId;

    public static PostFollowMemberResDto of(Long friendId) {
        return new PostFollowMemberResDto(friendId);
    }
}
