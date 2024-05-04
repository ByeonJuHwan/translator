package com.byeon.translator.service.member.front;

import com.byeon.translator.controller.request.MemberJoinRequest;
import com.byeon.translator.domain.entity.Member;
import com.byeon.translator.service.member.MemberWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberFrontService {

    private final MemberWriteService memberWriteService;

    public void join(MemberJoinRequest request) {
        Member member = Member.of(request.getUserId(), request.getPassword(), request.getName(), null);
        memberWriteService.join(member);
    }
}
