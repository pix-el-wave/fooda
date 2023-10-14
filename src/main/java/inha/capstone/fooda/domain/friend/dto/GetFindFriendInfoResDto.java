package inha.capstone.fooda.domain.friend.dto;

import inha.capstone.fooda.domain.member.dto.MemberDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetFindFriendInfoResDto {
    List<GetFindFriendInfoFollowingDto> followingList;

    List<GetFindFriendInfoFollowerDto> followerList;

    public static GetFindFriendInfoResDto from(List<MemberDto> followingList, List<MemberDto> followerList) {
        return new GetFindFriendInfoResDto(
                followingList.stream()
                        .map(GetFindFriendInfoFollowingDto::from)
                        .toList(),
                followerList.stream()
                        .map(GetFindFriendInfoFollowerDto::from)
                        .toList()
        );
    }
}
