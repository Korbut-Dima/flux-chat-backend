CREATE TABLE chat_room (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE chat_room_users (
    chat_room_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    PRIMARY KEY (chat_room_id, user_id),
    CONSTRAINT fk_chat_room FOREIGN KEY (chat_room_id) REFERENCES chat_room(id) ON DELETE CASCADE,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Create Message Table
CREATE TABLE message (
    id SERIAL PRIMARY KEY,
    sender_id BIGINT,
    chat_room_id BIGINT NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    message_type VARCHAR(50) NOT NULL,
    CONSTRAINT fk_sender FOREIGN KEY (sender_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_chat_room FOREIGN KEY (chat_room_id) REFERENCES chat_room (id) ON DELETE CASCADE
);
