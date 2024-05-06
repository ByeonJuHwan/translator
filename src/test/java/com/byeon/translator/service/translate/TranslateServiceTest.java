package com.byeon.translator.service.translate;

import com.byeon.translator.Repository.member.MemberRepository;
import com.byeon.translator.controller.request.translate.TranslateRequest;
import com.byeon.translator.controller.response.NoteResponse;
import com.byeon.translator.controller.response.deepl.DeeplResponse;
import com.byeon.translator.controller.response.deepl.DeeplTranslateResult;
import com.byeon.translator.domain.entity.Member;
import com.byeon.translator.service.note.NoteService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.MultiValueMap;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("[번역] 번역 로직 테스트")
@ExtendWith(MockitoExtension.class)
class TranslateServiceTest {

    @InjectMocks
    private TranslateService sut;

    @Mock
    private TranslateFeignClient translateFeignClient;
    @Mock
    private MemberRepository memberRepository;

    @Mock
    private NoteService noteService;

    @Test
    @DisplayName("[번역] Deeple api 로 번역 성공")
    void callApiResult() {
        TranslateRequest request = new TranslateRequest();
        request.setText("안녕");
        request.setTargetLang("EN");

        DeeplTranslateResult translateResult = new DeeplTranslateResult();
        translateResult.setDetectedSourceLanguage("KO");
        translateResult.setText("Hello");

        DeeplResponse expectedResponse = new DeeplResponse();
        expectedResponse.setTranslations(Collections.singletonList(translateResult));

        Member member = Member.builder().userId("test").build();

        when(memberRepository.findMemberByUserId("test")).thenReturn(Optional.of(member));
        when(translateFeignClient.translate(any(MultiValueMap.class))).thenReturn(expectedResponse);

        DeeplResponse actualResponse = sut.callApiResult(request, "test");

        assertNotNull(actualResponse);
        assertThat(actualResponse.getTranslations().size()).isEqualTo(1);
        assertThat(actualResponse.getTranslations().get(0).getText()).isEqualTo("Hello");
        verify(noteService).saveNote(any(NoteResponse.class));
    }
}