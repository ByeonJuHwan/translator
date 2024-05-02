package com.byeon.translator.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
@AllArgsConstructor
public class MemberJoinRequest {

    @NotBlank
    @Length(min = 2, max = 13)
    private String userId;

    @NotBlank
    @Length(min = 2, max = 13)
    private String password;
}
