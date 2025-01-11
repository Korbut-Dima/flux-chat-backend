package com.fluxchat.fluxchat.exceptions.abstracts;

public abstract class NotAllowedActionException extends RuntimeException {

    public NotAllowedActionException(String message) {
        super(message);
    }
}
