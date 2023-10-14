package inha.capstone.fooda.domain.member.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class PostSignupMemberResDto {
    private Long id;

    public static PostSignupMemberResDto of(Long id) {
        return new PostSignupMemberResDto(id);
    }
}
