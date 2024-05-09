package com.byeon.translator.service.translate;

import com.byeon.translator.controller.response.deepl.DeeplResponse;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "translate-client",
        url = "${deepl.url}"
)
public interface TranslateFeignClient {

    @PostMapping
    @Headers({"Content-Type: application/x-www-form-urlencoded", "Accept: application/json"})
    DeeplResponse translate(@RequestBody MultiValueMap<String, String> formData);
}