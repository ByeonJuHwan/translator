package com.byeon.translator.service.note;

import com.byeon.translator.Repository.NoteRepository;
import com.byeon.translator.Repository.member.MemberRepository;
import com.byeon.translator.domain.entity.Member;
import com.byeon.translator.domain.entity.Note;
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
import static org.mockito.Mockito.when;

@DisplayName("[단어장] 번역 단어장 비즈니스 로직")
@ExtendWith(MockitoExtension.class)
class NoteServiceTest {

    @InjectMocks
    private NoteService sut;

    @Mock
    private MemberRepository memberRepository;
    @Mock
    private NoteRepository noteRepository;


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
}