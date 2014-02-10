package com.fisherevans.vtc.oop.war;

import java.util.ArrayList;
import java.util.Collections;

public class Deck
{
	ArrayList<Card> _cards; // never null
	
	/** Creates cards list deck */
	public Deck()
	{
		empty();
	}
	
	/** empties deck, draws 52 cards then shuffles. */
	public void init()
	{
		empty();
		addDefault52();
		shuffle();
	}
	
	/** MEpties the deck */
	public void empty()
	{
		_cards = new ArrayList<Card>();
	}
	
	/** Add cards to deck in order High to low in order Clube, Spade, Heart, Dia */
	public void addDefault52()
	{
		for(int index = 1;index <= 13;index++)
		{
			_cards.add(new Card(Card.Suit.Club, index));
			_cards.add(new Card(Card.Suit.Spade, index));
			_cards.add(new Card(Card.Suit.Heart, index));
			_cards.add(new Card(Card.Suit.Diamond, index));
		}
	}
	
	/** Randomizes order of list */
	public void shuffle()
	{
		Collections.shuffle(_cards);
	}
	
	/** Add card to last id of lsist
	 * @param newCard Card to add to bottom
	 */
	public void addCardTop(Card newCard)
	{
		_cards.add(newCard);
	}
	
	/** Shift all cards up one index. Adds new card to bottom (first id) 
	 * @param newCard Card to add to bottom
	 */
	public void addCardBottom(Card newCard)
	{
		ArrayList<Card> tempCards = new ArrayList<Card>();
		tempCards.add(newCard);
		for(Card card:_cards)
		{
			tempCards.add(card);
		}
		_cards = tempCards;
	}
	
	/** Checks to see if card matching rank and suit exists in deck already.
	 * @param card Card to check for
	 * @return True for yes, false for no.
	 */
	public boolean hasCard(Card card)
	{
		return _cards.contains(card);
	}
	
	/**
	 * @return true if deck is empty, false if there are > 0 cards.
	 */
	public boolean isEmpty()
	{
		return _cards.isEmpty();
	}
	
	/**
	 * @return number of cards in deck
	 */
	public int getSize()
	{
		return _cards.size();
	}
	
	/** Returns new card onject describing top card of deck.
	 * @return Top card.
	 */
	public Card seeTopCard()
	{
		Card tempCard = _cards.get(_cards.size()-1);
		return new Card(tempCard.getSuit(), tempCard.getRank());
	}
	
	/** Removes top card (last index) fro list
	 */
	public void removeTopCard()
	{
		_cards.remove(_cards.size()-1);
	}
	
	/** Puts seeTopCard in temp var, call removeTopCard, return top card.
	 * @return Top card that was removed fromd eck.
	 */
	public Card drawCardTop()
	{
		Card tempCard = _cards.get(_cards.size()-1);
		removeTopCard();
		return tempCard;
	}
	
	/** Epties deck, copies deck given (new objects.)
	 * @param deck Deck to mimic
	 */
	public void setDeckAs(Deck deck)
	{
		empty();
		while(!deck.isEmpty())
		{
			addCardTop(deck.drawCardTop());
		}
	}
	
	/** Adds deck to top of deck (new objects)
	 * @param deck Deck to append to current
	 */
	public void addToTop(Deck deck)
	{
		while(!deck.isEmpty())
		{
			addCardTop(deck.drawCardTop());
		}
	}
	
	/** least each car din order of bottom to top.
	   @return String describing it.
	 */
	public String toString()
	{
		String str = "";
		for(Card card:_cards)
		{
			str += "[" + card.toString() + "] ";
		}
		return str;
	}
}
