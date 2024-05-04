package com.byeon.translator.service.member;

import com.byeon.translator.Repository.member.MemberRepository;
import com.byeon.translator.domain.entity.Member;
import com.byeon.translator.exception.custom.DuplicatedMemberException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberWriteService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void join(Member request){
        // 간단하게 아이디가 다르면 회원가입 가능하게 설정
        memberRepository.findByUserId(request.getUserId()).ifPresent(member -> {
            throw new DuplicatedMemberException("중복된 아이디 입니다.");
        });

        // password 인코딩
        request.setPassword(passwordEncoder.encode(request.getPassword()));

        // 회원 저장
        Member savedMember = memberRepository.save(request);
        log.info("savedMember = {}", savedMember);
    }
}
