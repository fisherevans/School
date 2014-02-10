package info.fisherevans.vtc.oop.deck;

import java.util.ArrayList;
import java.util.Collections;

import info.fisherevans.vtc.oop.deck.Card.Suit;
import info.fisherevans.vtc.oop.deck.es.DupeCardException;
import info.fisherevans.vtc.oop.deck.es.EmptyDeckException;
import info.fisherevans.vtc.oop.deck.es.FullDeckException;

/** Holds an array of 52 card objects. no card can be repeated. there is a set order to the cards.
 * @author fisher evans
 *
 */
public class Deck
{
	private Card[] _cards = new Card[52];
	private int lastIndex = 0;
	private Suit[] _suits = { Suit.Club, Suit.Diamond, Suit.Heart, Suit.Spade };
	
	/** Creates a brand new deck, in the order of Ace through King in the order Club, Diamond, Heart, Spade */
	public void createNewDeck()
	{
		int index = 0;
		for(Suit curSuit:_suits)
		{
			for(int tempRank = 1;tempRank <= 13;tempRank++)
			{
				_cards[index++] = new Card(curSuit, tempRank);
			}
		}
		
		lastIndex = index - 1;
	}
	
	/** Empties the deck */
	public void emptyDeck()
	{
		lastIndex = -1;
	}
	
	/** Randomizes the order of the available cards in the deck */
	public void shuffleDeck()
	{
		if(lastIndex != 0)
		{
			Card swapCard;
			int swapIndex;
			for(int curIndex = 0;curIndex <= lastIndex;curIndex++)
			{
				swapIndex = (int)((lastIndex-curIndex)*Math.random()) + 1;
				swapCard = _cards[swapIndex];
				_cards[swapIndex] = _cards[curIndex];
				_cards[curIndex] = swapCard;
			}
		}
	}
	
	/** Checks to see if the deck is empty.
	 * @return returns true if there are no more available cards in the deck. false otherwise */
	public boolean isEmtpy()
	{
		return (lastIndex  == -1);
	}
	
	/** Returns the card on the top of the avail cards within the deck.
	 * @return The top card
	 * @throws EmptyDeckException this is thrown if there are no cards available in the deck.
	 */
	public Card seeTopCard() throws EmptyDeckException
	{
		if(lastIndex == -1)
		{
			throw new EmptyDeckException();
		}
		return new Card(_cards[lastIndex].getSuit(), _cards[lastIndex].getRank());
	}
	
	/** Removes the top card of the deck from th available selection.
	 * @throws EmptyDeckException  this is thrown if there are no cards available in the deck.
	 */
	public void removeTopCard() throws EmptyDeckException
	{
		if(lastIndex == -1)
		{
			throw new EmptyDeckException();
		}
		lastIndex -= 1;
	}

	/** This is a combination of removetopCard and seeTopCard. It returns the result of seeTopCard before issuing removeTopCard.
	 * @return The top card
	 * @throws EmptyDeckException this is thrown if there are no cards available in the deck.
	 */
	public Card drawTopCard() throws EmptyDeckException
	{
		Card drawnCard = seeTopCard();
		removeTopCard();
		return drawnCard;
	}
	
	/** Adds a new card to the deck.
	 * @param newCard the same rank and suit combo MUST NOT be present with the available deck already
	 * @throws DupeCardException this is thrown if the card is already present int he deck.
	 * @throws FullDeckException this is thrown if the deck is full, and you are not able to add more cards.
	 */
	public void addCard(Card newCard) throws DupeCardException, FullDeckException
	{
		if(lastIndex == 51)
		{
			throw new FullDeckException();
		}
		
		if(isCardPresent(newCard))
		{
			throw new DupeCardException();
		}
		
		lastIndex += 1;
		_cards[lastIndex] = new Card(newCard.getSuit(), newCard.getRank());
	}
	
	/** Adds a new card to the bottom of the deck deck.
	 * @param newCard the same rank and suit combo MUST NOT be present with the available deck already
	 * @throws DupeCardException this is thrown if the card is already present int he deck.
	 * @throws FullDeckException this is thrown if the deck is full, and you are not able to add more cards.
	 */
	public void addCardToBottom(Card newCard) throws DupeCardException, FullDeckException
	{
		if(lastIndex == 51)
		{
			throw new FullDeckException();
		}
		
		if(isCardPresent(newCard))
		{
			throw new DupeCardException();
		}
		
		for(int index = lastIndex;index > -1;index++)
		{
			_cards[lastIndex+1] = _cards[lastIndex];
		}
		lastIndex += 1;
		_cards[0] = new Card(newCard.getSuit(), newCard.getRank());
	}
	
	public boolean isCardPresent(Card card)
	{
		for(int index = 0;index <= lastIndex;index++)
		{
			if(_cards[index].getRank() == card.getRank() && _cards[index].getSuit() == card.getSuit())
			{
				return true;
			}
		}
		return false;
	}
	
	/** sumeraizes the deck
	 * @return Returns each toString of each card in order, bottom to top, then the total number of cards.
	 */
	public String toString()
	{
		String deckString = "";
		for(int curIndex = 0;curIndex <= lastIndex;curIndex++)
		{
			deckString += _cards[curIndex].toString() + "\n";
		}
		deckString += "Total number of cards: " + (lastIndex+1);
		return deckString;
	}
	
	/** a simple test method that makes sure all methods work appropriatly. */
	public void test()
	{
		System.out.println("\nNEW DECK\n");
		createNewDeck();
		System.out.println(toString());
		
		System.out.println("\nSHUFFLE\n");
		shuffleDeck();
		System.out.println(toString());

		System.out.println("\nDRAW 27 CARDS\n");
		for(int draw = 0;draw < 27;draw++)
		{
			try
			{
				System.out.println("   Drew: " + drawTopCard());
			}
			catch (EmptyDeckException e)
			{
				System.out.println("   [ERROR] Cannot draw from an empty deck.");
			}
		}
		System.out.println("\nAFTER DRAWS\n");
		System.out.println(toString());

		System.out.println("\nLOOK TOP, REMOVE TOP, LOOK TOP\n");
		try
		{
			System.out.println(seeTopCard());
			removeTopCard();
			System.out.println(seeTopCard());
		}
		catch (EmptyDeckException e)
		{
			System.out.println("[ERROR] Tried to access an empty deck.");
		}
		
		System.out.println("\nAFTER LOOKNG AND REMOVING\n");
		System.out.println(toString());
		
		System.out.println("\nLOOK TOP, ADD, LOOK TOP\n");
		try
		{
			System.out.println(seeTopCard());
			addCard(_cards[51]);
			System.out.println(seeTopCard());
		}
		catch (DupeCardException e)
		{
			System.out.println("[ERROR] Tried to add a dupelicate card.");
		}
		catch (EmptyDeckException e)
		{
			System.out.println("[ERROR] Tried to access an empty deck.");
		}
		catch (FullDeckException e)
		{
			System.out.println("[ERROR] Tried to add a card to a full deck.");
		}

		System.out.println("\nLOOK TOP, ADD DUPE\n");
		try
		{
			System.out.println(seeTopCard());
			addCard(_cards[3]);
		}
		catch (DupeCardException e)
		{
			System.out.println("[ERROR] Tried to add a dupelicate card.");
		}
		catch (EmptyDeckException e)
		{
			System.out.println("[ERROR] Tried to access an empty deck.");
		}
		catch (FullDeckException e)
		{
			System.out.println("[ERROR] Tried to add a card to a full deck.");
		}
	}
	
	/** main program initiator. */
	public static void main(String[] args)
	{
		Deck thisDeck = new Deck();
		thisDeck.test();
	}
}
