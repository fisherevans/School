package info.fisherevans.vtc.oop.deck;

import info.fisherevans.vtc.oop.deck.es.EmptyDeckException;

import java.util.ArrayList;

public class WarGame
{
	private Deck _deckA, _deckB;
	
	public WarGame()
	{
		_deckA = new Deck();
		_deckB = new Deck();
		
		_deckA.createNewDeck();
		_deckB.createNewDeck();
		
		try
		{
			_deckB.emptyDeck();
			_deckA.shuffleDeck();
			for(int index = 0;index < 26;index++)
			{
				_deckB.addCard(_deckA.drawTopCard());
			}
		}
		catch(Exception e)
		{
			print("There was an error creating the two decks.");
			e.printStackTrace();
			System.exit(1);
}
		
		print("##### Deck A: #####");
		print(_deckA.toString());
	
		print("##### Deck B: #####");
		print(_deckB.toString());
	}
	
	public void playTurn() throws EmptyDeckException
	{
		Card dual[] = { _deckA.seeTopCard(), _deckB.seeTopCard() };
		ArrayList<Card> ante = new ArrayList<Card>();
		ante.add(_deckA.drawTopCard());
		ante.add(_deckB.drawTopCard());
		
		
	}
	
	public static void main(String[] args)
	{
		WarGame game = new WarGame();
	}
	
	static void print(String msg)
	{
		System.out.println(msg);
	}
}
