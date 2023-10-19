package inha.capstone.fooda.domain.member.dto;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

// KakaoOauthController에서 access token으로 유저 정보를 받아오기 위한 Response Dto
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@SuppressWarnings("unchecked")  // TODO: Map -> Object 변환 로직이 있어서 generic type casting 문제를 무시한다. 더 좋은 방법이 있다면 고려할 수 있음.
public class GetGetKakaoUserInfoMemberResDto {

    private Long id;    // Kakao 회원 번호
    private LocalDateTime connectedAt; // 연결 시각
    private KakaoAccount kakaoAccount;

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter(AccessLevel.PRIVATE)
    public static class KakaoAccount {
        private Boolean profileNeedsAgreement;
        private String nickname;

        public static KakaoAccount of(Boolean profileNeedsAgreement, String nickname) {
            return new KakaoAccount(profileNeedsAgreement, nickname);
        }

        public static KakaoAccount from(Map<String, Object> attributes) {
            return new KakaoAccount(
                    Boolean.valueOf(String.valueOf(attributes.get("profile_nickname_needs_agreement"))),
                    String.valueOf(((Map<String, String>) attributes.get("profile")).get("nickname"))
            );
        }
    }

    public static GetGetKakaoUserInfoMemberResDto of(Long id, LocalDateTime connectedAt, KakaoAccount kakaoAccount) {
        return new GetGetKakaoUserInfoMemberResDto(id, connectedAt, kakaoAccount);
    }

    public static GetGetKakaoUserInfoMemberResDto from(Map<String, Object> attributes) {
        return new GetGetKakaoUserInfoMemberResDto(
                Long.valueOf(String.valueOf(attributes.get("id"))),
                ZonedDateTime.parse(
                        String.valueOf(attributes.get("connected_at")),
                        DateTimeFormatter.ISO_INSTANT.withZone(ZoneId.systemDefault())
                ).toLocalDateTime(),
                KakaoAccount.from((Map<String, Object>) attributes.get("kakao_account"))
        );
    }

    public MemberDto toMemberDto() {
        return MemberDto.of(
                getNickname(),
                String.valueOf(getId())
        );
    }

    // Getter
    public String getNickname() {
        return this.getKakaoAccount().getNickname();
    }
}
