package com.byeon.translator.dto;

import com.byeon.translator.domain.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberCacheDto {
    private Long id;
    private String userId;
    private String name;

    public static MemberCacheDto of(Member member) {
        return new MemberCacheDto(
                member.getId(),
                member.getUserId(),
                member.getName()
        );
    }
}
