package inha.capstone.fooda.domain.feed.repository;

import inha.capstone.fooda.domain.feed.entity.Feed;
import inha.capstone.fooda.domain.friend.entity.Friend;
import inha.capstone.fooda.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface FeedRepository extends JpaRepository<Feed, Long> {

    @Query("SELECT DISTINCT t FROM Feed t LEFT OUTER JOIN FETCH t.foods LEFT OUTER JOIN FETCH t.member WHERE t.createdAt BETWEEN :startDate AND :endDate AND t.member.id = :memberId")
    public List<Feed> findAllByCreatedAtBetweenAndMemberIdUsingFetchJoin(LocalDateTime startDate, LocalDateTime endDate, Long memberId);
}
