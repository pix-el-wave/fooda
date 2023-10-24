package inha.capstone.fooda.domain.feed.repository;

import inha.capstone.fooda.domain.feed.entity.Feed;
import inha.capstone.fooda.domain.friend.entity.Friend;
import inha.capstone.fooda.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedRepository extends JpaRepository<Feed, Long> {

}
