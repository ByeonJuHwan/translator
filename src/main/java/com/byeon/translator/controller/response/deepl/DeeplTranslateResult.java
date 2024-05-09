package com.byeon.translator.controller.response.deepl;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeeplTranslateResult{

    @JsonProperty("detected_source_language")
    private String detectedSourceLanguage;

    private String text;

}
