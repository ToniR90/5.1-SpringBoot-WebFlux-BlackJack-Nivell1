package com.blackjack.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

import java.time.ZonedDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(ResourceNotFoundException.class)
    public Mono<ResponseEntity<Object>> handleNotFound(ResourceNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage(), "/players/{id}");
    }

    @ExceptionHandler(InvalidGameStateException.class)
    public Mono<ResponseEntity<Object>> handleInvalidGame(InvalidGameStateException ex) {
        return buildResponse(HttpStatus.CONFLICT, ex.getMessage(), "/games/{id}");
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<Object>> handleGeneric(Exception ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error", "unknown");
    }

    private Mono<ResponseEntity<Object>> buildResponse(HttpStatus status, String message, String path) {
        Map<String, Object> body = Map.of(
                "timestamp", ZonedDateTime.now(),
                "status", status.value(),
                "message", message,
                "path", path
        );
        return Mono.just(ResponseEntity.status(status).body(body));
    }
}