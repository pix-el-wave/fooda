package inha.capstone.fooda.domain.feed_image.service;

import inha.capstone.fooda.domain.common.service.S3Service;
import inha.capstone.fooda.domain.feed.entity.Feed;
import inha.capstone.fooda.domain.feed.entity.Menu;
import inha.capstone.fooda.domain.feed.repository.FeedRepository;
import inha.capstone.fooda.domain.feed_image.entity.FeedImage;
import inha.capstone.fooda.domain.feed_image.exception.NotImageFileExcpetion;
import inha.capstone.fooda.domain.feed_image.repository.FeedImageRepository;
import inha.capstone.fooda.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FeedImageService {
    private final FeedImageRepository feedImageRepository;
    private final FeedRepository feedRepository;
    private final S3Service s3Service;

    @Transactional
    public void uploadFeedImage(MultipartFile img, Long feedId) throws IOException {

        // 이미지 파일인지 검사하는 로직
        Image image = ImageIO.read(img.getInputStream());
        if (image == null) throw new NotImageFileExcpetion();

        String fileName = UUID.randomUUID().toString();
        String url = s3Service.upload(img, fileName);
        FeedImage feedImage = FeedImage.builder()
                .feed(feedRepository.getReferenceById(feedId))
                .fileNameStored(fileName)
                .fileName(img.getOriginalFilename())
                .url(url)
                .build();
        feedImageRepository.save(feedImage);
    }
}
