package com.byeon.translator.controller.response;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberJoinResponse {
    @NotBlank
    @Length(min = 2, max = 13)
    private String userId;

    @NotBlank
    @Length(min = 2, max = 13)
    private String password;
}
