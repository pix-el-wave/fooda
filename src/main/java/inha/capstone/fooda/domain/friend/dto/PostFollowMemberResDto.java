package inha.capstone.fooda.domain.friend.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostFollowMemberResDto {
    Long friendId;

    public static PostFollowMemberResDto of(Long friendId) {
        return new PostFollowMemberResDto(friendId);
    }
}
