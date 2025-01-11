package com.fluxchat.fluxchat.controllers;

import com.fluxchat.fluxchat.controllers.dtos.ChatRoomDto;
import com.fluxchat.fluxchat.controllers.dtos.ChatRoomShortDto;
import com.fluxchat.fluxchat.controllers.dtos.CreateChatRoomDto;
import com.fluxchat.fluxchat.services.interfaces.ChatRoomService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/chats")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    public ChatRoomController(ChatRoomService chatRoomService) {
        this.chatRoomService = chatRoomService;
    }

    @GetMapping
    public List<ChatRoomShortDto> getAllChatRooms() {
        return chatRoomService.getAllChatRooms();
    }

    @GetMapping("/{id}")
    public Optional<ChatRoomDto> getChatById(@PathVariable Long id) {
        return chatRoomService.getChatRoomDtoById(id);
    }

    @PostMapping("/{id}/add")
    public ChatRoomDto addParticipants(@PathVariable Long id, @RequestBody CreateChatRoomDto chatRoomDto) {
        return chatRoomService.addParticipants(id, chatRoomDto.getParticipantIds());
    }

    @PostMapping
    public ChatRoomShortDto createChatRoom(@RequestBody CreateChatRoomDto chatRoom) {
        return chatRoomService.createChatRoom(chatRoom);
    }
}
