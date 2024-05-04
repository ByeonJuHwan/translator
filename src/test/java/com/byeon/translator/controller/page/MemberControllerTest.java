package com.byeon.translator.controller.page;

import com.byeon.translator.AbstractContainerBaseTest;
import com.byeon.translator.controller.request.member.MemberJoinRequest;
import com.byeon.translator.service.member.front.MemberFrontService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("View - 회원 컨트롤러")
@AutoConfigureMockMvc
class MemberControllerTest extends AbstractContainerBaseTest{

    private final MockMvc mockMvc;

    MemberControllerTest(@Autowired MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }


    @MockBean
    private MemberFrontService memberFrontService;


    @Test
    @WithAnonymousUser
    @DisplayName("[회원가입] 회원가입 페이지 이동")
    void moveJoinPageTest() throws Exception {
        mockMvc.perform(get("/member/join"))
                .andExpect(status().isOk())
                .andExpect(view().name("member/join"))
                .andExpect(model().attributeExists("member"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }

    @Test
    @WithAnonymousUser
    @DisplayName("[회원가입] 회원 가입 성공 후 메인 페이지로 이동")
    void doJoinTest() throws Exception {
        MemberJoinRequest request = validRequest();
        doNothing().when(memberFrontService).join(any(MemberJoinRequest.class));

        mockMvc.perform(post("/member/join")
                        .param("userId", request.getUserId())
                        .param("password", request.getPassword())
                        .param("name", request.getName()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    @WithAnonymousUser
    @DisplayName("[회원가입] 회원 가입 시 아이디 비밀번호 validation 테스트")
    void requestValidationCheckTest() throws Exception {
        MemberJoinRequest request = invalidRequest();

        doNothing().when(memberFrontService).join(any(MemberJoinRequest.class));

        mockMvc.perform(post("/member/join")
                        .param("userId", request.getUserId())
                        .param("password", request.getPassword())
                        .param("name", request.getName()))
                .andExpect(status().isOk())
                .andExpect(view().name("member/join"))
                .andExpect(model().attributeExists("member"))
                .andExpect(model().attributeHasFieldErrors("member", "userId"))
                .andExpect(model().attributeHasFieldErrors("member", "password"))
                .andExpect(model().attributeHasFieldErrors("member", "name"));

    }


    @Test
    @WithAnonymousUser
    @DisplayName("[로그인] 로그인 페이지 이동 성공")
    void moveLoginPageTest() throws Exception {
        mockMvc.perform(get("/member/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("member/login"))
                .andExpect(model().attributeExists("login"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }

    private MemberJoinRequest validRequest() {
        return new MemberJoinRequest("test", "test" ,"test");
    }

    private MemberJoinRequest invalidRequest() {
        return new MemberJoinRequest("", "" ,"");
    }
}