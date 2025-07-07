package com.blackjack.api.controller;

import com.blackjack.api.dto.GameRequest;
import com.blackjack.api.dto.GameResponse;
import com.blackjack.api.service.GameService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/games")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @PostMapping
    public Mono<ResponseEntity<GameResponse>> startGame(@Valid @RequestBody GameRequest gameRequest) {
        return gameService.startNewGame(gameRequest.getPlayerId())
                .map(GameResponse::from)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<GameResponse>> getGame(@PathVariable String id) {
        return gameService.getGameById(id)
                .map(GameResponse::from)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @PutMapping("/{id}/hit")
    public Mono<ResponseEntity<GameResponse>> playerHits(@PathVariable String id) {
        return gameService.playerHits(id)
                .map(GameResponse::from)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{id}/stand")
    public Mono<ResponseEntity<GameResponse>> playerStands(@PathVariable String id) {
        return gameService.playerStands(id)
                .map(GameResponse::from)
                .map(ResponseEntity::ok);
    }
}
