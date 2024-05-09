package com.byeon.translator.controller.page;

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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@DisplayName("View - 번역 컨틀롤러")
@AutoConfigureMockMvc
class TranslateControllerTest extends AbstractContainerBaseTest {

    private final MockMvc mockMvc;

    TranslateControllerTest(@Autowired MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    @WithMockUser
    @DisplayName("[번역] 번역 페이지 이동 성공")
    void moveTranslatePageTest() throws Exception {
        mockMvc.perform(get("/translate"))
                .andExpect(status().isOk())
                .andExpect(view().name("translate"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }

//    @Test
//    @WithAnonymousUser
//    @DisplayName("[번역] 로그인 안한 회원의 경우 번역 페이지 이동 실패")
//    void moveTranslatePageFailTest() throws Exception {
//        mockMvc.perform(get("/translate"))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrlPattern("**/member/login"));
//    }
}