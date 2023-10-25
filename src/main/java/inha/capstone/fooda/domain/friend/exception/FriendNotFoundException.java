package inha.capstone.fooda.domain.friend.exception;

import inha.capstone.fooda.domain.common.exception.NotFoundException;

public class FriendNotFoundException extends NotFoundException {
    public FriendNotFoundException(String optionalMessage) {
        super(optionalMessage);
    }
}
