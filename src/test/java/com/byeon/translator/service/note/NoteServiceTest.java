package com.byeon.translator.service.note;

import com.byeon.translator.Repository.MemberCacheRepository;
import com.byeon.translator.Repository.NoteRepository;
import com.byeon.translator.Repository.member.MemberRepository;
import com.byeon.translator.controller.response.NoteResponse;
import com.byeon.translator.domain.entity.Member;
import com.byeon.translator.domain.entity.Note;
import com.byeon.translator.dto.MemberCacheDto;
import com.byeon.translator.exception.custom.MemberNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("[단어장] 번역 단어장 비즈니스 로직")
@ExtendWith(MockitoExtension.class)
class NoteServiceTest {

    @InjectMocks
    private NoteService sut;

    @Mock
    private MemberRepository memberRepository;
    @Mock
    private NoteRepository noteRepository;
    @Mock
    private MemberCacheRepository memberCacheRepository;


    @Test
    @DisplayName("[단어장] 자신이 번역한 단어장의 단어들을 검색한다.")
    void findMyNotes() {
        // given
        String userId = "test";
        Member member = Member.builder().userId(userId).build();

        List<Note> notes = List.of(new Note(1L, "test", "test", member));

        // when
        when(memberRepository.findMemberByUserId(userId)).thenReturn(Optional.of(member));
        when(noteRepository.findAllByMember(member)).thenReturn(notes);

        List<Note> myNotes = sut.findMyNotes(userId);

        // 검증
        assertThat(myNotes).isNotNull();
        assertThat(myNotes.size()).isEqualTo(1);
    }


    @Test
    @DisplayName("[단어장] 일치하는 회원이 없을 경우 예외 발생")
    void findMyNotes_MemberNotFoundException() {
        // given
        String userId = "nonexistentUser";

        // when
        when(memberCacheRepository.getUser(userId, MemberCacheDto.class)).thenReturn(Optional.empty());
        when(memberRepository.findMemberByUserId(userId)).thenReturn(Optional.empty());

        // 검증
        assertThatThrownBy(() -> sut.findMyNotes(userId)).isInstanceOf(MemberNotFoundException.class);
    }

    @Test
    @DisplayName("[단어장] 번역한 단어를 저장한다.")
    void saveNote() {
        Member member = Member.builder()
                .userId("test")
                .build();

        NoteResponse noteResponse = new NoteResponse("안녕", "hello", member);

        when(noteRepository.countNoteBySendMessageAndTranslateMessageAndMember(noteResponse.getSendMessage(), noteResponse.getTranslateMessage(), noteResponse.getMember()))
                .thenReturn(0L);

        sut.saveNote(noteResponse);

        verify(noteRepository, times(1)).save(any(Note.class));
    }

    @Test
    @DisplayName("[단어장] 중복되는 단어를 번역하면 저장하지 않는다.")
    void doNot_save_note() {
        Member member = Member.builder()
                .userId("test")
                .build();

        NoteResponse noteResponse = new NoteResponse("안녕", "hello", member);

        when(noteRepository.countNoteBySendMessageAndTranslateMessageAndMember(noteResponse.getSendMessage(), noteResponse.getTranslateMessage(), noteResponse.getMember()))
                .thenReturn(1L);

        sut.saveNote(noteResponse);

        verify(noteRepository, times(0)).save(any(Note.class));
    }
}