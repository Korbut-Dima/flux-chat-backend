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
public class ChatRoomShortDto {
    private Long id;
    private String name;
    public static ChatRoomShortDto from(ChatRoom chatRoom) {
        return ChatRoomShortDto.builder()
                .id(chatRoom.getId())
                .name(chatRoom.getName())
                .build();
    }
}
