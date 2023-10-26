package inha.capstone.fooda.domain.auth.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class PostSignupAuthResDto {
    private Long id;

    public static PostSignupAuthResDto of(Long id) {
        return new PostSignupAuthResDto(id);
    }
}
