package com.fluxchat.fluxchat.exceptions.abstracts;

public abstract class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}
