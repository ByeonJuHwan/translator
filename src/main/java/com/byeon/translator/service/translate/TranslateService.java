package com.byeon.translator.service.translate;


import com.byeon.translator.controller.request.translate.TranslateRequest;
import com.byeon.translator.controller.response.DeeplResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class TranslateService {

    @Value("${deepl.api-key}")
    private String apiKey;

    private final TranslateFeignClient translateFeignClient;

    public DeeplResponse callApiResult(TranslateRequest request) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("auth_key", apiKey);
        formData.add("text", request.getText());
        formData.add("target_lang", request.getTargetLang());

        return translateFeignClient.translate(formData);
    }
}
