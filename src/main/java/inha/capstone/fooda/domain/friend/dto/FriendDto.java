package inha.capstone.fooda.domain.friend.dto;

import inha.capstone.fooda.domain.friend.entity.Friend;
import inha.capstone.fooda.domain.member.entity.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class FriendDto {
    private Long id;
    private Member follower;
    private Member following;

    public static FriendDto from(Friend friend) {
        return new FriendDto(
                friend.getId(),
                friend.getFollower(),
                friend.getFollowing()
        );
    }

    public static FriendDto of(Member follower, Member following) {
        return new FriendDto(null, follower, following);
    }

    public Friend toEntity() {
        return Friend.builder()
                .id(getId())
                .follower(getFollower())
                .following(getFollowing())
                .build();
    }
}
