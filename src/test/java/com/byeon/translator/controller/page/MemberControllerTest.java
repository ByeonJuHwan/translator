package com.byeon.translator.controller.page;

import com.byeon.translator.controller.request.MemberJoinRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("View - 회원 컨트롤러")
@WebMvcTest(MemberController.class)
class MemberControllerTest {

    private final MockMvc mvc;

    MemberControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @Test
    @DisplayName("회원가입 페이지 이동")
    void moveJoinPageTest() throws Exception {
        mvc.perform(get("/join"))
                .andExpect(status().isOk())
                .andExpect(view().name("member/join"))
                .andExpect(model().attributeExists("member"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }

}