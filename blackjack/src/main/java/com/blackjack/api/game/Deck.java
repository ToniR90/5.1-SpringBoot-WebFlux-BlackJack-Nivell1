package com.blackjack.api.game;

import com.blackjack.api.model.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {

    private final List<Card> deck = new ArrayList<>();

    public Deck() {
        for(Card.Suit suit : Card.Suit.values()) {
            for(Card.Rank rank : Card.Rank.values()){
                deck.add(new Card(suit , rank));
            }
        }
    }

    public List<Card> getDeck(){
        return deck;
    }

    public void shuffle(){
        Collections.shuffle(deck);
    }

    public Card drawCard(){
        if(deck.isEmpty()){
            throw new IllegalStateException("There's no more cards in the deck");
        }
        return deck.remove(0);
    }

    public int remainingCards() {
        return deck.size();
    }

    public boolean isEmpty(){
        return deck.isEmpty();
    }
}
