package com.byeon.translator.service;

import com.byeon.translator.Repository.MemberCacheRepository;
import com.byeon.translator.Repository.member.MemberRepository;
import com.byeon.translator.config.RabbitMQConfig;
import com.byeon.translator.controller.request.NoteRequest;
import com.byeon.translator.controller.response.NoteResponse;
import com.byeon.translator.domain.entity.Member;
import com.byeon.translator.dto.MemberCacheDto;
import com.byeon.translator.exception.custom.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageQueueService {

    private final RabbitTemplate rabbitTemplate;
    private final MemberCacheRepository memberCacheRepository;
    private final MemberRepository memberRepository;

    @Async
    public void saveMQNote(String sendMessage, String translateMessage, String userId) {
        Member member = memberCacheRepository.getUser(userId, MemberCacheDto.class).map(Member::from)
                .orElseGet(() -> memberRepository.findMemberByUserId(userId).orElseThrow(() -> new MemberNotFoundException("일치하는 회원이 없습니다.")));

        NoteResponse noteResponse = new NoteResponse(sendMessage, translateMessage, member);
        log.info("Start rabbitMQ save note : {}", noteResponse);

        rabbitTemplate.convertAndSend(RabbitMQConfig.NOTE_EXCHANGE, RabbitMQConfig.NOTE_ROUTE_KEY, noteResponse);
    }

    @Async
    public void saveRank(NoteRequest noteRequest) {
        log.info("Start rabbitMQ save rank : {}", noteRequest);
        rabbitTemplate.convertAndSend(RabbitMQConfig.RANKING_EXCHANGE,RabbitMQConfig.RANKING_ROUTE_KEY, noteRequest);
    }
}
