package com.byeon.translator.service;

import com.byeon.translator.Repository.RankingCacheRepository;
import com.byeon.translator.controller.request.NoteRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RankingService {

    private final RankingCacheRepository rankingCacheRepository;

    public void saveRanking(NoteRequest noteRequest) {
        rankingCacheRepository.setRanking(noteRequest);
    }

    public List<NoteRequest> getTopFiveRank() {
        return rankingCacheRepository.getRanking();
    }
}