package com.byeon.translator.consumers;

import com.byeon.translator.config.RabbitMQConfig;
import com.byeon.translator.controller.request.NoteRequest;
import com.byeon.translator.service.RankingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class RankingConsumer {

    private final RankingService rankingService;

    @Transactional
    @RabbitListener(queues = RabbitMQConfig.RANKING_QUEUE, containerFactory = "rabbitListenerContainerFactory")
    public void updateRanking(NoteRequest noteRequest) {
        try {
            rankingService.saveRanking(noteRequest);
        } catch (Exception e) {
            log.error("rabbitMQ occur Exception : {}", e.getMessage());
        }
    }
}
