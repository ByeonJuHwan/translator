package com.byeon.translator.domain.entity;

import com.byeon.translator.controller.response.NoteResponse;
import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.FetchType.LAZY;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sendMessage;
    private String translateMessage;

    @ManyToOne(fetch = LAZY)
    private Member member;


    public void addMember(Member member) {
        this.member = member;
        member.getNotes().add(this);
    }

    public static Note of(NoteResponse noteResponse) {
        return Note.builder()
                .sendMessage(noteResponse.getSendMessage())
                .translateMessage(noteResponse.getTranslateMessage())
                .member(noteResponse.getMember())
                .build();
    }
}
