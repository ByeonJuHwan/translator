package com.byeon.translator.controller.api;

import com.byeon.translator.controller.request.translate.TranslateRequest;
import com.byeon.translator.controller.response.DeeplResponse;
import com.byeon.translator.service.translate.TranslateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class TranslateRestController {

    private final TranslateService translateService;

    @PostMapping("/translate")
    public ResponseEntity<DeeplResponse> translateResult(@RequestBody TranslateRequest request) {
        return ResponseEntity.ok(translateService.callApiResult(request));
    }
}
