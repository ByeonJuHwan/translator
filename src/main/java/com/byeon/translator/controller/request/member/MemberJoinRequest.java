package com.byeon.translator.controller.request.member;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
public class MemberJoinRequest {

    @NotBlank
    @Length(min = 2, max = 13)
    private String userId;

    @NotBlank
    @Length(min = 2, max = 13)
    private String password;

    @NotBlank
    private String name;
}
