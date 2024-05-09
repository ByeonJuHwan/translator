package com.byeon.translator.exception.custom;


public class MemberNotFoundException extends RuntimeException{

    public MemberNotFoundException(String message) {
        super(message);
    }
}
