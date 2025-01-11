package com.fluxchat.fluxchat.repositories;

import com.fluxchat.fluxchat.enteties.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends BaseJpaRepository<User> {

    Optional<User> findByEmail(String email);

    Optional<User> findByPhone(String phone);

    @Query(value = "SELECT u.* FROM users as u WHERE NOT EXISTS(SELECT * FROM chat_room_users cru WHERE cru.chat_room_id = :chatId AND cru.user_id = u.id)", nativeQuery = true)
    List<User> findAllOutOfChatRoom(@Param("chatId") Long chatId);
}
