package com.blackjack.api.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class Deck {

    private final List<Card> deck = new ArrayList<>();

    public Deck() {
        for(Card.Suit suit : Card.Suit.values()) {
            for(Card.Rank rank : Card.Rank.values()){
                deck.add(new Card(suit , rank));
            }
        }
    }

    public Deck(List<Card> remainingCards) {
        this.deck.addAll(remainingCards);
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

    public List<Card> remainingCards() {
        return new ArrayList<>(deck);
    }

    public boolean isEmpty(){
        return deck.isEmpty();
    }

    @Override
    public String toString() {
        return deck.stream()
                .map(Card::toString)
                .collect(Collectors.joining(", "));
    }
}
