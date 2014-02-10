package com.fisherevans.vtc.oop.war;

public class WarGame
{
	private Deck _deckA, // All never null
				_deckB,
				_deckAD,
				_deckBD,
				_ante;
	private boolean _playing; // never null
	
	/** Constructs the war game objects and initializes the variables. */
	public WarGame()
	{
		_deckA = new Deck();
		_deckB = new Deck();
		_deckAD = new Deck();
		_deckBD = new Deck();
		
		_ante = new Deck();
		
		_deckA.init();
		
		for(int index = 0;index < 26;index++)
		{
			_deckB.addCardTop(_deckA.drawCardTop());
		}
		
		_playing = true;
	}
	
	/** Draws a card from a and b. If b or a takes all card in ante if their rank wins (ace high).
	 *  If tie, two cards are draawn frome ach deck and added to ante. Draw then calls itself repeats the process.
	 * @param indent used for log formatting (amount to indent messages by)
	 */
	public void turn(String indent)
	{
		Card a = draw("Deck A", _deckA, _deckAD);
		Card b = draw("Deck B", _deckB, _deckBD);
		int aR = a.getRank() == 1 ? 14 : a.getRank();
		int bR = b.getRank() == 1 ? 14 : b.getRank();
		
		log(indent + "A[" + a.toString() + "] vs. B[" + b.toString() + "]");

		_ante.addCardTop(a);
		_ante.addCardTop(b);

		if(aR > bR)
		{
			log(indent + "A Won this round and received: " + _ante.toString());
			_deckAD.addToTop(_ante);
		}
		else if(aR < bR)
		{
			log(indent + "B Won this round and received: " + _ante.toString());
			_deckBD.addToTop(_ante);
		}
		else
		{
			log(indent + "It's a tie!!! A and B Both play 2 cards face down.");
			_ante.addCardTop(drawA());
			_ante.addCardTop(drawA());
			_ante.addCardTop(drawB());
			_ante.addCardTop(drawB());
			turn(indent + "  ");
		}
	}
	
	/** Formats "draw" method for deck a
	 * @return Card dranw for deck a
	 */
	public Card drawA()
	{
		return draw("A", _deckA, _deckAD);
	}
	
	/** Formats "draw" method for deck B
	 * @return Card dranw for deck b
	 */
	public Card drawB()
	{
		return draw("B", _deckB, _deckBD);
	}
	
	/** Draws a card from a deck. If deck is empty, shuffles discard, puts cards from discard to deck. Empties discard. draws again. If still empty, player loses.
	 * @param name Name of player/deck (used for logging)
	 * @param deck Deck object
	 * @param discard Discard pile
	 * @return Card drawn
	 */
	public Card draw(String name, Deck deck, Deck discard)
	{
		if(deck.isEmpty())
		{
			if(discard.isEmpty())
			{
				log("\n\n" + name + " ran out of cards AND lost the game!");
				_playing = false;
				System.exit(1);
				return null;
			}
			else
			{
				discard.shuffle();
				deck.setDeckAs(discard);
				discard.empty();
				return draw(name, deck, discard);
			}
		}
		else
		{
			return deck.drawCardTop();
		}
	}
	
	/** Simple alias for System.out.println() */
	static void log(String msg)
	{
		System.out.println(msg);
	}
	
	/** Plays a game of war. Call after The objet is initialized. */
	public void play()
	{
		while(_playing)
		{
			_ante.empty();
			turn("");
		}
	}
	
	public static void main(String[] args)
	{
		WarGame war = new WarGame();
		war.play();
	}
}
