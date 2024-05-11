package com.byeon.translator.controller.page;

import com.byeon.translator.AbstractContainerBaseTest;
import com.byeon.translator.controller.request.NoteRequest;
import com.byeon.translator.service.RankingService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("홈페이지 컨트롤러 테스트")
@AutoConfigureMockMvc
class MainControllerTest extends AbstractContainerBaseTest {

    private final MockMvc mvc;

    MainControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @MockBean
    private RankingService rankingService;

    @Test
    @DisplayName("[View] 메인 페이지 이동")
    @WithAnonymousUser
    void moveMainPageTest() throws Exception {
        List<NoteRequest> mockRankingList = Collections.singletonList(new NoteRequest());
        given(rankingService.getTopFiveRank()).willReturn(mockRankingList);

        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("main"))
                .andExpect(model().attributeExists("ranking"))
                .andExpect(model().attribute("ranking", mockRankingList));
    }
}