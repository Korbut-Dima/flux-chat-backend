package com.fluxchat.fluxchat.exceptions;

import com.fluxchat.fluxchat.exceptions.abstracts.NotFoundException;

public class UserNotFoundException extends NotFoundException {

    public UserNotFoundException(String message) {
        super(message);
    }
}
