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
        // TODO API 키가 log 에 뜨는건 아닌거 같아서 여기서 api Key 는 빼고 출력되도록 변경
        log.info("[POST] [DemoFeignInterceptor] {}", decodedBody);

        // TODO MQ 프로토콜로 비동기로 DB 저장 혹은 redis 작업 실행
    }
}
