package com.byeon.translator.controller.page;

import com.byeon.translator.AbstractContainerBaseTest;
import com.byeon.translator.Repository.NoteRepository;
import com.byeon.translator.Repository.member.MemberRepository;
import com.byeon.translator.controller.response.NoteResponse;
import com.byeon.translator.service.note.front.NoteFrontService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("[번역기록] 번역 노트 컨트롤러")
@AutoConfigureMockMvc
class NoteControllerTest extends AbstractContainerBaseTest {

    private final MockMvc mockMvc;

    NoteControllerTest(@Autowired MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @MockBean
    private NoteFrontService noteFrontService;

    @Test
    @WithMockUser(username = "test")
    @DisplayName("[번역기록] 번역 기록 페이지 이동 성공")
    void myNote() throws Exception {
        List<NoteResponse> mockNotes = Collections.singletonList(new NoteResponse());
        when(noteFrontService.getNoteSearchResponse("test")).thenReturn(mockNotes);

        mockMvc.perform(get("/notes"))
                .andExpect(status().isOk())
                .andExpect(view().name("notes"))
                .andExpect(model().attributeExists("notes"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }
}