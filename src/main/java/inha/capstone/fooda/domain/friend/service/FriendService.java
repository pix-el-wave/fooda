package inha.capstone.fooda.domain.friend.service;

import inha.capstone.fooda.domain.friend.entity.Friend;
import inha.capstone.fooda.domain.friend.repository.FriendRepository;
import inha.capstone.fooda.domain.member.dto.MemberDto;
import inha.capstone.fooda.domain.member.entity.Member;
import inha.capstone.fooda.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FriendService {
    private final FriendRepository friendRepository;
    private final MemberRepository memberRepository;

    /**
     * 아이디가 username인 사용자가 팔로우하고 있는 사용자 목록 조회
     *
     * @param username 조회하려는 사용자의 아이디
     * @return 조회된 MemberDto 리스트
     */
    public List<MemberDto> findFollowingMembers(String username) {
        return friendRepository.findByFollower(findMemberByUsername(username))
                .stream()
                .map(friend ->
                        MemberDto.from(findMemberByUsername(friend.getFollowing().getUsername())))
                .toList();
    }

    /**
     * 아이디가 username인 사용자를 팔로우하고 있는 사용자 목록 조회
     *
     * @param username 조회하려는 사용자의 아이디
     * @return 조회된 MemberDto 리스트\
     */
    public List<MemberDto> findFollowerMembers(String username) {
        return friendRepository.findByFollowing(findMemberByUsername(username))
                .stream()
                .map(friend ->
                        MemberDto.from(findMemberByUsername(friend.getFollower().getUsername())))
                .toList();
    }

    private Member findMemberByUsername(String username) {
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " 아이디를 가진 유저가 존재하지 않습니다.")
                );
    }

    /**
     * followerUsername가 followingUsername를 팔로우하는 정보를 DB에 저장
     *
     * @param followerUsername 팔로우 요청을 하는 사용자의 아이디
     * @param followingUsername  팔로우 요청 대상 사용자의 아이디
     * @return 저장된 팔로우 정보의 PK
     */
    @Transactional
    public Long requestToFollow(String followerUsername, String followingUsername) {
        Friend savedFriend = friendRepository.save(
                Friend.builder()
                        .follower(findMemberByUsername(followerUsername))
                        .following(findMemberByUsername(followingUsername))
                        .build());
        return savedFriend.getId();
    }
}
