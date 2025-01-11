package com.fluxchat.fluxchat.controllers.dtos;

import com.fluxchat.fluxchat.enteties.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateMessageDto {
    private String content;
    private MessageType messageType;
    private Long chatId;
}
