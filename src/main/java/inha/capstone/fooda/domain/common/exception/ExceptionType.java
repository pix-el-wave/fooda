package inha.capstone.fooda.domain.common.exception;

import inha.capstone.fooda.domain.feed_image.exception.NotImageFileExcpetion;
import inha.capstone.fooda.domain.member.exception.UsernameDuplicateException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * [ Error code 규약 ]
 * Auth: 1XXX
 * Member : 2XXX
 */

@AllArgsConstructor
@Getter
public enum ExceptionType {

    // Common
    UNHANDLED_EXCEPTION(0, "알 수 없는 서버 에러가 발생했습니다.", null),
    BAD_REQUEST_EXCEPTION(1, "요청 데이터가 잘못되었습니다.", null),
    ACCESS_DENIED_EXCEPTION(2, "권한이 유효하지 않습니다.", null),
    AUTHENTICATION_EXCEPTION(3, "인증 과정에서 문제가 발생했습니다.", null),
    NOT_IMAGE_EXCEPTION(4, "이미지 파일이 아닙니다.", NotImageFileExcpetion.class),

    // Auth
    USERNAME_DUPLICATE_EXEPTION(1000, "아이디(유저네임)이 중복됩니다.", UsernameDuplicateException.class);

    private final int errorCode;
    private final String errorMessage;
    private final Class<? extends Exception> type;

    public static ExceptionType from(Class<? extends CustomException> classType) {
        return findExceptionType(classType, UNHANDLED_EXCEPTION);
    }

    public static ExceptionType fromByAuthenticationFailed(Class<? extends Exception> classType) {
        return findExceptionType(classType, AUTHENTICATION_EXCEPTION);
    }

    private static ExceptionType findExceptionType(
            Class<? extends Exception> classType,
            ExceptionType defaultExceptionType
    ) {
        return Arrays.stream(values())
                .filter(it -> Objects.nonNull(it.type) && it.type.equals(classType))
                .findFirst()
                .orElse(defaultExceptionType);
    }
}
