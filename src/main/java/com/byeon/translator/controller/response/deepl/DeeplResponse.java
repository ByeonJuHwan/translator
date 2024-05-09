package com.byeon.translator.controller.response.deepl;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DeeplResponse {
    private List<DeeplTranslateResult> translations;
}
