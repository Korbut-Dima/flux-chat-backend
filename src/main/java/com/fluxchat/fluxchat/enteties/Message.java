package com.fluxchat.fluxchat.enteties;

import com.fluxchat.fluxchat.enteties.enums.MessageType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @ManyToOne
    @JoinColumn(name = "chat_room_id", nullable = false)
    private ChatRoom chatRoom;

    @Column(nullable = false)
    @Length(max = 200)
    private String content;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private MessageType messageType;

    public String getSenderRepresentation() {
        if (Objects.nonNull(this.sender)) {
            return this.sender.getFirstName() + this.sender.getLastName();
        }
        return null;
    }

    public Long getSenderIdSafe() {
        if (Objects.nonNull(this.sender)) {
            return this.sender.getId();
        }
        return null;
    }
}
