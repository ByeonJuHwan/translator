package com.byeon.translator.service.translate;

import com.byeon.translator.controller.request.translate.TranslateRequest;
import com.byeon.translator.controller.response.DeeplResponse;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(
        name = "translate-client",
        url = "${deepl.url}"
)
public interface TranslateFeignClient {

    @PostMapping
    @Headers({"Content-Type: application/x-www-form-urlencoded", "Accept: application/json"})
    DeeplResponse translate(@RequestBody MultiValueMap<String, String> formData);
}