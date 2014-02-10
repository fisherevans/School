package info.fisherevans.jchat;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;


public class Chat
{
	public GUI mainFrame;
	public Thread serverConnection;
	public boolean online = false,
				   chatting = false;
	public String defaultIP = "localhost",
				  defaultPort = "63930",
				  defaultNick = "user",
				  defaultPass = "";
	public String newMessage = null;
	
	public PrintWriter out = null;
	
	public Thread chatListener;
	
	public Chat()
	{
		mainFrame = new GUI(this);
		mainFrame.setTitle("JChat - Private Instant Messaging");
		mainFrame.pack();
		mainFrame.setVisible(true);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		mainFrame.setLocation((dim.width-mainFrame.getSize().width)/2, (dim.height-mainFrame.getSize().height)/2);
		
		updateLayout();
	}
	
	public void updateLayout()
	{
		if(online)
		{
			mainFrame.connectionButton.setText("Disconnect");
			mainFrame.serverField.setEditable(false);
			mainFrame.portField.setEditable(false);
			mainFrame.nickField.setEditable(false);
			mainFrame.passwordField.setEditable(false);
		}
		else
		{
			mainFrame.connectionButton.setText("Connect");
			mainFrame.serverField.setEditable(true);
			mainFrame.portField.setEditable(true);
			mainFrame.nickField.setEditable(true);
			mainFrame.passwordField.setEditable(true);
		}
	}
	
	public void appendChat(String message)
	{
		mainFrame.chatArea.setText(mainFrame.chatArea.getText() + message);
		int x = mainFrame.chatArea.getSelectionEnd();
		mainFrame.chatArea.select(x,x);
	}
	
	public void commandConnect()
	{
		online = !online;

		defaultNick = mainFrame.nickField.getText();
		defaultPort = mainFrame.portField.getText();
		defaultIP = mainFrame.serverField.getText();
		defaultPass = mainFrame.passwordField.getText();
		  
	    if(online)
	    {
	    	appendChat("Attempting to connect to the server...");
	    	chatListener = new Thread(new ChatListener());
	    	chatListener.start();
	    	System.out.println("Attempting to connect to " + defaultIP + ":" + defaultPort);
	    }
	    else
	    {
	    	chatListener = null;
	    }
		updateLayout();
	}
	
	public void commandClient()
	{

		updateLayout();
	}
	
	public void commandServer()
	{
		
		updateLayout();
	}
	
	public void commandSend()
	{
		newMessage = mainFrame.chatField.getText();
		mainFrame.chatField.selectAll();
		if(out != null)
		{
			out.println(newMessage);
			out.flush();
		}
	}
	
	public class ChatListener implements Runnable
	{
		public void run()
		{
			try
			{
				Socket socket = new Socket(defaultIP, Integer.parseInt(defaultPort));
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new PrintWriter(socket.getOutputStream(), true);
				out.println(defaultNick + ":" + defaultPass);
				out.flush();
				while(online)
				{
					Thread.sleep(25);
					if(in.ready())
					{
						appendChat(in.readLine());
					}
				}
				in.close();
				socket.close();
			}
			catch(Exception e)
			{
				appendChat("There was a fatal error while trying to connect to the server.");
			}
		}
	}
}
