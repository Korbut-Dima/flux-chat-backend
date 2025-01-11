package com.fluxchat.fluxchat.services.interfaces;

import com.fluxchat.fluxchat.controllers.dtos.ChatMessageDto;
import com.fluxchat.fluxchat.enteties.ChatRoom;
import com.fluxchat.fluxchat.enteties.Message;
import com.fluxchat.fluxchat.enteties.enums.MessageType;

import java.util.List;

public interface MessageService {
    List<Message> getMessagesByChatRoomId(Long chatRoomId);

    ChatMessageDto saveMessage(String content, MessageType messageType, ChatRoom chatRoom);
    ChatMessageDto saveMessageAddUser(ChatRoom chatRoom, String content);

    List<Message> getAllMessages();

    List<ChatMessageDto> getRecentMessages(Long messageId, int page, int size);
}
