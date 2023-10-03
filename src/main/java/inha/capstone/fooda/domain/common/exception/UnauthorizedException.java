package inha.capstone.fooda.domain.common.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends CustomException {

    public UnauthorizedException() {
        super(HttpStatus.UNAUTHORIZED);
    }

    public UnauthorizedException(String optionalMessage) {
        super(HttpStatus.UNAUTHORIZED, optionalMessage);
    }
}
