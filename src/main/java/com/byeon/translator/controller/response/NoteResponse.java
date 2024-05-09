package com.byeon.translator.controller.response;

import com.byeon.translator.domain.entity.Member;
import com.byeon.translator.domain.entity.Note;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoteResponse {
    private String sendMessage;
    private String translateMessage;
    private Member member;

    public static NoteResponse fromEntity(Note note) {
        return NoteResponse.builder()
                .sendMessage(note.getSendMessage())
                .translateMessage(note.getTranslateMessage())
                .build();
    }
}
