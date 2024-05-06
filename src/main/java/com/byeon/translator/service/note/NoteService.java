package com.byeon.translator.service.note;

import com.byeon.translator.Repository.NoteRepository;
import com.byeon.translator.Repository.member.MemberRepository;
import com.byeon.translator.controller.response.NoteResponse;
import com.byeon.translator.domain.entity.Member;
import com.byeon.translator.domain.entity.Note;
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

    @Transactional(readOnly = true)
    public List<Note> findMyNotes(String userId) {
        // TODO 유저 검색을 2번씩 하므로 한쪽을 Redis 에 저장 (로그인할때, 번역 노트장)
        Member member = memberRepository.findMemberByUserId(userId).orElseThrow(RuntimeException::new);
        return noteRepository.findAllByMember(member);
    }

    @Transactional
    public void saveNote(NoteResponse noteResponse) {
        noteRepository.save(Note.of(noteResponse));
    }
}
