package com.byeon.translator.controller.page;

import com.byeon.translator.controller.response.NoteResponse;
import com.byeon.translator.service.note.front.NoteFrontService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Slf4j
@Controller
@RequiredArgsConstructor
public class NoteController {

    private final NoteFrontService noteFrontService;

    @GetMapping("/notes")
    public String myNote(Model model, Authentication authentication) {
        List<NoteResponse> notes = noteFrontService.getNoteSearchResponse(authentication.getName());
        model.addAttribute("notes", notes);
        return "notes";
    }
}
