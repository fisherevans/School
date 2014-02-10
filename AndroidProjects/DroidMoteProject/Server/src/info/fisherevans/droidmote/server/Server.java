package info.fisherevans.droidmote.server;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server
{
	private Main parent;
	
	private ServerSocket socketServer;
	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	
	private String serverIp;
	private int serverPort;
	
	private Thread networkThread;
	private Connection networkRunnable;
	
	public boolean run = false;
	
	public String meanOfFailure = "No error.";

	public Server(Main parent)
	{
		this.parent = parent; // App object
		
		initiateThread();
	}
	
	public Server(Main parent, String ip, int port)
	{
		this.parent = parent; // App object
		serverIp = ip; // Server's ip and port
		serverPort = port;
		
		initiateThread();
	}
	
	public void setServerInfo(String ip, int port)
	{
		serverIp = ip; // Server's ip and port
		serverPort = port;
	}
	
	public void initiateThread()
	{
		try { socketServer = new ServerSocket(parent.config.port); }
		catch (IOException e) { }
		
		parent.log("The network thread is ready");
		
		networkRunnable = new Connection(); // Private class that holds the network loop
		networkThread = new Thread(networkRunnable); // And the thread to hold that
	}
	
	public void startConnection()
	{
		networkThread.start(); // Public on button for the network
		parent.log("The network thread is running on port " + parent.config.port);
	}
	
	public void endConnection()
	{
		try
		{
			if(out != null) { out.println("q"); out.flush(); out.close(); } // If the objects aren't null, close any connection
			if(in != null) in.close();
			if(socket != null) socket.close();
			if(socketServer != null) socketServer.close();
			
			out = null; // Nullify them.
			in = null;
			socket = null;
			socketServer = null;
		} catch(Exception e) { }
	}
	
	class Connection implements Runnable
	{
		public void run()
		{
			try
			{
				parent.log("Waiting for a client to connect...");
				socket = socketServer.accept(); // Create the socket, and grab the input and output from it
				out = new PrintWriter(socket.getOutputStream(), false);
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				
				parent.log("A client has connected from: " + socket.getRemoteSocketAddress().toString().replace("/", ""));
				
				int heartbeatCount = 0; // Used to track the number of loops made since last heartbeat was sent
				long lastHeartbeat = System.currentTimeMillis(); // Last time the server sent a heartbeat
				List<String> outputBuffer; // Local buffer for out going commands
				float[] mouseDelta = { 0, 0 }; // Local mouse delta value buffer;
				String input; // Used to buffer input
				run = true;
				while(run)
				{
					Thread.sleep(50);
					heartbeatCount++;
					if(heartbeatCount >= 70) // Every 3500 ms
					{
						heartbeatCount = 0;
						out.println("h");
						out.flush();
						
						if(System.currentTimeMillis() - lastHeartbeat > 8000)
						{
							parent.log("The connection was lost bewteen the server and client.");
							parent.restart();
						}
					}
					
					if(in.ready()) // Loop through input
					{
						
						while(in.ready() && (input = in.readLine()) != null)
						{
							if(parent.debug) parent.log("[CLIENT COMMAND] " + input);
							
							switch (input.charAt(0))
							{
								case 'm': // If about Mouse
								{
									switch(input.charAt(1))
									{
										case 'm': // Move mouse
											String dXY[] = input.substring(2).split(":");
											parent.mouseMove(Integer.parseInt(dXY[0]), Integer.parseInt(dXY[1])); // Get the passed ints,a nd apply the delta
											break;
										case '1': // Mouse 1
											switch (input.charAt(2))
											{
												case 'p':
													parent.mousePress(InputEvent.BUTTON1_MASK); break; // Press or Release the button
												case 'r':
													parent.mouseRelease(InputEvent.BUTTON1_MASK); break;
											}
											break;
										case '2': // Mouse 2
											switch (input.charAt(2))
											{
												case 'p':
													parent.mousePress(InputEvent.BUTTON2_MASK); break; // Press or Release the button
												case 'r':
													parent.mouseRelease(InputEvent.BUTTON2_MASK); break;
											}
											break;
										case '3': // Mouse 3
											switch (input.charAt(2))
											{
												case 'p':
													parent.mousePress(InputEvent.BUTTON3_MASK); break; // Press or Release the button
												case 'r':
													parent.mouseRelease(InputEvent.BUTTON3_MASK); break;
											}
											break;
										case 's': // Mouse Scroll
											parent.mouseScroll(Integer.parseInt(input.substring(2))); // Scroll the mouse
											break;
									}
									break;
								}
								case 'k': // If it's about the keyboard
								{
									if(input.equals("k<<")) // If it's a backspace
									{
										parent.keyPress(KeyEvent.VK_BACK_SPACE);
										parent.keyRelease(KeyEvent.VK_BACK_SPACE);
									}
									else if(input.equals("kvv")) // enter key
									{
										parent.keyPress(KeyEvent.VK_ENTER);
										parent.keyRelease(KeyEvent.VK_ENTER);
									}
									else // Else type the given character
										parent.charInterp.typeKey(input.charAt(1));
									break;
								}
								case 'h': // If it's a heart beat
								{
									lastHeartbeat = System.currentTimeMillis(); // If so, update last heartbeat time
									break;
								}
								case 'q': // If it's a kill signal
								{
									parent.log("The client has closed the connection.");
									parent.restart();
									break;
								}
							}
						}
					}
				}
			} catch(Exception e) { }
		}
	}
}