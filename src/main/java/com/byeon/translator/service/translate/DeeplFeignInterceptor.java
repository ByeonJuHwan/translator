package com.byeon.translator.service.translate;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@RequiredArgsConstructor
public class DeeplFeignInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        // 요청 본문을 UTF-8 문자열로 디코딩합니다.
        String decodedBody = URLDecoder.decode(
                StringUtils.toEncodedString(template.body(), StandardCharsets.UTF_8), StandardCharsets.UTF_8
        );

        // RequestLog 표기
        writeTranslateRequestLog(decodedBody);

        // TODO MQ 프로토콜로 비동기로 DB 저장 혹은 redis 작업 실행
    }

    private void writeTranslateRequestLog(String decodedBody) {
        // api 도 decodedBody 에 뜨므로 api key 는 제외해서 출력
        String[] translateRequestBody = decodedBody.split("&");
        StringBuilder logBuilder = new StringBuilder();
        logBuilder.append(translateRequestBody[1]).append(" ").append(translateRequestBody[2]);
        log.info("[POST] [DemoFeignInterceptor] {}", logBuilder);
    }
}
