package com.byeon.translator.Repository.member;

import com.byeon.translator.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findMemberByUserId(String userId);

    Optional<Member> findByUserId(String userName);
}
