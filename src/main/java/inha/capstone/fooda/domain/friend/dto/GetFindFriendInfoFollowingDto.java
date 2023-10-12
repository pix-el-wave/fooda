package inha.capstone.fooda.domain.friend.dto;


import inha.capstone.fooda.domain.member.dto.MemberDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetFindFriendInfoFollowingDto {
    @Schema(example = "홍길동", description = "사용자의 실명")
    private String name;

    @Schema(example = "gildong", description = "사용자의 아이디(닉네임)")
    private String username;

    public static GetFindFriendInfoFollowingDto from(MemberDto memberDto) {
        return new GetFindFriendInfoFollowingDto(memberDto.getName(), memberDto.getUsername());
    }
}
