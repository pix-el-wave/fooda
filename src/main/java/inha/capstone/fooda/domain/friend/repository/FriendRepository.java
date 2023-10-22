package inha.capstone.fooda.domain.friend.repository;

import inha.capstone.fooda.domain.friend.entity.Friend;
import inha.capstone.fooda.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    List<Friend> findByFollowing(Member member);

    List<Friend> findByFollower(Member member);

    Optional<Friend> findByFollowerAndFollowing(Member follower, Member following);
}
