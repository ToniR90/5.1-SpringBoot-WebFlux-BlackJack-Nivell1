package com.blackjack.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Card {

    private Suit suit;
    private Rank rank;

    public enum Suit {
        HEARTS , DIAMONDS , CLUBS , SPADES
    }

    public enum Rank {
        TWO(2) , THREE(3) , FOUR(4) , FIVE(5) ,
        SIX(6) , SEVEN(7) , EIGHT(8) , NINE(9) ,
        TEN(10) , JACK(10) , QUEEN(10) , KING(10) ,
        ACE(11);

        private final int value;

        Rank(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public boolean isAce() {
            return this == ACE;
        }
    }

    public int getCardValue() {
        return rank.getValue();
    }

    public boolean isAce() {
        return rank.isAce();
    }

    @Override
    public String toString() {
        return rank.name() + " of " + suit.name() + "\n" +
                "Value: " + rank.getValue() + "\n";
    }
}
