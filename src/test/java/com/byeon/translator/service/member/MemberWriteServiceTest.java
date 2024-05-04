package com.byeon.translator.service.member;

import com.byeon.translator.Repository.member.MemberRepository;
import com.byeon.translator.domain.entity.Member;
import com.byeon.translator.exception.custom.DuplicatedMemberException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("회원 관련 로직")
@ExtendWith(MockitoExtension.class)
class MemberWriteServiceTest {

    @InjectMocks
    private MemberWriteService sut;

    @Mock
    private MemberRepository memberRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("[회원가입] 성공")
    void join() {
        // given
        Member member = Member.builder()
                .userId("test")
                .password("test")
                .build();

        String encodedPassword = "encodedPassword";

        // when
        when(passwordEncoder.encode(member.getPassword())).thenReturn(encodedPassword);
        when(memberRepository.save(any(Member.class))).thenReturn(member);

        assertDoesNotThrow(() -> sut.join(member));
        verify(passwordEncoder).encode("test");
        verify(memberRepository).save(member);
    }

    @Test
    @DisplayName("[회원가입] 아이디 비밀번호가 동시에 중복시 에러 발생")
    void 중복회원_오류_테스트() {
        // given
        Member request = Member.builder()
                .userId("test")
                .build();

        Member existingMember = Member.builder()
                .userId("test")
                .build();

        // when
        when(memberRepository.findByUserId(request.getUserId()))
                .thenReturn(Optional.of(existingMember));

        // 검증
        assertThrows(DuplicatedMemberException.class , ()->{
            sut.join(request);
        });
    }
}