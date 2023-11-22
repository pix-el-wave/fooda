package inha.capstone.fooda.domain.feed.service;

import inha.capstone.fooda.domain.feed.dto.FeedDto;
import inha.capstone.fooda.domain.feed.dto.UploadFeedDto;
import inha.capstone.fooda.domain.feed.entity.Feed;
import inha.capstone.fooda.domain.feed.entity.Menu;
import inha.capstone.fooda.domain.feed.repository.FeedRepository;
import inha.capstone.fooda.domain.feed_image.service.FeedImageService;
import inha.capstone.fooda.domain.food.repository.FoodRepository;
import inha.capstone.fooda.domain.food.service.FoodService;
import inha.capstone.fooda.domain.member.repository.MemberRepository;
import inha.capstone.fooda.utils.AICommunicationUtils;
import inha.capstone.fooda.utils.FoodListReqDto;
import inha.capstone.fooda.utils.FoodListResDto;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FeedService {
    private final FeedRepository feedRepository;
    private final FeedImageService feedImageService;
    private final MemberRepository memberRepository;
    private final FoodRepository foodRepository;
    private final FoodService foodService;

    private final AICommunicationUtils aiCommunicationUtils;

    @Transactional
    public UploadFeedDto uploadFeed(Long memberId, Boolean open, Menu menu, List<MultipartFile> imgs)
            throws IOException {
        Long feedId = saveFeed(memberId, open, menu);
        List<FoodListResDto> foodList = new ArrayList<>();
        for (MultipartFile img : imgs) {
            String url = feedImageService.uploadFeedImage(img, feedId);
            foodList.addAll(aiCommunicationUtils.requestImageList(new FoodListReqDto(url)).getData());
        }
        return new UploadFeedDto(feedId, foodList);
    }

    @Transactional
    public Long saveFeed(Long memberId, Boolean open, Menu menu) {
        Feed feed = Feed.builder()
                .member(memberRepository.getReferenceById(memberId))
                .open(open)
                .menu(menu)
                .likeCount(0L)
                .build();
        feedRepository.save(feed);
        return feed.getId();
    }

    public List<FeedDto> selectFeed(Long memberId, LocalDate start, LocalDate end) {
        List<Feed> feedList = feedRepository.findAllByCreatedAtBetweenAndMemberIdUsingFetchJoin(start.atStartOfDay(),
                end.plusDays(1).atStartOfDay(), memberId);
        return feedList.stream()
                .map(FeedDto::from)
                .toList();
    }

    public List<FeedDto> selectFeedByFollowing(Long memberId) {
        List<Feed> feedList = feedRepository.findFeedsByFollowing(memberId);
        return feedList.stream()
                .map(FeedDto::from)
                .toList();
    }

    public List<FeedDto> selectFeedByAll(Long memberId) {
        List<Feed> feedList = feedRepository.findAll();
        return feedList.stream()
                .map(FeedDto::from)
                .toList();
    }

    public Long countFeed(String username) {
        return feedRepository.countAllByMember(
                memberRepository.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException(username + " 아이디를 가진 유저가 존재하지 않습니다."))
        );
    }
}
