package com.byeon.translator.Repository;

import com.byeon.translator.dto.MemberCacheDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MemberCacheRepository {
    private final RedisTemplate<String, Object> userRedisTemplate;
    private final static Duration USER_CACHE_TTL = Duration.ofDays(3);

    public void setUser(MemberCacheDto member) {
        String key = getKey(member.getUserId());
        log.info("Set User to Redis {}, {}", key, member);
        userRedisTemplate.opsForValue().setIfAbsent(key, member, USER_CACHE_TTL);
    }

    public Optional<MemberCacheDto> getUser(String userId) {
        String key = getKey(userId);
        MemberCacheDto member = (MemberCacheDto) userRedisTemplate.opsForValue().get(key);
        log.info("Get data to Redis {}, {}", key, member);
        return Optional.ofNullable(member);
    }

    private String getKey(String userId) {
        return "USER:" + userId;
    }
}
