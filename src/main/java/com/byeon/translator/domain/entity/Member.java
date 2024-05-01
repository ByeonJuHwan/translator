package com.byeon.translator.domain.entity;


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
}
