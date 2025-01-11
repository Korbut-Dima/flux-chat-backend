package com.fluxchat.fluxchat.services.interfaces;

import com.fluxchat.fluxchat.controllers.dtos.UserChatParticipantDto;
import com.fluxchat.fluxchat.controllers.dtos.UserDto;
import com.fluxchat.fluxchat.enteties.User;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.Collections;
import java.util.List;


public interface UserService {
    List<User> getAll();
    List<User> getAllByIds(Collection<Long> ids);

    User findById(Long id);

    User create(User user);

    void delete(Long id);

    User update(Long id, User user);

    List<UserChatParticipantDto> findAllUsersOutOfChat(Long chatId);

    Page<User> getAllPages(int page, int pageSize, String sort, Long mafId);
}
