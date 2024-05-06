package com.byeon.translator.controller.api;

import com.byeon.translator.AbstractContainerBaseTest;
import com.byeon.translator.controller.request.translate.TranslateRequest;
import com.byeon.translator.controller.response.deepl.DeeplResponse;
import com.byeon.translator.controller.response.deepl.DeeplTranslateResult;
import com.byeon.translator.service.translate.TranslateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("[번역] - 번역 api 컨트롤러")
@AutoConfigureMockMvc
class TranslateRestControllerTest extends AbstractContainerBaseTest {

    private final MockMvc mvc;

    TranslateRestControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }
    @MockBean
    private TranslateService translateService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    @DisplayName("[번역] 일본어 번역 성공")
    void translateResult() throws Exception {
        DeeplResponse response = new DeeplResponse();
        DeeplTranslateResult result = new DeeplTranslateResult();
        result.setText("Hello");
        result.setDetectedSourceLanguage("EN");
        response.setTranslations(Collections.singletonList(result));


        given(translateService.callApiResult(any(TranslateRequest.class), any(String.class))).willReturn(response);

        mvc.perform(post("/translate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new TranslateRequest("Hello", "EN"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.translations[0].text").value("Hello"));

    }
}