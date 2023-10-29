package inha.capstone.fooda.domain.feed.dto;

import inha.capstone.fooda.domain.feed.entity.Menu;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostSelectFeedReqDto {

    @NotNull(message = "시작 날짜를 정해주세요.")
    @Schema(description = "시작 날짜")
    private LocalDate start;

    @NotNull(message = "끝 날짜를 정해주세요.")
    @Schema(description = "끝 날짜")
    private LocalDate end;
}
