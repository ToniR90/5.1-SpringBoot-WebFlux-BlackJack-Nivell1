package com.blackjack.api.repository;

import com.blackjack.api.model.Player;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface PlayerRepository extends ReactiveCrudRepository<Player, Long> {

    Flux<Player> findAllByOrderByVictoryRatioDesc();
}