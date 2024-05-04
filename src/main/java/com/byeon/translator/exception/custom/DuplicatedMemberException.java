package com.byeon.translator.exception.custom;

public class DuplicatedMemberException extends RuntimeException {
    public DuplicatedMemberException(String message) {
        super(message);
    }
}
