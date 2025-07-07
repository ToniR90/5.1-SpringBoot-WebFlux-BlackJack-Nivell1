package com.blackjack.api.service;

import com.blackjack.api.expection.ResourceNotFoundException;
import com.blackjack.api.model.Player;
import com.blackjack.api.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;

    public Mono<Player> createPlayer(String name){
        Player newPlayer = new Player();
        newPlayer.setName(name);
        newPlayer.setPlayedGames(0);
        newPlayer.setWonGames(0);
        newPlayer.setVictoryRatio(0);

        return playerRepository.save(newPlayer);
    }

    public Mono<Player> getPlayerById(Long playerId) {
        return playerRepository.findById(playerId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Player not found")));
    }

    public Flux<Player> getRanking() {
        return playerRepository.findAllByOrderByVictoryRatioDesc();
    }

    public Mono<Player> registerGameResult(Long playerId, boolean won) {
        return getPlayerById(playerId)
                .map(player -> {
                    player.registerGame(won);
                    return player;
                })
                .flatMap(playerRepository::save);
    }
}
