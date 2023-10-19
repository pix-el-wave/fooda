package inha.capstone.fooda.domain.member.dto;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("[DTO] 카카오 사용자 정보 가져오기 테스트")
class GetGetKakaoUserInfoMemberResDtoTest {
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void 인증_결과를_Map으로_받으면_카카오_인증_응답_객체로_변환한다() throws Exception {
        // given
        String response = "{" +
                "\"id\": 1234567890," +
                "\"connected_at\": \"2023-10-19T13:07:45Z\"," +
                "\"kakao_account\": {" +
                "\"profile_nickname_needs_agreement\": true," +
                "\"profile\": {" +
                "\"nickname\": \"member1\"" +
                "}" +
                "}" +
                "}";
        Map<String, Object> attributes = mapper.readValue(response, new TypeReference<>() {
        });

        // when
        GetGetKakaoUserInfoMemberResDto result = GetGetKakaoUserInfoMemberResDto.from(attributes);

        // then
        assertAll(
                () -> assertThat(result.getId()).isEqualTo(1234567890L),
                () -> assertThat(result.getConnectedAt()).isEqualTo(
                        ZonedDateTime.of(2023, 10, 19, 13, 7, 45, 0, ZoneOffset.UTC)
                                .withZoneSameInstant(ZoneId.systemDefault())
                                .toLocalDateTime()
                ),
                () -> assertThat(result.getNickname()).isEqualTo("member1")
        );
    }
}