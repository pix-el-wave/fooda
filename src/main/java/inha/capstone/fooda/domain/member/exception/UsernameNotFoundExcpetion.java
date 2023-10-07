package inha.capstone.fooda.domain.member.exception;

import inha.capstone.fooda.domain.common.exception.NotFoundException;

public class UsernameNotFoundExcpetion extends NotFoundException {
    public UsernameNotFoundExcpetion(String optionalMessage) {
        super(optionalMessage);
    }
}
