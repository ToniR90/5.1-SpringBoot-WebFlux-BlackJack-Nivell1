package com.blackjack.api.controller;


import com.blackjack.api.dto.PlayerRequest;
import com.blackjack.api.model.Player;
import com.blackjack.api.service.PlayerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public Mono<ResponseEntity<Player>> createPlayer(@Valid @RequestBody PlayerRequest playerRequest) {
        return playerService.createPlayer(playerRequest.getName())
                .map(savedPlayer -> ResponseEntity.ok().body(savedPlayer));
    }
}