package com.byeon.translator.service.note.front;


import com.byeon.translator.controller.response.NoteResponse;
import com.byeon.translator.domain.entity.Note;
import com.byeon.translator.service.note.NoteService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteFrontService {

    private final NoteService noteService;

    public List<NoteResponse> getNoteSearchResponse(String userId) {
        List<Note> notes = noteService.findMyNotes(userId);
        return notes.stream().map(NoteResponse::fromEntity).toList();
    }
}
