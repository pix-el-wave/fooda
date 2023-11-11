package inha.capstone.fooda.domain.friend.repository;

import inha.capstone.fooda.domain.friend.entity.Friend;
import inha.capstone.fooda.domain.member.entity.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    long countAllByFollowing(Member member);

    long countAllByFollower(Member member);

    List<Friend> findByFollowing(Member member);

    List<Friend> findByFollower(Member member);

    Optional<Friend> findByFollowerAndFollowing(Member follower, Member following);
}
