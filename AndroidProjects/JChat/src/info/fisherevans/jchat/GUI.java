package info.fisherevans.jchat;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUI extends JFrame implements ActionListener
{
    public JLabel serverLabel;
    public JLabel nickLabel;
    public JLabel portLabel;
    public JTextField serverField;
    public JTextField portField;
    public JTextField nickField;
    public JButton connectionButton;
    public JTextField chatField;
    public JEditorPane chatArea;
    public JButton sendButton;
    public JScrollPane chatScroll;
    public JLabel passwordLabel;
    public JTextField passwordField;
    
    Chat mainChat;
    
    public GUI(Chat parent)
    {
    	mainChat = parent;
		
        mainLayout customLayout = new mainLayout();

        getContentPane().setFont(new Font("Helvetica", Font.PLAIN, 12));
        getContentPane().setLayout(customLayout);

        serverLabel = new JLabel("Server IP:");//0
        getContentPane().add(serverLabel);

        nickLabel = new JLabel("Nickname:");
        getContentPane().add(nickLabel);

        portLabel = new JLabel("Port:");
        getContentPane().add(portLabel);

        serverField = new JTextField(mainChat.defaultIP);
        getContentPane().add(serverField);

        portField = new JTextField(mainChat.defaultPort);
        getContentPane().add(portField);

        nickField = new JTextField(mainChat.defaultNick);
        getContentPane().add(nickField);


        passwordField = new JTextField("");//11
        getContentPane().add(passwordField);

        chatField = new JTextField("");
        getContentPane().add(chatField);

        sendButton = new JButton("Send");
        sendButton.setActionCommand("sendButton");
        sendButton.addActionListener(this);
        getContentPane().add(sendButton);

        chatArea = new JEditorPane();
        chatArea.setMargin(new Insets(6,6,6,6));
        chatArea.setEditable(false);
        chatScroll = new JScrollPane(chatArea,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        	);
        getContentPane().add(chatScroll);

        passwordLabel = new JLabel("Password:");
        getContentPane().add(passwordLabel);
        
        connectionButton = new JButton("Connect");
        connectionButton.setActionCommand("connectionButton");
        connectionButton.addActionListener(this);
        getContentPane().add(connectionButton);

        setSize(getPreferredSize());
    }

	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand().equals("connectionButton"))
		{ mainChat.commandConnect(); }
		else if(e.getActionCommand().equals("sendButton"))
		{ mainChat.commandSend(); }
	}
}

class mainLayout implements LayoutManager
{
    public mainLayout() { }
    public void addLayoutComponent(String name, Component comp) { }
    public void removeLayoutComponent(Component comp) {  }

    public Dimension preferredLayoutSize(Container parent)
    {
        Dimension dim = new Dimension(0, 0);

        Insets insets = parent.getInsets();
        dim.width = 469 + insets.left + insets.right;
        dim.height = 173 + insets.top + insets.bottom;

        return dim;
    }

    public Dimension minimumLayoutSize(Container parent)
    {
        Dimension dim = new Dimension(0, 0);
        return dim;
    }

    public void layoutContainer(Container parent)
    {
        Insets insets = parent.getInsets();

        Component c;
        c = parent.getComponent(0);
        if (c.isVisible()) {c.setBounds(insets.left+8,insets.top+8,72,24);}
        c = parent.getComponent(1);
        if (c.isVisible()) {c.setBounds(insets.left+8,insets.top+72,72,24);}
        c = parent.getComponent(2);
        if (c.isVisible()) {c.setBounds(insets.left+8,insets.top+40,72,24);}
        c = parent.getComponent(3);
        if (c.isVisible()) {c.setBounds(insets.left+88,insets.top+8,96,24);}
        c = parent.getComponent(4);
        if (c.isVisible()) {c.setBounds(insets.left+88,insets.top+40,96,24);}
        c = parent.getComponent(5);
        if (c.isVisible()) {c.setBounds(insets.left+88,insets.top+72,96,24);}
        c = parent.getComponent(11);
        if (c.isVisible()) {c.setBounds(insets.left+8,insets.top+136,176,24);}
        c = parent.getComponent(7);
        if (c.isVisible()) {c.setBounds(insets.left+192,insets.top+136,192,24);}
        c = parent.getComponent(8);
        if (c.isVisible()) {c.setBounds(insets.left+392,insets.top+136,64,24);}
        c = parent.getComponent(9);
        if (c.isVisible()) {c.setBounds(insets.left+192,insets.top+8,264,120);}
        c = parent.getComponent(10);
        if (c.isVisible()) {c.setBounds(insets.left+8,insets.top+104,72,24);}
        c = parent.getComponent(6);
        if (c.isVisible()) {c.setBounds(insets.left+88,insets.top+104,96,24);}
    }
}
