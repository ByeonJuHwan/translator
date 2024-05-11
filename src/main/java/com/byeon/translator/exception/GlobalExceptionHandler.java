package com.byeon.translator.exception;

import com.byeon.translator.controller.response.MemberJoinResponse;
import com.byeon.translator.exception.custom.DuplicatedMemberException;
import com.byeon.translator.exception.custom.MemberNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicatedMemberException.class)
    public String handleDuplicatedMemberException(DuplicatedMemberException e, Model model) {
        model.addAttribute("userIdError", e.getMessage());
        model.addAttribute("member", new MemberJoinResponse());
        return "member/join";
    }

    @ExceptionHandler(MemberNotFoundException.class)
    public String handleMemberNotFoundException(MemberNotFoundException e, Model model) {
        model.addAttribute("userIdError", e.getMessage());
        model.addAttribute("member", new MemberJoinResponse());
        return "member/join";
    }

    @ExceptionHandler(Exception.class)
    public String handlerException(Exception e) {
        return "error/500";
    }
}
