package inha.capstone.fooda.domain.friend.service;

import inha.capstone.fooda.domain.friend.dto.FriendDto;
import inha.capstone.fooda.domain.friend.entity.Friend;
import inha.capstone.fooda.domain.friend.repository.FriendRepository;
import inha.capstone.fooda.domain.member.dto.MemberDto;
import inha.capstone.fooda.domain.member.entity.Gender;
import inha.capstone.fooda.domain.member.entity.Member;
import inha.capstone.fooda.domain.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class FriendServiceTest {
    @InjectMocks
    private FriendService friendService;
    @Mock
    private FriendRepository friendRepository;
    @Mock
    private MemberRepository memberRepository;

    @Test
    public void 유저네임이_주어지면_유저의_팔로잉_목록을_조회한다() {
        //given
        String username = "member1";
        given(friendRepository.findByFollowing(any(Member.class))).willReturn(createFriendList(createFollowingDtoList()));
        given(memberRepository.findByUsername(any(String.class))).willReturn(Optional.of(createMember("member1", "member1", 1L)));

        //when
        List<MemberDto> followingMembers = friendService.findFollowingMembers(username);

        //then
        Assertions.assertThat(followingMembers.size()).isEqualTo(3);
    }

    @Test
    public void 유저네임이_주어지면_유저의_팔로워_목록을_조회한다() {
        //given
        String username = "member1";
        given(friendRepository.findByFollowing(any(Member.class))).willReturn(createFriendList(createFollowerDtoList()));
        given(memberRepository.findByUsername(any(String.class))).willReturn(Optional.of(createMember("member1", "member1", 1L)));

        //when
        List<MemberDto> followingMembers = friendService.findFollowingMembers(username);

        //then
        Assertions.assertThat(followingMembers.size()).isEqualTo(2);
    }

    private List<Friend> createFriendList(List<FriendDto> friendDtos) {
        return friendDtos
                .stream()
                .map(FriendDto::toEntity)
                .toList();
    }

    private List<FriendDto> createFollowingDtoList() {
        return List.of(
                FriendDto.of(
                        createMember("member1", "member1", 1L),
                        createMember("member2", "member2", 2L)),
                FriendDto.of(
                        createMember("member1", "member1", 1L),
                        createMember("member3", "member3", 3L)),
                FriendDto.of(
                        createMember("member1", "member1", 1L),
                        createMember("member5", "member5", 5L))
        );
    }

    private List<FriendDto> createFollowerDtoList() {
        return List.of(
                FriendDto.of(
                        createMember("member4", "member4", 4L),
                        createMember("member1", "member1", 1L)),
                FriendDto.of(
                        createMember("member5", "member5", 5L),
                        createMember("member1", "member1", 1L))
        );
    }

    private Member createMember(String name, String username, Long id) {
        Member member = createMemberDto(name, username).toEntity();
        ReflectionTestUtils.setField(member, "id", id);
        return member;
    }

    private MemberDto createMemberDto(String name, String username) {
        return MemberDto.of(name, username, "pwd1234!", Gender.MALE, 70, 180, 25, 60, 2000);
    }
}
