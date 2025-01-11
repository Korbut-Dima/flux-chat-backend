package com.fluxchat.fluxchat.controllers.dtos;

import com.fluxchat.fluxchat.enteties.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserChatParticipantDto {
    private Long id;
    private String firstName;
    private String lastName;

    public static UserChatParticipantDto from(User user) {
        return UserChatParticipantDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }
}
