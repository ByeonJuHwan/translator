package com.byeon.translator.controller.page;


import com.byeon.translator.controller.request.MemberJoinRequest;
import com.byeon.translator.controller.response.MemberJoinResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    @GetMapping("/join")
    public String joinPage(Model model) {
        model.addAttribute("member", new MemberJoinResponse());
        return "member/join";
    }

    @PostMapping("/join")
    public String join(MemberJoinRequest request, Model model) {
        return "main";
    }

}
