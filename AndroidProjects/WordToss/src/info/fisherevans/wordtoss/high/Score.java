package info.fisherevans.wordtoss.high;

public class Score
{
	private int diff;
	private float time;
	private String word, wordS;
	
	public Score(String word, String wordS, float time, int diff)
	{
		this.word = word;
		this.wordS = wordS;
		this.diff = diff;
		this.time = time;
	}
	
	public String getWord()
	{
		return word;
	}
	
	public String getWordS()
	{
		return wordS;
	}
	
	public int getDiff()
	{
		return diff;
	}
	
	public float getTime()
	{
		return time;
	}
}
