package com.byeon.translator.service.translate;


import com.byeon.translator.controller.request.NoteRequest;
import com.byeon.translator.controller.request.translate.TranslateRequest;
import com.byeon.translator.controller.response.deepl.DeeplResponse;
import com.byeon.translator.service.MessageQueueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
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
    private final MessageQueueService messageQueueService;

    @Retryable(
            maxAttempts = 2,
            backoff = @Backoff(2000)
    )
    public DeeplResponse callApiResult(TranslateRequest request, String userId) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("auth_key", apiKey);
        formData.add("text", request.getText());
        formData.add("target_lang", request.getTargetLang());

        DeeplResponse translatedResult = translateFeignClient.translate(formData);

        // 여기는 비동기로 동작 번역은 나가야하 는데 굳이 note 에 저장하는걸 기다릴 필요가 없으므로 별도의 쓰레드 처리
        messageQueueService.saveMQNote(request.getText(), translatedResult.getTranslations().get(0).getText(), userId);
        messageQueueService.saveRank(NoteRequest.of(request.getText(), translatedResult.getTranslations().get(0).getText()));

        return translatedResult;
    }

    @Recover
    public DeeplResponse recover(RuntimeException e, TranslateRequest request, String userId) {
        log.error("All the retries failed, request : {}, error : {}", request.getText(), e.getMessage());
        return null;
    }
}
