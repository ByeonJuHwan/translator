package com.byeon.translator.Repository;


import com.byeon.translator.domain.entity.Member;
import com.byeon.translator.domain.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findAllByMember(Member member);

    long countNoteBySendMessageAndTranslateMessageAndMember(String sendMessage, String translateMessage, Member member);
}

