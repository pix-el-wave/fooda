package inha.capstone.fooda.domain.feed.repository;

import inha.capstone.fooda.domain.feed.entity.Feed;
import inha.capstone.fooda.domain.member.entity.Member;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FeedRepository extends JpaRepository<Feed, Long> {

    @EntityGraph(attributePaths = {"foods", "feedImages"})
    @Query("SELECT DISTINCT t FROM Feed t WHERE t.createdAt BETWEEN :startDate AND :endDate AND t.member.id = :memberId")
    public List<Feed> findAllByCreatedAtBetweenAndMemberIdUsingFetchJoin(LocalDateTime startDate, LocalDateTime endDate,
                                                                         Long memberId);

    public List<Feed> findAllByMember(Member member);

    public Long countAllByMember(Member member);
}
