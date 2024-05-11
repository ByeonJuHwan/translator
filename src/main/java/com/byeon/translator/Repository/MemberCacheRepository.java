package com.byeon.translator.Repository;

import com.byeon.translator.dto.MemberCacheDto;
import com.byeon.translator.exception.custom.ConvertJsonException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MemberCacheRepository {
    private final RedisTemplate<String, Object> userRedisTemplate;
    private final static Duration USER_CACHE_TTL = Duration.ofDays(3);
    private final ObjectMapper objectMapper;

    public void setUser(MemberCacheDto member) {
        String key = getKey(member.getUserId());
        try{
            String memberJson = objectMapper.writeValueAsString(member);
            log.info("Set User to Redis {}, {}", key, memberJson);
            userRedisTemplate.opsForValue().set(key, memberJson, USER_CACHE_TTL);
        } catch (JsonProcessingException e) {
            throw new ConvertJsonException("Failed convert Dto To Json");
        }
    }

    public <T> Optional<T> getUser(String userId, Class<T> classType) {
        String key = getKey(userId);
        String MemberJsonData = (String) userRedisTemplate.opsForValue().get(key);

        try {
            if (StringUtils.hasText(MemberJsonData)) {
                return Optional.ofNullable(objectMapper.readValue(MemberJsonData, classType));
            }
            return Optional.empty();
        } catch (JsonProcessingException e) {
            throw new ConvertJsonException("Failed convert Json To Dto");
        }
    }

    private String getKey(String userId) {
        return "USER:" + userId;
    }
}
