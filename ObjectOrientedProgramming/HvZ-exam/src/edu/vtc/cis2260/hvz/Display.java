/**
 * Display.java
 * Copyright 2011, Craig A. Damon
 * all rights reserved
 */
package edu.vtc.cis2260.hvz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * Display - an actual visible display
 * @author Craig A. Damon
 *
 */
public class Display extends JFrame
{
	private static final long serialVersionUID = -746893135768898959L;


	/**
	 * @throws HeadlessException
	 */
	public Display(PlayingField field) throws HeadlessException
		{
			super("Humans vs Zombies");
			_field = new FieldDisplay(field);
			_scores = new ScorePanel(field.getGame());
			getContentPane().setLayout(new BorderLayout());
			getContentPane().add(_field,BorderLayout.CENTER);
			getContentPane().add(_scores,BorderLayout.SOUTH);
			pack();
 	    addWindowListener(new WindowAdapter(){
				@Override
				public void windowClosing(WindowEvent e)
				{
	        System.exit(0);				
				}
	      });
			setVisible(true);
		}


	/** description
	 * @param g the rendering environment
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param name
	 */
	public void displayStructure(Graphics g, int x, int y, int width, int height, String name,int doorSide)
	{
     _field.displayStructure(g,x,y,width,height, name,doorSide);		
	}


	/** description
	 * @param g the rendering environment
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param name
	 */
	public void displayPlayer(Graphics g, int x, int y, int dir, char type, int number)
	{
     _field.displayPlayer(g,x,y,dir,type,number);		
	}
	
	public void updateScores()
	{
		_scores.updateScores();
	}
	

	/** description
	 * @param ended
	 */
	public void gameOver(boolean ended)
	{
		_scores.gameEnded(ended);
	}


	private FieldDisplay _field;
	private ScorePanel _scores;

	
	/**
	 * FieldDisplay - a component that will show a playing field
	 * @author Craig A. Damon
	 *
	 */
	private final class FieldDisplay extends JComponent
	{
		private static final long serialVersionUID = 7075366977433663675L;

		/**
		 * 
		 */
		public FieldDisplay(PlayingField field)
			{
				_field = field;
				_baseFont = new Font("SansSerif",Font.BOLD,10);
				Dimension size = new Dimension(_field.getWidth(),_field.getHeight());
				setMinimumSize(size);
				setPreferredSize(size);
			}
		
		public void paintComponent(Graphics g)
		{
			g.setColor(Color.GREEN);
			g.fillRect(0, 0, getWidth(), getHeight());
			_field.display(g);
		}
		
		static final private int PLAYER_WIDTH=30;
		static final private int PLAYER_HEIGHT=15;
		
		/** description
		 * @param g
		 * @param x
		 * @param y
		 * @param dir
		 * @param type
		 * @param number
		 */
		public void displayPlayer(Graphics g, int x, int y, int dir, char type,int number)
		{
			if (type == 'H')
				g.setColor(Color.BLUE);
			else
				g.setColor(Color.RED);
			String name = String.valueOf(type)+String.valueOf(number);
			int pointSize = 10;
			Font font = _baseFont.deriveFont(Font.BOLD,pointSize);
			g.setFont(font);
			int width = g.getFontMetrics().stringWidth(name)+8;
			if (width < PLAYER_WIDTH)
				width = PLAYER_WIDTH;
			int left = convertX(x)-width/2;
			int top = convertY(y)-PLAYER_HEIGHT/2;
			g.drawRect(left,top,width,PLAYER_HEIGHT);
			g.drawString(name,left+2,top+pointSize);
			int dirX1 = left+width-2;
			int dirY1 = top+PLAYER_HEIGHT/2;
			int dirX2 = dirX1+(int)(Math.cos(dir)*2+0.5);
			int dirY2 = dirY1+(int)(Math.sin(dir)*2+0.5);
			g.drawLine(dirX1,dirY1,dirX2,dirY2);
		}

		/** description
		 * @param g
		 * @param x
		 * @param y
		 * @param width
		 * @param height
		 * @param name
		 */
		public void displayStructure(Graphics g, int x, int y, int width,int height, String name,int doorSide)
		{
			g.setColor(Color.DARK_GRAY);
			int left = convertX(x);
			int top = convertY(y);
			int w = convertX(width);
			int h = convertY(height);
			g.fillRect(left,top, w, h);
			//System.out.println("Drawing "+name+" at "+left+"("+x+"),"+top+"("+y+") for "+w+"("+width+")X"+h+"("+height+"), window is "+getWidth()+"X"+getHeight());
			g.setColor(Color.WHITE);
			int doorLength = 10;
			int doorThick = 4;
			switch (doorSide)
			{
			case 1:
				g.fillRect(left+w-doorThick/2, top+(h-doorLength)/2, doorThick, doorLength);
				break;
			case 2:
				g.fillRect(left+(w-doorLength)/2, top-doorThick/2, doorLength, doorThick);
				break;
			case 3:
				g.fillRect(left-doorThick/2, top+(h-doorLength)/2, doorThick, doorLength);
				break;
			case 4:
				g.fillRect(left+(w-doorLength)/2, top+h-doorThick/2, doorLength, doorThick);
				break;
			}
			int pointSize = 18;
			Font font = _baseFont.deriveFont(Font.BOLD,pointSize);
			g.setFont(font);
			int textWidth = g.getFontMetrics().stringWidth(name);
			g.drawString(name, left+(w-textWidth)/2, top+pointSize);
		}
		
		private int convertX(int x)
		{
			return (int)(x*(float)getWidth()/_field.getWidth()+0.5f);
		}
		
		private int convertY(int y)
		{
			return (int)(y*(float)getHeight()/_field.getHeight()+0.5f);
		}

		private PlayingField _field;
		private Font _baseFont;
	}

  private static class ScorePanel extends Box
  {
  	  private static final long serialVersionUID = 8073145651293753256L;
			
  	  public ScorePanel(HvZGame game)
  	   {
  	  	   super(BoxLayout.X_AXIS);
  	  	   _game = game;
  	  	   add(Box.createHorizontalStrut(20)); // set a minimum pad on the left
       add(Box.createHorizontalGlue());  // then absorb extra space
       add(new JLabel("Humans:"));
       add(Box.createHorizontalStrut(5));
  	  	   _humans = new JTextField(4);
  	  	   add(_humans);
  	  	   add(Box.createHorizontalGlue());
  	  	   add(Box.createHorizontalStrut(20));
  	  	   add(Box.createHorizontalGlue());
  	       add(new JLabel("Zombies:"));
  	       add(Box.createHorizontalStrut(5));
  	  	  	   _zombies = new JTextField(4);
  	  	  	   add(_zombies);
  	  	  	   add(Box.createHorizontalGlue());
  	  	  	   add(Box.createHorizontalStrut(20));
  	  	  	   _replay = new JButton("Replay");
  	  	  	   _replay.setEnabled(false);
  	  	  	   _replay.addActionListener(new ActionListener(){
  	  	  	  	     public void actionPerformed(ActionEvent event)
  	  	  	  	     {
  	  	  	  	    	    _game.resetGame();
  	  	  	  	    	    _game.run();
  	  	  	  	     }
  	  	  	   });
  	  	  	   add(_replay);
  	  	  	   add(Box.createHorizontalStrut(20));
  	   }
  	   
  	   /** description
			 * @param ended
			 */
			public void gameEnded(boolean ended)
			{
				_replay.setEnabled(ended);
			}

			/**
  	    * update the scores on this panel
  	    *
  	    */
  	   public void updateScores()
  	   {
	  	   _humans.setText(String.valueOf(_game.numHumans()));
	  	   _zombies.setText(String.valueOf(_game.numZombies()));
  	   }
  	   
  	   
  	   private JTextField _humans;
  	   private JTextField _zombies;
  	   private JButton _replay;
  	   private HvZGame _game;
  }
}
