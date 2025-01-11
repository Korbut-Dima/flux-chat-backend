package com.fluxchat.fluxchat.repositories;

import com.fluxchat.fluxchat.enteties.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByChatRoomId(Long chatRoomId);
    List<Message> findByChatRoomId(Pageable pageable, Long chatRoomId);
}
