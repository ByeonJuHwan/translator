package com.byeon.translator.controller.page;

import com.byeon.translator.controller.request.NoteRequest;
import com.byeon.translator.service.RankingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {

    private final RankingService rankingService;

    @GetMapping("/")
    public String mainPage(Model model) {
        List<NoteRequest> topFiveRank = rankingService.getTopFiveRank();
        model.addAttribute("ranking", topFiveRank);
        return "main";
    }
}
