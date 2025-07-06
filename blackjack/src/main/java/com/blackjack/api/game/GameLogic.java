package com.blackjack.api.game;

import com.blackjack.api.model.Card;
import com.blackjack.api.model.Game;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GameLogic {

    public int calculateHandValue(List<Card> hand) {
        int handValue = 0;
        int aceCount = 0;

        for(Card card : hand){
            int cardValue = card.getCardValue();
            handValue += cardValue;

            if(card.getRank() == Card.Rank.ACE) {
                aceCount++;
            }
        }
        while(handValue > 21 && aceCount > 0){
            handValue -= 10;
            aceCount--;
        }
        return handValue;
    }

    public boolean isBlackjack(List<Card> hand) {
        if (hand.size() != 2) return false;

        boolean hasAce = false;
        boolean hasTenValue = false;

        for (Card card : hand) {
            Card.Rank rank = card.getRank();
            if (rank == Card.Rank.ACE) hasAce = true;
            else if (rank == Card.Rank.TEN || rank == Card.Rank.JACK ||
                    rank == Card.Rank.QUEEN || rank == Card.Rank.KING) hasTenValue = true;
        }

        return hasAce && hasTenValue;
    }

    public boolean isBust(List<Card> hand){
        return calculateHandValue(hand) > 21;
    }

    public boolean shouldDealerDraw(List<Card> dealerHand){
        return calculateHandValue(dealerHand) < 17;
    }

    public Game.GameStatus determineGameOutcome(List<Card> playerHand, List<Card> dealerHand) {
        int playerScore = calculateHandValue(playerHand);
        int dealerScore = calculateHandValue(dealerHand);

        boolean playerBlackjack = isBlackjack(playerHand);
        boolean dealerBlackjack = isBlackjack(dealerHand);

        if (playerBlackjack && !dealerBlackjack) return Game.GameStatus.WIN;
        if (dealerBlackjack && !playerBlackjack) return Game.GameStatus.LOSS;

        if (playerScore > 21) return Game.GameStatus.LOSS;
        if (dealerScore > 21) return Game.GameStatus.WIN;

        if (playerScore > dealerScore) return Game.GameStatus.WIN;
        if (playerScore < dealerScore) return Game.GameStatus.LOSS;

        return Game.GameStatus.DRAW;
    }
}