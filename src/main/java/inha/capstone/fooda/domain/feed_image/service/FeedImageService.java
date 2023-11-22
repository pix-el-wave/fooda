package inha.capstone.fooda.domain.feed_image.service;

import inha.capstone.fooda.domain.common.service.S3Service;
import inha.capstone.fooda.domain.feed.entity.Feed;
import inha.capstone.fooda.domain.feed.repository.FeedRepository;
import inha.capstone.fooda.domain.feed_image.dto.FeedImageDto;
import inha.capstone.fooda.domain.feed_image.entity.FeedImage;
import inha.capstone.fooda.domain.feed_image.exception.NotImageFileExcpetion;
import inha.capstone.fooda.domain.feed_image.repository.FeedImageRepository;
import inha.capstone.fooda.domain.member.entity.Member;
import inha.capstone.fooda.domain.member.repository.MemberRepository;
import java.awt.Image;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import javax.imageio.ImageIO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FeedImageService {
    private final FeedImageRepository feedImageRepository;
    private final FeedRepository feedRepository;
    private final MemberRepository memberRepository;
    private final S3Service s3Service;

    @Transactional
    public String uploadFeedImage(MultipartFile img, Long feedId) throws IOException {

        // 이미지 파일인지 검사하는 로직
        Image image = ImageIO.read(img.getInputStream());
        if (image == null) {
            throw new NotImageFileExcpetion();
        }

        String fileName = UUID.randomUUID().toString();
        String url = s3Service.upload(img, fileName);
        FeedImage feedImage = FeedImage.builder()
                .feed(feedRepository.getReferenceById(feedId))
                .fileNameStored(fileName)
                .fileName(img.getOriginalFilename())
                .url(url)
                .build();
        feedImageRepository.save(feedImage);
        return url;
    }

    /**
     * 사용자가 업로드한 피드의 첫번째 이미지 리스트를 조회한다.
     *
     * @param username 사용자의 닉네임(아이디)
     * @return 피드 첫번째 이미지 리스트
     */
    public List<FeedImageDto> getFirstFeedImageList(String username) {
        // 멤버 조회
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " 아이디를 가진 유저가 존재하지 않습니다."));

        // 해당 멤버가 올린 피드 조회
        List<Feed> feedList = feedRepository.findAllByMember(member);

        // 피드의 첫번째 이미지 리스트
        List<FeedImage> feedImageList = feedList.stream()
                .map(feedImageRepository::findTop1ByFeed)
                .toList();

        return feedImageList.stream()
                .map(FeedImageDto::from)
                .toList();
    }
}
