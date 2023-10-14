package inha.capstone.fooda.domain.feed.dto;

import inha.capstone.fooda.domain.feed.entity.Menu;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostFeedReqDto {
    private List<MultipartFile> img;
    private Boolean open;
    private Menu meal;
}
