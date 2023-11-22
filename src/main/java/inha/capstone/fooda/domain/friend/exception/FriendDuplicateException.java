package inha.capstone.fooda.domain.friend.exception;

import inha.capstone.fooda.domain.common.exception.ConflictException;

public class FriendDuplicateException extends ConflictException {
    public FriendDuplicateException(String optionalMessage) {
        super(optionalMessage);
    }
}
