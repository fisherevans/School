import java.util.ArrayList;


public class Dictionary
{
	ArrayList<Definition> _words;
	
	public Dictionary()
	{
		_words = new ArrayList<Definition>();
	}
	
	public void addDefinition(Definition newDef)
	{
		_words.add(newDef);
	}
}
