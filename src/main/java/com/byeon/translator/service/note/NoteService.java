package com.byeon.translator.service.note;

import com.byeon.translator.Repository.MemberCacheRepository;
import com.byeon.translator.Repository.NoteRepository;
import com.byeon.translator.Repository.member.MemberRepository;
import com.byeon.translator.controller.response.NoteResponse;
import com.byeon.translator.domain.entity.Member;
import com.byeon.translator.domain.entity.Note;
import com.byeon.translator.dto.MemberCacheDto;
import com.byeon.translator.exception.custom.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final MemberRepository memberRepository;
    private final MemberCacheRepository memberCacheRepository;

    @Transactional(readOnly = true)
    public List<Note> findMyNotes(String userId) {
        // 기존에 DB 에서 검색 해오던 로직을 redis 에서 가져오게 변경
        Member member = memberCacheRepository.getUser(userId, MemberCacheDto.class).map(Member::from)
                .orElseGet(() -> memberRepository.findMemberByUserId(userId).orElseThrow(() -> new MemberNotFoundException("일치하는 회원이 없습니다.")));
        return noteRepository.findAllByMember(member);
    }

    @Transactional
    public void saveNote(NoteResponse noteResponse) {
        long duplicateNoteCount = noteRepository.countNoteBySendMessageAndTranslateMessageAndMember
                (noteResponse.getSendMessage(), noteResponse.getTranslateMessage(), noteResponse.getMember());

        if (duplicateNoteCount == 0) {
            noteRepository.save(Note.of(noteResponse));
        }
    }
}
