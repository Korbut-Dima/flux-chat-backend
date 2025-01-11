package com.fluxchat.fluxchat.controllers.dtos;

import com.fluxchat.fluxchat.enteties.Message;
import com.fluxchat.fluxchat.enteties.enums.MessageType;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessageDto {
    private String sender;
    private Long senderId;
    private String content;
    private LocalDateTime createdAt;
    private String chatRoom;
    private MessageType type;

    public static ChatMessageDto from(Message message) {

        return ChatMessageDto.builder()
                .sender(message.getSenderRepresentation())
                .senderId(message.getSenderIdSafe())
                .content(message.getContent())
                .createdAt(message.getCreatedAt())
                .chatRoom(message.getChatRoom().getName())
                .type(message.getMessageType())
                .build();
    }
}
