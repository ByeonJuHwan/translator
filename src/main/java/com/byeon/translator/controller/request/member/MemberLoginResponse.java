package com.byeon.translator.controller.request.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberLoginResponse {

    private String userId;
    private String password;

}
