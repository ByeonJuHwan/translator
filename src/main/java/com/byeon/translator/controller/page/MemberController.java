package com.byeon.translator.controller.page;


import com.byeon.translator.controller.request.member.MemberJoinRequest;
import com.byeon.translator.controller.request.member.MemberLoginResponse;
import com.byeon.translator.controller.response.MemberJoinResponse;
import com.byeon.translator.service.member.front.MemberFrontService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberFrontService memberFrontService;

    @GetMapping("/join")
    public String joinPage(Model model) {
        model.addAttribute("member", new MemberJoinResponse());
        return "member/join";
    }

    @PostMapping("/join")
    public String join(@Validated @ModelAttribute("member") MemberJoinRequest request, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.error("error = {}", bindingResult.getAllErrors());
            return "member/join";
        }

        memberFrontService.join(request);

        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginPage(@RequestParam(required = false) String error, Model model) {
        model.addAttribute("login", new MemberLoginResponse());
        model.addAttribute("error", error);
        return "member/login";
    }
}
