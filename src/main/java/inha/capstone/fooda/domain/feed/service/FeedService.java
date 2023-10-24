package inha.capstone.fooda.domain.feed.service;

import inha.capstone.fooda.domain.feed.entity.Feed;
import inha.capstone.fooda.domain.feed.entity.Menu;
import inha.capstone.fooda.domain.feed.repository.FeedRepository;
import inha.capstone.fooda.domain.feed_image.service.FeedImageService;
import inha.capstone.fooda.domain.friend.repository.FriendRepository;
import inha.capstone.fooda.domain.member.dto.MemberDto;
import inha.capstone.fooda.domain.member.entity.Member;
import inha.capstone.fooda.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FeedService {
    private final FeedRepository feedRepository;
    private final FeedImageService feedImageService;
    private final MemberRepository memberRepository;

    @Transactional
    public long uploadFeed(Long memberId, Boolean open, Menu menu, List<MultipartFile> imgs) throws IOException {
        Long feedId = saveFeed(memberId, open, menu);
        for (MultipartFile img : imgs) {
            feedImageService.uploadFeedImage(img, feedId);
        }
        return feedId;
    }

    @Transactional
    public Long saveFeed(Long memberId, Boolean open, Menu menu) {
        Feed feed = Feed.builder()
                .member(memberRepository.getReferenceById(memberId))
                .open(open)
                .menu(menu)
                .build();
        feedRepository.save(feed);
        return feed.getId();
    }
}
