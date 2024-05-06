package com.byeon.translator.security;


import com.byeon.translator.Repository.member.MemberRepository;
import com.byeon.translator.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class SecurityService implements UserDetailsService {

    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findMemberByUserId(username)
                .orElseThrow(() -> new UsernameNotFoundException("아이디가 없습니다 : " + username));

        // TODO redis 에 여기서 member 를 넣어 줘서 DB IO 를 줄여보자


        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("USER");
        return new User(member.getUserId(), member.getPassword(), Collections.singletonList(authority));
    }
}
