package com.blackjack.api.controller;


import com.blackjack.api.dto.GameResponse;
import com.blackjack.api.dto.PlayerRequest;
import com.blackjack.api.dto.PlayerResponse;
import com.blackjack.api.service.PlayerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/players")
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService){
        this.playerService = playerService;
    }

    @PostMapping
    public Mono<ResponseEntity<PlayerResponse>> createPlayer(@Valid @RequestBody PlayerRequest playerRequest) {
        return playerService.createPlayer(playerRequest.getName())
                .map(PlayerResponse::from)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<PlayerResponse>> getPlayerById(@PathVariable Long id) {
        return playerService.getPlayerById(id)
                .map(PlayerResponse::from)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @GetMapping
    public Flux<PlayerResponse> getAllPlayers() {
        return playerService.getAllPlayers()
                .map(PlayerResponse::from);
    }

    @GetMapping("/ranking")
    public Flux<PlayerResponse> getRanking() {
        return playerService.getRanking()
                .map(PlayerResponse::from);
    }
}