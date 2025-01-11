package com.fluxchat.fluxchat.exceptions.abstracts;

public abstract class AlreadyExistException extends RuntimeException {

    public AlreadyExistException(String message) {
        super(message);
    }

}
