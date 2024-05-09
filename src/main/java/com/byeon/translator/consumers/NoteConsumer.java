package com.byeon.translator.consumers;


import com.byeon.translator.config.RabbitMQConfig;
import com.byeon.translator.controller.response.NoteResponse;
import com.byeon.translator.service.note.NoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class NoteConsumer {

    private final NoteService noteService;

    @Transactional
    @RabbitListener(queues = RabbitMQConfig.NOTE_QUEUE,  containerFactory = "rabbitListenerContainerFactory")
    public void saveNote(NoteResponse noteResponse) {
        try {
            noteService.saveNote(noteResponse);
        } catch (Exception e) {
            log.error("rabbitMQ occur Exception : {}", e.getMessage());
        }
    }
}
