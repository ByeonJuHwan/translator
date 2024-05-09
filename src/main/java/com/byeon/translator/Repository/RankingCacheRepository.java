package com.byeon.translator.Repository;

import com.byeon.translator.controller.request.NoteRequest;
import com.byeon.translator.exception.custom.ConvertJsonException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Repository
@RequiredArgsConstructor
public class RankingCacheRepository {

    private final RedisTemplate<String, Object> rankRedisTemplate;
    private final ObjectMapper objectMapper;
    private final static String RANKING_KEY = "RANKING";


    public void setRanking(NoteRequest noteRequest) {
        log.info("update ranking {}", noteRequest);
        try {
            String noteRequestJson = objectMapper.writeValueAsString(noteRequest);
            rankRedisTemplate.opsForZSet().incrementScore(getKey(), noteRequestJson, 1);
        } catch (JsonProcessingException e) {
            throw new ConvertJsonException("Failed convert Dto To Json");
        }
    }

    public List<NoteRequest> getRanking() {
        return Objects.requireNonNull(rankRedisTemplate.opsForZSet()
                        .reverseRange(getKey(), 0, 4)).stream()
                .map(o -> {
                    try {
                        return objectMapper.readValue((String) o, NoteRequest.class);
                    } catch (JsonProcessingException e) {
                        throw new ConvertJsonException("Failed to convert JSON to NoteRequest");
                    }
                })
                .collect(Collectors.toList());
    }

    private String getKey() {
        return RANKING_KEY;
    }
}
