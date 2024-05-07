package com.byeon.translator.service.translate;

import com.byeon.translator.AbstractContainerBaseTest;
import com.byeon.translator.controller.request.translate.TranslateRequest;
import com.byeon.translator.controller.response.deepl.DeeplResponse;
import com.byeon.translator.service.MessageQueueService;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.MultiValueMap;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TranslateRetryServiceTest extends AbstractContainerBaseTest {

    @Autowired
    TranslateService translateService;

    @MockBean
    MessageQueueService messageQueueService;

    @MockBean
    private TranslateFeignClient translateFeignClient;


    @Test
    void retry_success(){
        // 첫 번째 호출에서 예외를 던지고, 두 번째 호출에서 성공적인 응답을 반환
        when(translateFeignClient.translate(any(MultiValueMap.class)))
                .thenThrow(new RuntimeException("Service Unavailable"))
                .thenReturn(new DeeplResponse()); // secondResponse

        // 서비스 메소드 호출
        DeeplResponse response = translateService.callApiResult(new TranslateRequest(), "userId");

        assertNull(response);
        // translate 메소드가 2번 호출되었는지 확인
        verify(translateFeignClient, times(2)).translate(any(MultiValueMap.class));
    }

    @Test
    void retry_fail() {
        // Given
        TranslateRequest request = new TranslateRequest();
        request.setText("Hello");
        request.setTargetLang("DE");
        String userId = "user123";

        when(translateFeignClient.translate(any(MultiValueMap.class)))
                .thenThrow(new RuntimeException("Service Unavailable"));

        // Then
        DeeplResponse deeplResponse = translateService.callApiResult(request, userId);


        Assertions.assertThat(deeplResponse).isNull();
        verify(translateFeignClient, times(2)).translate(any(MultiValueMap.class));


    }
}