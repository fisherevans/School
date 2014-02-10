/**
 * Card.java
 * Copyright 2011, Craig A. Damon
 * all rights reserved
 */
package info.fisherevans.vtc.oop.deck;

import java.util.Comparator;

/**
 * Card - A simple playing card
 * @author Craig A. Damon
 *
 */
public class Card
{
	 /** the suits in standard playing cards */
   public enum Suit { Club, Diamond, Heart, Spade };
   
   /**
    * a comparison that will treat aces as high, and compare by suit for equal rank.
    *  (spades, hearts, diamonds, clubs from highest to lowest).
    */
   public static final Comparator<Card> aceHighCompare = new AceHighCompare();
   
   /**
    * a comparison based only on rank that will treat aces as high.
    *  Suits are completely ignored.
    */
   public static final Comparator<Card> aceHighRankCompare = new AceHighRankCompare();

   /**
    * create a card with the specified suit and rank
    * @param suit
    * @param rank
    */
   public Card(Suit suit,int rank)
   {
  	   _suit = suit;
  	   _rank = rank;
  	   repOK();
   }
   
   /**
    * get the human description of this card
    * @return a string like "5 of diamonds"
    * @see java.lang.Object#toString()
    */
   @Override
   public String toString()
   {
  	   return rankName()+" of "+_suit+"s";
   }
   
   /**
 	 * @return the suit
 	 */
 	public Suit getSuit()
 	{
 		return _suit;
 	}

 	/**
 	 * @return the rank
 	 */
 	public int getRank()
 	{
 		return _rank;
 	}
 	
 	/**
 	 * get a readable name for the rank
 	 * @return the name, such as "Ace" or "5"
 	 */
 	public String rankName()
 	{
 		switch (getRank())
 		{
 			case 1:
 				return "Ace";
 			case 11:
 				return "Jack";
 			case 12:
 				return "Queen";
 			case 13:
 				return "King";
 			default:
 				return String.valueOf(getRank());	
 		}
 	}
 	
 	/**
 	 * check whether two cards are the same
 	 * @param otherCard
 	 * @return true if they are the same value
 	 */
 	public boolean equals(Card otherCard)
 	{
 		return getRank() == otherCard.getRank() && getSuit() == otherCard.getSuit();
 	}
 	
 	/**
 	 * check whether this is the same as the other object
 	 * @param other
 	 * @return
 	 * @see java.lang.Object#equals(java.lang.Object)
 	 */
 	@Override
 	public boolean equals(Object other)
 	{
 		if (other instanceof Card)
 			return equals((Card)other);
 		return false;
 	}
 	
 	/**
 	 * compute the hash code. Required if you override equals
 	 * @return a unique integer for each card value
 	 * @see java.lang.Object#hashCode()
 	 */
 	@Override
 public int hashCode()
 {
	 return getSuit().ordinal()*13+getRank()-1;
 }

	
   /**
    * verify the representation invariants of this card
    *
    */
   private void repOK()
   {
  	   assert _suit != null;
  	   assert _rank >= 1;
  	   assert _rank <= 13;
   }
	
   private Suit _suit;  // never null
	 private int _rank;   // between 1 and 13
	 
	 private static final class AceHighCompare implements Comparator<Card>
	 {
		/**
		 * @param card1
		 * @param card2
		 * @return 1 if card 1 is greater, -1 if card 1 is lesser, 0 if they are the same card
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		@Override
		public int compare(Card card1, Card card2)
		{
			if (card1.getRank() == 1)
				{
					// first card is an ace
					if (card2.getRank() == 1)
						return card1.getSuit().compareTo(card2.getSuit());
				  return 1;  // ace wins
				}
			if (card2.getRank() == 1)
				{ 
					// second card is an ace, it is higher
					return -1;
				}
			if (card1.getRank() < card2.getRank())
				return -1;
			if (card1.getRank() > card2.getRank())
				return 1;
			return card1.getSuit().compareTo(card2.getSuit());
		}
		 
	 }
	 
	 private static final class AceHighRankCompare implements Comparator<Card>
	 {
		/**
		 * @param card1
		 * @param card2
		 * @return 1 if card 1 is greater, -1 if card 1 is lesser, 0 if they are the same rank
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		@Override
		public int compare(Card card1, Card card2)
		{
			if (card1.getRank() == 1)
				{
					// first card is an ace
					if (card2.getRank() == 1)
						return 0;
				  return 1;  // ace wins
				}
			if (card2.getRank() == 1)
				{ 
					// second card is an ace, it is higher
					return -1;
				}
			if (card1.getRank() < card2.getRank())
				return -1;
			if (card1.getRank() > card2.getRank())
				return 1;
			return 0;
		}
		 
	 }
}
