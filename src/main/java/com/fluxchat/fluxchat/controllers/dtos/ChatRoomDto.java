package com.fluxchat.fluxchat.controllers.dtos;

import com.fluxchat.fluxchat.enteties.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoomDto {
    private Long id;
    private String name;
    private List<UserChatParticipantDto> participants;

    public static ChatRoomDto from(ChatRoom chatRoom) {
        List<UserChatParticipantDto> participants = chatRoom.getParticipants().stream()
                .map(UserChatParticipantDto::from)
                .toList();
        return ChatRoomDto.builder()
                .id(chatRoom.getId())
                .name(chatRoom.getName())
                .participants(participants)
                .build();
    }
}
