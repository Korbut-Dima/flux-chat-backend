package com.fluxchat.fluxchat.controllers;

import com.fluxchat.fluxchat.controllers.dtos.ChatMessageDto;
import com.fluxchat.fluxchat.controllers.dtos.LoadMessagesRequest;
import com.fluxchat.fluxchat.services.interfaces.MessageService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ChatMessageController {
    private final MessageService messageService;

    public ChatMessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @MessageMapping("/chat.loadMessages")
    @SendTo("/topic/chatroom/messages")
    public List<ChatMessageDto> loadMessages(LoadMessagesRequest request) {
        return messageService.getRecentMessages(
                request.getChatRoomId(),
                request.getPage(),
                request.getSize()
        );
    }


}
