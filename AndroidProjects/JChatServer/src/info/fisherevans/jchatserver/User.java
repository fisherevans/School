package info.fisherevans.jchatserver;

import java.net.Socket;

public class User
{
	public String name;
	public Socket socket;
	public boolean admin;
	private Server parent;
	
	public User(Socket newSocket, String newName, String pass, Server newParent)
	{
		parent = newParent;
		name = newName;
		socket = newSocket;
		admin = pass.equals(parent.pass) ? true : false;
	}
}
