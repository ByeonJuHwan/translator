package com.byeon.translator.security;


import com.byeon.translator.Repository.MemberCacheRepository;
import com.byeon.translator.Repository.member.MemberRepository;
import com.byeon.translator.domain.entity.Member;
import com.byeon.translator.dto.MemberCacheDto;
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
    private final MemberCacheRepository memberCacheRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findMemberByUserId(username)
                .orElseThrow(() -> new UsernameNotFoundException("아이디가 없습니다 : " + username));

        // redis 에 member 정보 저장
        memberCacheRepository.setUser(MemberCacheDto.of(member));

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("USER");
        return new User(member.getUserId(), member.getPassword(), Collections.singletonList(authority));
    }
}
