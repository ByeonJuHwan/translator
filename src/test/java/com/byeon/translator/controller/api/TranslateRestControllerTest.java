package com.byeon.translator.controller.api;

import com.byeon.translator.AbstractContainerBaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@DisplayName("[번역] - 번역 api 컨트롤러")
@AutoConfigureMockMvc
class TranslateRestControllerTest extends AbstractContainerBaseTest {

    private final MockMvc mvc;

    TranslateRestControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @Test
    @WithMockUser
    @DisplayName("[번역] 일본어 번역 성공")
    void translateResult() throws Exception {
        mvc.perform(post("/translate"))
                .andExpect(status().isOk());
        // TODO service 완성 하고 통합 테스트로 진행
    }
}