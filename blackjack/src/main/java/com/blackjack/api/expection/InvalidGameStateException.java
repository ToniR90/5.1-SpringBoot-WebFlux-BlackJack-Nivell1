package com.blackjack.api.expection;

public class InvalidGameStateException extends RuntimeException {
    public InvalidGameStateException(String message) {
        super(message);
    }
}
