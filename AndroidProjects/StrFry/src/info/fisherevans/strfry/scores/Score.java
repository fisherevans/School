package info.fisherevans.strfry.scores;

public class Score
{
	private int diff;
	private long time;
	private String word, wordS;
	
	public Score(String word, String wordS, long time, int diff)
	{
		setDiff(diff);
		setTime(time);
		setWord(word);
		setWordS(wordS);
	}

	public int getDiff()
	{
		return diff;
	}

	public void setDiff(int diff)
	{
		this.diff = diff;
	}

	public long getTime()
	{
		return time;
	}

	public void setTime(long time)
	{
		this.time = time;
	}

	public String getWord()
	{
		return word;
	}

	public void setWord(String word)
	{
		this.word = word;
	}

	public String getWordS()
	{
		return wordS;
	}

	public void setWordS(String wordS)
	{
		this.wordS = wordS;
	}
}
