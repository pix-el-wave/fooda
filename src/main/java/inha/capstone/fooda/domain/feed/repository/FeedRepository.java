package inha.capstone.fooda.domain.feed.repository;

import inha.capstone.fooda.domain.feed.entity.Feed;
import inha.capstone.fooda.domain.friend.entity.Friend;
import inha.capstone.fooda.domain.member.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface FeedRepository extends JpaRepository<Feed, Long> {

    @EntityGraph(attributePaths = {"foods", "feedImages", "member"})
    @Query("SELECT DISTINCT t FROM Feed t WHERE t.createdAt BETWEEN :startDate AND :endDate AND t.member.id = :memberId")
    public List<Feed> findAllByCreatedAtBetweenAndMemberIdUsingFetchJoin(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("memberId") Long memberId);

    @EntityGraph(attributePaths = {"foods", "feedImages", "member"})
    @Query("SELECT DISTINCT t FROM Feed t WHERE t.member.id = :memberId OR t.member.id IN (SELECT f.follower.id FROM Friend f WHERE f.following.id = :memberId)")
    public List<Feed> findFeedsByFollowing(@Param("memberId") Long memberId);
}
