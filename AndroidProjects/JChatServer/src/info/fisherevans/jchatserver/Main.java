package info.fisherevans.jchatserver;
import java.io.BufferedReader;
import java.io.InputStreamReader;


public class Main
{
	public static void main(String args[])
	{
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		String title, disc, port, pass;
		try
		{
			System.out.print("Please enter a name for the server: ");
			title = input.readLine();
			System.out.print("Please enter a discription for the chat: ");
			disc = input.readLine();
			System.out.print("Please enter the port you wish to use: ");
			port = input.readLine();
			System.out.print("Please enter your desired admin password: ");
			pass = input.readLine();
			
			Server chatServer = new Server(title, disc, port, pass);
		}
		catch(Exception e)
		{
			System.out.println("There was a problem requesting input...");
		}
	}
}
