package com.byeon.translator.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberLoginResponse {

    private String userId;
    private String password;

}
