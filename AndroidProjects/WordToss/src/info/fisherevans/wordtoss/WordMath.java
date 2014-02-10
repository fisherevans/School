package info.fisherevans.wordtoss;

import info.fisherevans.wordtoss.views.LetterTile;

public class WordMath
{
	private WordTossActivity parent;
	
	public WordMath(WordTossActivity parent)
	{
		this.parent = parent;
	}
	
	public String scrambleWord(String word)
	{
		StringBuilder tempWord = new StringBuilder(word);
        String wordScramble = "";
        int rand;
        
        while(tempWord.length() > 0)
        {
        	rand = (int)(Math.random()*tempWord.length());
            wordScramble += tempWord.charAt(rand);
            tempWord.deleteCharAt(rand);
        }
        
        if(wordScramble.equals(word))
        {
        	return scrambleWord(word);
        }
        else
        {
            return wordScramble;
        }
	}
	
	public boolean isEqual()
	{
		String temp = "";
		for(LetterTile tile : parent.currentTiles)
		{
			temp += tile.getLetter();
		}
		
		return temp.equals(parent.currentWord);
	}
}
