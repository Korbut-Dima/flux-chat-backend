package com.fluxchat.fluxchat.controllers;

import com.fluxchat.fluxchat.controllers.dtos.ChatMessageDto;
import com.fluxchat.fluxchat.controllers.dtos.CreateMessageDto;
import com.fluxchat.fluxchat.enteties.ChatRoom;
import com.fluxchat.fluxchat.services.interfaces.ChatRoomService;
import com.fluxchat.fluxchat.services.interfaces.MessageService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;
    private final ChatRoomService chatRoomService;
    private final SimpMessagingTemplate messagingTemplate;

    public MessageController(MessageService messageService, ChatRoomService chatRoomService, SimpMessagingTemplate messagingTemplate) {
        this.messageService = messageService;
        this.chatRoomService = chatRoomService;
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping
    public ChatMessageDto createChatRoom(@RequestBody CreateMessageDto messageDto) {
        ChatRoom chatRoom = chatRoomService.getChatRoomById(messageDto.getChatId()).orElseThrow(() -> new RuntimeException("No Chat found"));
        ChatMessageDto message = messageService.saveMessage(messageDto.getContent(), messageDto.getMessageType(), chatRoom);
        messagingTemplate.convertAndSend("/topic/chatroom/messages", message);
        return message;
    }
}
