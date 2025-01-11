package com.fluxchat.fluxchat.services;

import com.fluxchat.fluxchat.controllers.dtos.ChatMessageDto;
import com.fluxchat.fluxchat.enteties.ChatRoom;
import com.fluxchat.fluxchat.enteties.Message;
import com.fluxchat.fluxchat.enteties.enums.MessageType;
import com.fluxchat.fluxchat.repositories.MessageRepository;
import com.fluxchat.fluxchat.services.interfaces.AuthService;
import com.fluxchat.fluxchat.services.interfaces.MessageService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final AuthService authService;

    public MessageServiceImpl(MessageRepository messageRepository, AuthService authService) {
        this.messageRepository = messageRepository;
        this.authService = authService;
    }

    @Override
    public List<Message> getMessagesByChatRoomId(Long chatRoomId) {
        return messageRepository.findByChatRoomId(chatRoomId);
    }

    @Override
    public ChatMessageDto saveMessage(String content, MessageType messageType, ChatRoom chatRoom) {
        Message message = Message.builder()
                .sender(authService.getUserFromContext())
                .chatRoom(chatRoom)
                .content(content)
                .messageType(messageType)
                .build();
        return ChatMessageDto.from(messageRepository.save(message));
    }

    @Override
    public ChatMessageDto saveMessageAddUser(ChatRoom chatRoom, String content) {
        Message message = Message.builder()
                .content(content)
                .messageType(MessageType.SYSTEM)
                .chatRoom(chatRoom)
                .build();
        return ChatMessageDto.from(messageRepository.save(message));
    }

    @Override
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    @Override
    public List<ChatMessageDto> getRecentMessages(Long messageId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return messageRepository.findByChatRoomId(pageable, messageId).stream().map(ChatMessageDto::from).toList();
    }
}
