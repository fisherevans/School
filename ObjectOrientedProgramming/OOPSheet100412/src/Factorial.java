
public class Factorial
{
	private long _answer, _count;
	
	public Factorial()
	{
		_answer = 1;
		_count = 1;
	}
	
	public long next()
	{
		return _answer *= _count++;
	}
	
	public boolean hasNext()
	{
		return Long.MAX_VALUE /_count > _answer;
	}
	
	public void test()
	{
		for(int i = 1;hasNext();i++) { System.out.println(i + "! = " + next()); }
	}
	
	public static void main(String[] args)
	{
		new Factorial().test();
	}
}