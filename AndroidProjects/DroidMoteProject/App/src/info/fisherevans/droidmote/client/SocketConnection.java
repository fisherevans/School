package info.fisherevans.droidmote.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import android.util.Log;

public class SocketConnection
{
	private DroidMote parent;
	
	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	
	private String serverIp;
	private int serverPort;
	
	private Thread networkThread;
	private Connection networkRunnable;

	public SocketConnection(DroidMote parent)
	{
		this.parent = parent; // App object
		
		initiateThread();
	}
	
	public SocketConnection(DroidMote parent, String ip, int port)
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
		networkRunnable = new Connection(); // Private class that holds the network loop
		networkThread = new Thread(networkRunnable); // And the thread to hold that
	}
	
	public void startConnection()
	{
		networkThread.start(); // Public on button for the network
	}
	
	public void endConnection()
	{
		try
		{
			if(out != null) { out.println("q"); out.flush(); out.close(); } // If the objects aren't null, close any connection
			if(in != null) in.close();
			if(socket != null) socket.close();
			
			out = null; // Nullify them.
			in = null;
			socket = null;
		}
		catch(Exception e)
		{
			
		}
	}
	
	class Connection implements Runnable
	{
		public void run()
		{
			try
			{
				socket = new Socket(serverIp, serverPort); // Create the socket, and grab the input and output from it
				out = new PrintWriter(socket.getOutputStream(), false);
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

				parent.connected = true; // After the connections are made, set the state to connected
				
				int heartbeatCount = 50; // Used to track the number of loops made since last heartbeat was sent
				long lastHeartbeat = System.currentTimeMillis(); // Last time the server sent a heartbeat
				List<String> outputBuffer; // Local buffer for out going commands
				float[] mouseDelta = { 0, 0 }; // Local mouse delta value buffer;
				String input; // Used to buffer input
				while(parent.connected)
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
							parent.connected = false;
							parent.connectionTimedOut();
						}
					}

					outputBuffer = parent.outputBufferAdapter(2,""); // Create the local buffer from the global buffer
					if(outputBuffer != null)
					{
						for(int loop = 0;loop < outputBuffer.size();loop++) // Send each entry
							out.println(outputBuffer.get(loop));
						
						out.flush();
					}

					mouseDelta = parent.mouseDeltaAdapter(2, 0, 0);
					if(mouseDelta[0] != 0 || mouseDelta[1] != 0)
					{
						out.println("mm" + (int)(mouseDelta[0]) + ":" + (int)(mouseDelta[1]));
						out.flush();
					}

					if(in.ready())
					{
						while(in.ready() && (input = in.readLine()) != null && parent.connected) // Loop through input
						{
							if(input.startsWith("h"))
								lastHeartbeat = System.currentTimeMillis(); // If so, update last heartbeat time
							else if(input.startsWith("q"))
								parent.connected = false;
						}
					}
				}
			} catch(Exception e) { }
		}
	}
}
