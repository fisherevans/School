import java.util.ArrayList;


public class Definition
{
	private String _word,			// never null or emtpy
					_definition,	// never null or emtpy
					_speech;		// never null or emtpy
	
	public Definition(String word, String definition, String speech)
	{
		_word = word;
		_definition = definition;
		_speech = speech;
	}
	
	public String getWord()
	{
		return _word;
	}
	
	public String getDefinition()
	{
		return _definition;
	}
	
	public String getSpeech()
	{
		return _speech;
	}
}
