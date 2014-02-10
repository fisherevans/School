package info.fisherevans.wordtoss.words;

public class Word
{
	private String word;
	private float diff;
	
	public Word(String word, float diff)
	{
		this.word = word.toUpperCase();
		this.diff = diff;
	}
	
	public float getDiff()
	{
		return diff;
	}
	
	public String getWord()
	{
		return word;
	}
}
