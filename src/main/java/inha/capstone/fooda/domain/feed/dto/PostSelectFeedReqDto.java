package inha.capstone.fooda.domain.feed.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class PostSelectFeedReqDto {

    @NotNull(message = "시작 날짜를 정해주세요.")
    @Schema(description = "시작 날짜")
    private LocalDate start;

    @NotNull(message = "끝 날짜를 정해주세요.")
    @Schema(description = "끝 날짜")
    private LocalDate end;
}
