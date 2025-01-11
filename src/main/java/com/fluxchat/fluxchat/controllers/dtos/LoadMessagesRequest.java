package com.fluxchat.fluxchat.controllers.dtos;

import lombok.Data;

@Data
public class LoadMessagesRequest {
    private Long chatRoomId;
    private int page;
    private int size;
}
