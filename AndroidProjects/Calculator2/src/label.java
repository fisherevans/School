import javax.swing.*;

import java.awt.event.*;

public class label extends JFrame implements ActionListener
{
		// Declare GUI components
	private JLabel dummy;
	private JTextField current, previous;
	private JButton one, two, three, four, five, six, seven, eight, nine, zero, point, plus, minus, times, divide, enter, clear;
	
		// Adding Dec vs. Adding Floats
	private boolean pointB = false;
	
		// Create operation vars
	private String curNum = "0";
	private String curCalc = "new";
	private Object newCalc;
	private double result = 0;
	
	public label()
	{
			// Window
		setSize(272,292);
		setTitle("Fisher's Calculator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
			//Button Placements
		one = new JButton("1");
		one.setBounds(5,155,45,45);
		one.addActionListener(this);
		add(one);

		two = new JButton("2");
		two.setBounds(55,155,45,45);
		two.addActionListener(this);
		add(two);

		three = new JButton("3");
		three.setBounds(105,155,45,45);
		three.addActionListener(this);
		add(three);

		four = new JButton("4");
		four.setBounds(5,105,45,45);
		four.addActionListener(this);
		add(four);

		five = new JButton("5");
		five.setBounds(55,105,45,45);
		five.addActionListener(this);
		add(five);

		six = new JButton("6");
		six.setBounds(105,105,45,45);
		six.addActionListener(this);
		add(six);

		seven = new JButton("7");
		seven.setBounds(5,55,45,45);
		seven.addActionListener(this);
		add(seven);

		eight = new JButton("8");
		eight.setBounds(55,55,45,45);
		eight.addActionListener(this);
		add(eight);

		nine = new JButton("9");
		nine.setBounds(105,55,45,45);
		nine.addActionListener(this);
		add(nine);

		zero = new JButton("0");
		zero.setBounds(5,205,95,45);
		zero.addActionListener(this);
		add(zero);

		point = new JButton(".");
		point.setBounds(105,205,45,45);
		point.addActionListener(this);
		add(point);
		
		plus = new JButton("+");
		plus.setBounds(155,55,45,45);
		plus.addActionListener(this);
		add(plus);

		minus = new JButton("-");
		minus.setBounds(205,55,45,45);
		minus.addActionListener(this);
		add(minus);

		times = new JButton("*");
		times.setBounds(155,105,45,45);
		times.addActionListener(this);
		add(times);

		divide = new JButton("/");
		divide.setBounds(205,105,45,45);
		divide.addActionListener(this);
		add(divide);

		enter = new JButton("=");
		enter.setBounds(205,155,45,95);
		enter.addActionListener(this);
		add(enter);
		
		clear = new JButton("C");
		clear.setBounds(155,155,45,95);
		clear.addActionListener(this);
		add(clear);

		previous = new JTextField("");
		previous.setBounds(5,5,245,20);
		previous.setEditable(false);
		add(previous);

		current = new JTextField(curNum);
		current.setBounds(5,30,245,20);
		current.setEditable(false);
		add(current);
		
			// Weird bug needed to prop. align all previous components
		dummy = new JLabel("");
		dummy.setBounds(0,0,0,0);
		add(dummy);
	}
	
	public void actionPerformed(ActionEvent e)
	{
			//Edit cur number
		if(e.getSource() == one || e.getSource() == two || e.getSource() == three || e.getSource() == four || e.getSource() == five || e.getSource() == six || e.getSource() == seven || e.getSource() == eight || e.getSource() == nine || e.getSource() == zero || e.getSource() == point)
		{
			editCurNum(e);
			//Get rid of leading 0's
			while(curNum.substring(0,1).equals("0") && curNum.length() > 1)
			{
				curNum = curNum.substring(1);
			}
			current.setText(curNum);
		}
			// Operators
		else if (e.getSource() == plus || e.getSource() == minus || e.getSource() == times || e.getSource() == divide)
		{
			newCalc = e.getSource();
			
				// IF op'ing from last result
			if(curCalc.equals("enter"))
			{
				curCalc = "new";
				curNum = current.getText();
			}
				
				// If it's the first #, or second
			if(curCalc.equals("new"))
			{
				result = Double.parseDouble(curNum);
			}
			else if(curCalc.equals("plus"))
			{
				result = result + Double.parseDouble(curNum);
			}
			else if(curCalc.equals("minus"))
			{
				result = result - Double.parseDouble(curNum);
			}
			else if(curCalc.equals("times"))
			{
				result = result * Double.parseDouble(curNum);
			}
			else if(curCalc.equals("divide"))
			{
				result = result / Double.parseDouble(curNum);
			}
				
				// Setting the new Calc
			if(newCalc == plus)
			{
				previous.setText(Double.toString(result) + " + ");
				curCalc = "plus";
			}
			else if(newCalc == minus)
			{
				previous.setText(Double.toString(result) + " - ");
				curCalc = "minus";
			}
			else if(newCalc == times)
			{
				previous.setText(Double.toString(result) + " * ");
				curCalc = "times";
			}
			else if(newCalc == divide)
			{
				previous.setText(Double.toString(result) + " / ");
				curCalc = "divide";
			}
			
			curNum = "0";
			pointB = false;
			current.setText(Double.toString(result));
		}
		else if(e.getSource() == enter)
		{
				// Finalize operation
			if(curCalc.equals("new"))
			{
				result = Double.parseDouble(curNum);
				previous.setText("");
			}
			else if (curCalc.equals("plus"))
			{
				previous.setText(Double.toString(result) + " + " + Double.parseDouble(curNum));
				result = result + Double.parseDouble(curNum);
			}
			else if (curCalc.equals("minus"))
			{
				previous.setText(Double.toString(result) + " - " + Double.parseDouble(curNum));
				result = result - Double.parseDouble(curNum);
			}
			else if (curCalc.equals("times"))
			{
				previous.setText(Double.toString(result) + " * " + Double.parseDouble(curNum));
				result = result * Double.parseDouble(curNum);
			}
			else if (curCalc.equals("divide"))
			{
				previous.setText(Double.toString(result) + " / " + Double.parseDouble(curNum));
				result = result / Double.parseDouble(curNum);
			}
			
				// Reset
			curNum = "0";
			curCalc = "enter";
			newCalc = null;
			pointB = false;
			current.setText(Double.toString(result));
		}
		else if(e.getSource() == clear)
		{
				// Clear everything
			curNum = "0";
			result = 0;
			curCalc = "new";
			newCalc = null;
			pointB = false;
			current.setText("0");
			previous.setText("");
		}
		
	}
	
	public void editCurNum(ActionEvent e)
	{
			//Add the numbers
		if(e.getSource() == one)
		{
			curNum = curNum + "1";
		}
		else if(e.getSource() == two)
		{
			curNum = curNum + "2";
		}
		else if(e.getSource() == three)
		{
			curNum = curNum + "3";
		}
		else if(e.getSource() == four)
		{
			curNum = curNum + "4";
		}
		else if(e.getSource() == five)
		{
			curNum = curNum + "5";
		}
		else if(e.getSource() == six)
		{
			curNum = curNum + "6";
		}
		else if(e.getSource() == seven)
		{
			curNum = curNum + "7";
		}
		else if(e.getSource() == eight)
		{
			curNum = curNum + "8";
		}
		else if(e.getSource() == nine)
		{
			curNum = curNum + "9";
		}
		else if(e.getSource() == zero)
		{
			curNum = curNum + "0";
		}
		else if(e.getSource() == point)
		{
				// Left or right of point
			if(!pointB)
			{
				pointB = true;
				curNum = curNum + ".";
			}
		}
		
	}
}
