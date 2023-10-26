package inha.capstone.fooda.domain.feed.service;

import inha.capstone.fooda.domain.feed.entity.Feed;
import inha.capstone.fooda.domain.feed.entity.Menu;
import inha.capstone.fooda.domain.feed.repository.FeedRepository;
import inha.capstone.fooda.domain.feed_image.service.FeedImageService;
import inha.capstone.fooda.domain.member.repository.MemberRepository;
import inha.capstone.fooda.utils.AICommunicationUtils;
import inha.capstone.fooda.utils.FoodListReqDto;
import inha.capstone.fooda.utils.FoodListResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FeedService {
    private final FeedRepository feedRepository;
    private final FeedImageService feedImageService;
    private final MemberRepository memberRepository;

    private final AICommunicationUtils aiCommunicationUtils;

    @Transactional
    public List<FoodListResDto> uploadFeed(Long memberId, Boolean open, Menu menu, List<MultipartFile> imgs) throws IOException {
        Long feedId = saveFeed(memberId, open, menu);
        List<FoodListResDto> all = new ArrayList<>();
        for (MultipartFile img : imgs) {
            String url = feedImageService.uploadFeedImage(img, feedId);
            all.addAll(aiCommunicationUtils.requestImageList(new FoodListReqDto(url)).getData());
        }
        return all;
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
