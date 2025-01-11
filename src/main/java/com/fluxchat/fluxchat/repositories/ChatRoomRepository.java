package com.fluxchat.fluxchat.repositories;

import com.fluxchat.fluxchat.enteties.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomRepository extends BaseJpaRepository<ChatRoom> {
    Optional<ChatRoom> findByName(String name);
    @Query(value = "SELECT DISTINCT cr FROM ChatRoom cr JOIN cr.participants p WHERE p.id IN :userIds")
    List<ChatRoom> findAllByUserIds(@Param("userIds") List<Long> userIds);
}
