package inha.capstone.fooda.domain.feed.repository;

import inha.capstone.fooda.domain.feed.entity.Feed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface FeedRepository extends JpaRepository<Feed, Long> {

    @Query("SELECT DISTINCT t FROM Feed t LEFT OUTER JOIN FETCH t.foods LEFT OUTER JOIN FETCH t.member WHERE t.createdAt BETWEEN :startDate AND :endDate AND t.member.id = :memberId")
    public List<Feed> findAllByCreatedAtBetweenAndMemberIdUsingFetchJoin(LocalDateTime startDate, LocalDateTime endDate, Long memberId);

    @Query("SELECT DISTINCT t FROM Feed t LEFT OUTER JOIN FETCH t.foods LEFT OUTER JOIN FETCH t.member WHERE t.member.id = :memberId OR t.member.id IN (SELECT f.follower.id FROM Friend f WHERE f.following.id = :memberId)")
    public List<Feed> findFeedsByFollowing(Long memberId);
}
