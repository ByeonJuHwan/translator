package com.byeon.translator.controller.request;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteRequest {
    private String sendMessage;
    private String translateMessage;

    public static NoteRequest of(String sendMessage, String translateMessage) {
        return new NoteRequest(sendMessage, translateMessage);
    }
}
