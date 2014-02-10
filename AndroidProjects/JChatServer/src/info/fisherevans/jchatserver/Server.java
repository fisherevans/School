package info.fisherevans.jchatserver;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;


public class Server
{
	public String title, disc, port, pass;
	public boolean running = true;
	
	public ArrayList<Socket> socketList;
	public ArrayList<User> users;
	
	public ServerSocket serverSocket;
	
	Calendar calendar;
	
	Thread connectionHandler, chatListener;
	
	public Server(String newTitle, String newDisc, String newPort, String newPass)
	{
		title = newTitle;
		disc = newDisc;
		port = newPort;
		pass = newPass;
		
		socketList = new ArrayList<Socket>();
		users = new ArrayList<User>();
		
		log("Starting the server...");
		try
		{
			serverSocket = new ServerSocket(Integer.parseInt(port));
		}
		catch(Exception e)
		{
			log("There was an error creating the server.");
			log(e.getMessage());
		}

		connectionHandler = new Thread(new ConnectionHandler());
		chatListener = new Thread(new ChatListener());
		
		connectionHandler.start();
		chatListener.start();
	}
	
	public void log(String message)
	{
		calendar = new GregorianCalendar();
		String time = String.format("%02d", calendar.get(Calendar.HOUR)) + ":" + String.format("%02d", calendar.get(Calendar.MINUTE)) + ":" + String.format("%02d", calendar.get(Calendar.SECOND));
		System.out.println("[" + time + "] " + message);
	}
	
	public class ChatListener implements Runnable
	{
		public void run()
		{
			while(running)
			{
				try
				{
					Thread.sleep(20);
					
					List<String> messages = new ArrayList<String>();
			        PrintWriter out;
			        BufferedReader in;
			        
					for(User user : users)
					{
				        in = new BufferedReader(new InputStreamReader(user.socket.getInputStream()));
				        if(in.ready())
				        {
				        	messages.add(formatMessage(user, in.readLine()));
				        }
					}
					
					for(String msg : messages)
					{
						broadcast(msg);
					}
			        
				}
				catch(Exception e)
				{
					log("There was a problem handling chat");
					log(e.getMessage());
				}
			}
		}
		
	}
	
	public String formatMessage(User user, String message)
	{
		if(user.admin)
			return user.name + ": <i>" + message + "</i>";
		else
			return "<b>" + user.name + "<b>: <i>" + message + "</i>";
	}
	
	public void sendTo(Socket sock, String[] toSend)
	{
		try
		{
			PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
			for(String msg : toSend)
			{
				out.println(msg);
				out.flush();
			}
		}
		catch(Exception e)
		{
			log("Failed to send message to user.");
		}
	}
	
	public void sendTo(Socket sock, String toSend)
	{
		try
		{
			PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
			out.println(toSend);
			out.flush();
		}
		catch(Exception e)
		{
			log("Failed to send message to user.");
		}
	}
	
	public void broadcast(String msg) throws IOException
	{
		PrintWriter out;
		for(Socket sock : socketList)
		{
	        out = new PrintWriter(sock.getOutputStream(), true);
	        out.println(msg);
	        out.flush();
		}
	}
	
	public boolean isUserName(String name)
	{
		for(User user : users)
		{
			if(user.name.equals(name))
			{
				return true;
			}
		}
		return false;
	}
	
	public class ConnectionHandler implements Runnable
	{
		public void run()
		{
			log("Starting connection handler");
			while(running)
			{
				try
				{
					Thread.sleep(25);
					Socket newConnection = serverSocket.accept();
					if(newConnection != null)
					{
						BufferedReader in = new BufferedReader(new InputStreamReader(newConnection.getInputStream()));
						while(!in.ready()) { Thread.sleep(10); }
						String info = in.readLine();
						log("New user info - " + info);
				        String newInfo[] = info.split(":");
				        if(isUserName(newInfo[0]))
				        {
				        	sendTo(newConnection, "The username: " + newInfo[0] + " is already in use. Please try a different one.");
				        	newConnection.close();
							log("New Connection from: " + newConnection.getRemoteSocketAddress().toString() + " (" + newInfo[0] + ", " + newInfo[1] + ") - BAD USERNAME, IN USE!");
				        }
				        else
				        {
				        	users.add(new User(newConnection, newInfo[0], newInfo[1], Server.this));
							String tempMsgs[] = { "Welcome to: " + title, "*" + disc + "*", " ", " " };
							sendTo(newConnection, tempMsgs);
							log("New Connection from: " + newConnection.getRemoteSocketAddress().toString() + " (" + newInfo[0] + ", " + newInfo[1] + ")");
				        }
					}
				}
				catch(Exception e)
				{
					log("There was a problem excepting an incoming connection.");
					e.printStackTrace();
					System.exit(1);
				}
			}
		}
		
	}
}
