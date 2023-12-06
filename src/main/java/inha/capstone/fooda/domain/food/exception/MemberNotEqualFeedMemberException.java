package inha.capstone.fooda.domain.food.exception;

import inha.capstone.fooda.domain.common.exception.ConflictException;

public class MemberNotEqualFeedMemberException extends ConflictException {
    public MemberNotEqualFeedMemberException(String optionalMessage) {
        super(optionalMessage);
    }
}
