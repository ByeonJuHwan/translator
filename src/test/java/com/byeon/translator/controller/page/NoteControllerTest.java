package com.byeon.translator.controller.page;

import com.byeon.translator.AbstractContainerBaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("[번역기록] 번역 노트 컨트롤러")
@AutoConfigureMockMvc
class NoteControllerTest extends AbstractContainerBaseTest {

    private final MockMvc mockMvc;

    NoteControllerTest(@Autowired MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    @WithMockUser
    @DisplayName("[번역기록] 번역 기록 페이지 이동 성공")
    void myNote() throws Exception {
        mockMvc.perform(get("/notes"))
                .andExpect(status().isOk())
                .andExpect(view().name("notes"))
                .andExpect(model().attributeExists("notes"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }
}