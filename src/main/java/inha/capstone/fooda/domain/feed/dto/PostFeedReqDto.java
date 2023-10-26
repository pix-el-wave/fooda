package inha.capstone.fooda.domain.feed.dto;

import inha.capstone.fooda.domain.feed.entity.Menu;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostFeedReqDto {

    @NotNull(message = "이미지를 최소 1개 업로드해 주세요.")
    @Schema(description = "음식 사진")
    private List<MultipartFile> img;

    @NotNull(message = "친구 공개 여부를 선택해주세요.")
    @Schema(description = "친구 공개 여부")
    private Boolean open;

    @NotNull(message = "식사 종류를 선택해주세요.")
    @Schema(description = "식사 종류")
    private Menu meal;
}
