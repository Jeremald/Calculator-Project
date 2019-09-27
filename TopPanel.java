/* This class creates the panel that will appear on the top part of the calculator. Has
 * buttons.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class TopPanel extends JPanel implements ActionListener
{
	private JButton[] buttons = new JButton[4];
	
	/* Array Key:
	 * 0 - Binary Button
	 * 1 - Octal Button
	 * 2 - Decimal Button
	 * 3 - Hexadecimal Button
	 */
	
	private Font title = new Font("Arial Black", Font.PLAIN, 16);
	private Font displayFont = new Font("Arial", Font.BOLD, 24);
	private Font equationFont = new Font("Arial", Font.PLAIN, 15);
	private String hexValue = "0";
	private String decValue = "0";
	private String octValue = "0";
	private String binValue = "0";
	private String displayString;
	
	public TopPanel()
	{
		setLayout(null);
		setPreferredSize(new Dimension(Calculator.getWidth(), 245 - 30)); //sets size of panel in frame
		createButtons();
		addButtons();
		setVisible(true);
	}
	
	//creates the buttons for this panel
	public void createButtons()
	{
		buttons[0] = new JButton("BIN");
		buttons[0].setBounds(0, 190, Calculator.getWidth(), 25);
		buttons[1] = new JButton("OCT");
		buttons[1].setBounds(0, 165, Calculator.getWidth(), 25);
		buttons[2] = new JButton("DEC");
		buttons[2].setBounds(0, 140, Calculator.getWidth(), 25);
		buttons[3] = new JButton("HEX");
		buttons[3].setBounds(0, 115, Calculator.getWidth(), 25);
		
		//set text alignment for buttons to left and set color of buttons
		for(int index = 0; index < buttons.length; index++)
		{
			buttons[index].setHorizontalAlignment(SwingConstants.LEFT);
			buttons[index].setBackground(new Color(239, 239, 239));
			buttons[index].addActionListener(this);
		}
		
		//start in decimal mode, so decimal button has blue text
		
		buttons[2].setForeground(new Color(62, 125, 168));
	}
	
	//adds buttons to the panel
	public void addButtons()
	{
		for(int index = 0; index < buttons.length; index++)
		{
			add(buttons[index]);
		}
	}
	
	//Adds in painted components
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g); //set up painting
		
		g.setFont(title);
		g.drawString("PROGRAMMER", 60, 30); //paint title
		//paint button next to title
		g.drawLine(10, 20, 30, 20);
		g.drawLine(10, 25, 30, 25);
		g.drawLine(10, 30, 30, 30);
		g.setFont(displayFont);
		
		//adjust display font if necessary
		if(Calculator.getDisplay().length() > 40)
		{
			g.setFont(new Font("Arial", Font.BOLD, 8));
		}
		else if(Calculator.getDisplay().length() > 28)
		{
			g.setFont(new Font("Arial", Font.BOLD, 12));
		}
		else if(Calculator.getDisplay().length() > 21)
		{
			g.setFont(new Font("Arial", Font.BOLD, 18));
		}
		
		
		//display String
		FontMetrics displayMet = g.getFontMetrics(); //used for positioning display string
		displayString = format(Calculator.getDisplay()); //get display formatted right
		g.drawString(displayString, Calculator.getWidth() - displayMet.stringWidth(displayString) - 20, 103); //paint display
		
		//equation String
		g.setFont(equationFont);
		displayMet = g.getFontMetrics(); //used for positioning
		String equationString = "";
		ArrayList<String> equation = Calculator.getEquation();
		
		for(int index = 0; index < equation.size(); index++)
		{
			equationString += equation.get(index) + " ";
		}
		
		g.drawString(equationString, Calculator.getWidth() - displayMet.stringWidth(equationString) - 20, 70);
		
		
		//set values for buttons
		if(Calculator.getDisplay().equals("Cannot divide by zero"))
		{
			binValue = octValue = decValue = hexValue = "";
		}
		
		else if(Calculator.getDisplay().length() > 0) //don't change if display is empty
		{
			//get value of current base
			int radix = 0;
			if(Calculator.getMode() == 0)
			{
				radix = 2;
			}
			else if(Calculator.getMode() == 1)
			{
				radix = 8;
			}
			else if(Calculator.getMode() == 2)
			{
				radix = 10;
			}
			else if (Calculator.getMode() == 3)
			{
				radix = 16;
			}
			
			//values depend on the length mode
			if(Calculator.getLengthMode() == 0)
			{
				long temp;
				
				//get correct display value based on the base
				if(Calculator.getMode() == 2)
				{
					temp = Long.parseLong(Calculator.getDisplay(), radix);
				}
				else
				{
					temp = Long.parseUnsignedLong(Calculator.getDisplay(), radix);
				}
			
				binValue = Long.toBinaryString(temp);
				octValue = Long.toOctalString(temp);
				decValue = "" + temp;
				hexValue = Long.toHexString(temp);
			}
			else if(Calculator.getLengthMode() == 1)
			{
				long temp;
				if(Calculator.getMode() == 2)
				{
					temp = (int)Long.parseLong(Calculator.getDisplay(), radix);
				}
				else
				{
					temp = (int)Long.parseUnsignedLong(Calculator.getDisplay(), radix);
				}
			
				binValue = Integer.toBinaryString((int)temp);
				octValue = Integer.toOctalString((int)temp);
				decValue = "" + temp;
				hexValue = Integer.toHexString((int)temp);
			}
			else if(Calculator.getLengthMode() == 2)
			{
				long temp;
				if(Calculator.getMode() == 2)
				{
					temp = (short)Long.parseLong(Calculator.getDisplay(), radix);
				}
				else
				{
					temp = (short)Long.parseUnsignedLong(Calculator.getDisplay(), radix);
				}
			
				binValue = Integer.toBinaryString(0xFFFF & (short)temp);
				octValue = Integer.toOctalString(0xFFFF & (short)temp);
				decValue = "" + temp;
				hexValue = Integer.toHexString(0xFFFF & (short)temp);
			}
			else if(Calculator.getLengthMode() == 3)
			{
				long temp;
				if(Calculator.getMode() == 2)
				{
					temp = (byte)Long.parseLong(Calculator.getDisplay(), radix);
				}
				else
				{
					temp = (byte)Long.parseUnsignedLong(Calculator.getDisplay(), radix);
				}
			
				binValue = Integer.toBinaryString(0xFF & (byte)temp);
				octValue = Integer.toOctalString(0xFF & (byte)temp);
				decValue = "" + temp;
				hexValue = Integer.toHexString(0xFF & (byte)temp);
			}
		}
		
		else //set them to 0 when calculator is cleared
		{
			binValue = octValue = decValue = hexValue = "0";
		}
		
		//extra formatting for binValue
		while(binValue.length() % 4 != 0 && binValue != "0")
		{
			binValue = 0 + binValue;
		}
		
		//update button text
		boolean negative = false;
		
		if(decValue.charAt(0) == '-') //don't count negative for commas or spaces
		{
			negative = true;
			decValue = decValue.substring(1);
		}
		
		decValue = decFormat(decValue);
		
		if(negative) //re add negative sign for negative values
		{
			decValue = "-" + decValue;
		}
		
		buttons[0].setText("BIN     " + binFormat(binValue));
		buttons[1].setText("OCT    " + octFormat(octValue));
		buttons[2].setText("DEC    " + decValue);
		buttons[3].setText("HEX    " + hexFormat(hexValue));
	}
	
	//formats display string correctly for the mode the calculator is in
	public String format(String s)
	{
		boolean negative = false;
		
		if(s.length() == 0)
		{
			s += "0";
		}
		
		if(s.charAt(0) == '-') //don't count negative for commas or spaces
		{
			negative = true;
			s = s.substring(1);
		}
		
		//format appropriately for mode calculator is in
		if(Calculator.getMode() == 0)
		{
			s = binFormat(s);
		}
		else if(Calculator.getMode() == 1)
		{
			s = octFormat(s);
		}
		else if(Calculator.getMode() == 2)
		{
			s = decFormat(s);
		}
		else if(Calculator.getMode() == 3)
		{
			s = hexFormat(s);
		}

		//add back negative sign for negative numbers
		if(negative)
		{
			s = "-" + s;
		}
		return s;
	}
	
	public String decFormat(String s) //insert commas from right to left every 3 numbers
	{
		if(s.length() >= 4)
		{
			for(int index = s.length() - 3; index > 0; index -= 3)
			{
				s = s.substring(0, index) + "," + s.substring(index);
			}
		}
		
		return s;
	}
	
	public String binFormat(String s) //insert spaces right to left every 4 numbers
	{
		if(s.length() >= 5)
		{
			for(int index = s.length() - 4; index > 0; index -= 4)
			{
				s = s.substring(0, index) + " " + s.substring(index);
			}
		}
		
		return s;
	}
	
	public String octFormat(String s) //insert spaces right to left every 3 numbers
	{
		if(s.length() >= 4)
		{
			for(int index = s.length() - 3; index > 0; index -= 3)
			{
				s = s.substring(0, index) + " " + s.substring(index);
			}
		}
		
		return s;
	}
	
	public String hexFormat(String s) //insert spaces right to left every 4 numbers
	{
		if(s.length() >= 5)
		{
			for(int index = s.length() - 4; index > 0; index -= 4)
			{
				s = s.substring(0, index) + " " + s.substring(index);
			}
		}
		
		return s;
	}
	
	public void actionPerformed(ActionEvent e) //check for when buttons are clicked
	{
		if(e.getSource() == buttons[0]) //Bin button clicked
		{
			//swap button colors
			buttons[Calculator.getMode()].setForeground(Color.BLACK);
			buttons[0].setForeground(new Color(62, 125, 168));
			
			int radix = 2; //get base of current mode
			int newRadix = 2;
			
			if(Calculator.getMode() == 1)
			{
				radix = 8;
			}
			else if(Calculator.getMode() == 2)
			{
				radix = 10;
			}
			else if (Calculator.getMode() == 3)
			{
				radix = 16;
			}
			
			//convert results and display
			Calculator.convertResults(radix, newRadix);
			Calculator.convertDisplay(radix, newRadix);
			Calculator.convertEquation(radix, newRadix);
			
			//change buttons
			Calculator.binButtons();
			
			//change mode
			Calculator.setMode(0);
		}
		
		else if(e.getSource() == buttons[1]) //Oct button clicked
		{
			//swap button colors
			buttons[Calculator.getMode()].setForeground(Color.BLACK);
			buttons[1].setForeground(new Color(62, 125, 168));
			
			int radix = 8; //get base of current mode
			int newRadix = 8;
			
			if(Calculator.getMode() == 0)
			{
				radix = 2;
			}
			else if(Calculator.getMode() == 2)
			{
				radix = 10;
			}
			else if (Calculator.getMode() == 3)
			{
				radix = 16;
			}
			
			//convert results and display
			Calculator.convertResults(radix, newRadix);
			Calculator.convertDisplay(radix, newRadix);
			Calculator.convertEquation(radix, newRadix);
			
			//change buttons
			Calculator.octButtons();
			
			//change mode
			Calculator.setMode(1);
		}
		
		else if(e.getSource() == buttons[2]) //Dec button clicked
		{
			//swap button colors
			buttons[Calculator.getMode()].setForeground(Color.BLACK);
			buttons[2].setForeground(new Color(62, 125, 168));
			
			int radix = 10; //get base of current mode
			int newRadix = 10;
			
			if(Calculator.getMode() == 0)
			{
				radix = 2;
			}
			else if(Calculator.getMode() == 1)
			{
				radix = 8;
			}
			else if (Calculator.getMode() == 3)
			{
				radix = 16;
			}
			
			//convert results and display
			Calculator.convertResults(radix, newRadix);
			Calculator.convertDisplay(radix, newRadix);
			Calculator.convertEquation(radix, newRadix);
			
			//change buttons
			Calculator.decButtons();
			
			//change mode
			Calculator.setMode(2);
		}
		
		else if(e.getSource() == buttons[3]) //Hex button clicked
		{
			//swap button colors
			buttons[Calculator.getMode()].setForeground(Color.BLACK);
			buttons[3].setForeground(new Color(62, 125, 168));
			
			int radix = 16; //get base of current mode
			int newRadix = 16;
			
			if(Calculator.getMode() == 0)
			{
				radix = 2;
			}
			else if(Calculator.getMode() == 1)
			{
				radix = 8;
			}
			else if (Calculator.getMode() == 2)
			{
				radix = 10;
			}
			
			//convert results and display
			Calculator.convertResults(radix, newRadix);
			Calculator.convertDisplay(radix, newRadix);
			Calculator.convertEquation(radix, newRadix);
			
			//change buttons
			Calculator.hexButtons();
			
			//change mode
			Calculator.setMode(3);
		}
	}
}
