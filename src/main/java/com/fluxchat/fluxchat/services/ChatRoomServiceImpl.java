package com.fluxchat.fluxchat.services;

import com.fluxchat.fluxchat.controllers.dtos.ChatRoomDto;
import com.fluxchat.fluxchat.controllers.dtos.ChatRoomShortDto;
import com.fluxchat.fluxchat.controllers.dtos.CreateChatRoomDto;
import com.fluxchat.fluxchat.enteties.ChatRoom;
import com.fluxchat.fluxchat.enteties.User;
import com.fluxchat.fluxchat.repositories.ChatRoomRepository;
import com.fluxchat.fluxchat.services.interfaces.AuthService;
import com.fluxchat.fluxchat.services.interfaces.ChatRoomService;
import com.fluxchat.fluxchat.services.interfaces.MessageService;
import com.fluxchat.fluxchat.services.interfaces.UserService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final AuthService authService;
    private final UserService userService;
    private final SimpMessagingTemplate messagingTemplate;
    private final MessageService messageService;

    public ChatRoomServiceImpl(ChatRoomRepository chatRoomRepository, AuthService authService, UserService userService, SimpMessagingTemplate messagingTemplate, MessageService messageService) {
        this.chatRoomRepository = chatRoomRepository;
        this.authService = authService;
        this.userService = userService;
        this.messagingTemplate = messagingTemplate;
        this.messageService = messageService;
    }

    public List<ChatRoomShortDto> getAllChatRooms() {
        Long id = authService.getUserFromContext().getId();
        return chatRoomRepository.findAllByUserIds(Collections.singletonList(id)).stream().map(ChatRoomShortDto::from).toList();
    }

    @Override
    public Optional<ChatRoomDto> getChatRoomDtoById(Long id) {
        return chatRoomRepository.findById(id).map(ChatRoomDto::from);
    }

    @Override
    public Optional<ChatRoom> getChatRoomById(Long id) {
        return chatRoomRepository.findById(id);
    }

    @Override
    public ChatRoomDto addParticipants(Long id, List<Long> participants) {
        Collection<User> users = userService.getAllByIds(participants);
        ChatRoom chatRoom = chatRoomRepository.findById(id).orElseThrow(() -> new RuntimeException("No chat found"));

        chatRoom.getParticipants().addAll(users);
        ChatRoom updatedChatRoom = chatRoomRepository.save(chatRoom);

        users.forEach(user -> {
            String context = String.format("Користувач %s %s був доданий до чату", user.getFirstName(), user.getLastName());
            messagingTemplate.convertAndSend("/topic/chatroom/messages",
                    messageService.saveMessageAddUser(updatedChatRoom, context));
        });
        return ChatRoomDto.from(updatedChatRoom);
    }

    public Optional<ChatRoomDto> getChatRoomByName(String name) {
        return chatRoomRepository.findByName(name).map(ChatRoomDto::from);
    }

    public ChatRoomShortDto createChatRoom(CreateChatRoomDto chatRoom) {
        List<User> participants = new ArrayList<>();
        participants.add(authService.getUserFromContext());
        if (!CollectionUtils.isEmpty(chatRoom.getParticipantIds())) {
            participants.addAll(userService.getAllByIds(chatRoom.getParticipantIds()));
        }
        ChatRoom newChatRoom = ChatRoom.builder()
                .name(chatRoom.getName())
                .participants(participants)
                .build();
        return ChatRoomShortDto.from(chatRoomRepository.save(newChatRoom));
    }

    public void deleteChatRoom(Long id) {
        chatRoomRepository.deleteById(id);
    }
}
