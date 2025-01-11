package com.fluxchat.fluxchat.services.interfaces;

import com.fluxchat.fluxchat.controllers.dtos.ChatRoomDto;
import com.fluxchat.fluxchat.controllers.dtos.ChatRoomShortDto;
import com.fluxchat.fluxchat.controllers.dtos.CreateChatRoomDto;
import com.fluxchat.fluxchat.enteties.ChatRoom;

import java.util.List;
import java.util.Optional;

public interface ChatRoomService {
    List<ChatRoomShortDto> getAllChatRooms();

    Optional<ChatRoomDto> getChatRoomDtoById(Long id);
    Optional<ChatRoom> getChatRoomById(Long id);

    Optional<ChatRoomDto> getChatRoomByName(String name);

    ChatRoomShortDto createChatRoom(CreateChatRoomDto chatRoom);

    void deleteChatRoom(Long id);
    ChatRoomDto addParticipants(Long id, List<Long> participants);
}
