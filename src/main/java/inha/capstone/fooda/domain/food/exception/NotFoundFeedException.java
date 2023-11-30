package inha.capstone.fooda.domain.food.exception;

import inha.capstone.fooda.domain.common.exception.ConflictException;

public class NotFoundFeedException extends ConflictException {
    public NotFoundFeedException(String optionalMessage) {
        super(optionalMessage);
    }
}
