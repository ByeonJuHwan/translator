package com.byeon.translator.controller.request.translate;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TranslateRequest {

    private String text;

    @JsonProperty("target_lang")
    private String targetLang;
}
