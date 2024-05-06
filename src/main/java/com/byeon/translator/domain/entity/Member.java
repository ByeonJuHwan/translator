package com.byeon.translator.domain.entity;


import com.byeon.translator.dto.MemberCacheDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private String password;
    private String name;

    @OneToMany(mappedBy = "member")
    private List<Note> notes = new ArrayList<>();


    @Builder
    public Member(Long id, String userId, String password, String name, List<Note> notes) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.notes = notes;
    }

    public static Member of(String userId, String password, String name, List<Note> notes) {
        return Member.builder()
                .name(name)
                .password(password)
                .userId(userId)
                .notes(notes)
                .build();
    }

    public static Member from(MemberCacheDto memberCacheDto) {
        return Member.builder()
                .id(memberCacheDto.getId())
                .userId(memberCacheDto.getUserId())
                .name(memberCacheDto.getName())
                .build();
    }
}
