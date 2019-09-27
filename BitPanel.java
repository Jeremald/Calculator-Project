/* This class creates the panel for the bit toggle keypad.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BitPanel extends JPanel implements ActionListener
{
	JButton[] buttons = new JButton[64];
	JLabel[] labels = new JLabel[16];
	
	public BitPanel()
	{
		setLayout(null);
		setPreferredSize(new Dimension(Calculator.getWidth(), Calculator.getHeight() - 285));
		setBackground(new Color(209, 209, 209));
		createButtons();
		addButtons();
		setVisible(true);
	}
	
	public void createButtons()
	{
		for(int index = 0; index < buttons.length; index++)
		{
			buttons[index] = new JButton("0");
			buttons[index].setMargin(new Insets(1, 1, 1, 1)); //smaller padding for buttons
			buttons[index].setBackground(new Color(209, 209, 209));
			buttons[index].addActionListener(this);
		}
		
		//create labels
		int x = 60;
		int y = 30;
		int width = 22;
		int height = 25;
		int labelNum = 60;
		
		for(int index = 0; index < labels.length; index++)
		{
			labels[index] = new JLabel("" + labelNum);
			
			if(labelNum < 10)
			{
				labels[index].setBounds(x + 5, y, width, height);
			}
			else
			{
				labels[index].setBounds(x, y, width, height);
			}
			
			labelNum -= 4; //only have label for every 4 positions
			
			if((index + 1) % 4 == 0) //if last in row
			{
				y += 65; //value needed to get spacing right
				x = 60; //smallest x value
			}
			
			else
			{
				x += 80; //value needed to get spacing right
			}
		}
		
		
	}
	
	public void addButtons()
	{
		int x = 5; //lowest value
		int y = 5; //lowest value
		int width = 18;
		int height = 25;
		
		for(int index = 0; index < 64; index++)
		{
			buttons[index].setBounds(x, y, width, height);
			add(buttons[index]);
			
			if((index + 1) % 16 == 0) //if at end of row
			{
				x = 5; //smallest x value
				y += 65; //value used to get spacing right
			}
			else if((index + 1) % 4 == 0) //every 4th button
			{
				x += 26; //value used to get spacing right
			}
			else
			{
				x += width;
			}
		}
		
		//add labels
		for(int index = 0; index < 16; index++)
		{
			add(labels[index]);
		}
	}
	
	public void actionPerformed(ActionEvent e) //check when buttons are clicked
	{
		for(int index = 0; index < buttons.length; index++)
		{
			if(e.getSource() == buttons[index])
			{
				//toggle buttons between 0 and 1 when clicked
				if(buttons[index].getText().equals("0"))
				{
					buttons[index].setText("1");
					buttons[index].setForeground(new Color(62, 125, 168));
				}
				else
				{
					buttons[index].setText("0");
					buttons[index].setForeground(Color.BLACK);
				}
				
				updateDisplay(); //display changes when buttons change
			}
		}
	}
	
	public void updateDisplay()
	{
		String temp = "";
		
		for(int index = 0; index < buttons.length; index++)
		{
			temp += buttons[index].getText(); //build binary string
		}

		//convert to correct base. If current base is binary, remove leading zeros
		if(Calculator.getMode() == 0)
		{
			long displayTemp = Long.parseLong(temp, 2);
			
			//adjust for length limit if needed
			if(Calculator.getLengthMode() == 1)
			{
				displayTemp = (int)displayTemp;
				temp = Integer.toBinaryString((int)displayTemp);
			}
			else if(Calculator.getLengthMode() == 2)
			{
				displayTemp = (short)displayTemp;
				temp = Integer.toBinaryString(0xFFFF & (short)displayTemp);
			}
			else if(Calculator.getLengthMode() == 3)
			{
				displayTemp = (byte)displayTemp;
				temp = Integer.toBinaryString(0xFF & (byte)displayTemp);
			}
			else
			{
				temp = Long.toBinaryString(displayTemp);
			}
			
		}
		else if(Calculator.getMode() == 1)
		{
			long displayTemp = Long.parseLong(temp, 2);
			
			//adjust for length limit if needed
			if(Calculator.getLengthMode() == 1)
			{
				displayTemp = (int)displayTemp;
				temp = Integer.toOctalString((int)displayTemp);
			}
			else if(Calculator.getLengthMode() == 2)
			{
				displayTemp = (short)displayTemp;
				temp = Integer.toOctalString(0xFFFF & (short)displayTemp);
			}
			else if(Calculator.getLengthMode() == 3)
			{
				displayTemp = (byte)displayTemp;
				temp = Integer.toOctalString(0xFF & (byte)displayTemp);
			}
			else
			{
				temp = Long.toOctalString(displayTemp);
			}
		}
		else if(Calculator.getMode() == 2)
		{
			long displayTemp = Long.parseLong(temp, 2);
			
			//adjust for length limit if needed
			if(Calculator.getLengthMode() == 1)
			{
				displayTemp = (int)displayTemp;
				temp = "" + displayTemp;
			}
			else if(Calculator.getLengthMode() == 2)
			{
				displayTemp = (short)displayTemp;
				temp = "" + displayTemp;
			}
			else if(Calculator.getLengthMode() == 3)
			{
				displayTemp = (byte)displayTemp;
				temp = "" + displayTemp;
			}
			else
			{
				temp = "" + displayTemp;
			}
		}
		else if(Calculator.getMode() == 3)
		{
			long displayTemp = Long.parseLong(temp, 2);
			
			//adjust for length limit if needed
			if(Calculator.getLengthMode() == 1)
			{
				displayTemp = (int)displayTemp;
				temp = Integer.toHexString((int)displayTemp);
			}
			else if(Calculator.getLengthMode() == 2)
			{
				displayTemp = (short)displayTemp;
				temp = Integer.toHexString(0xFFFF & (short)displayTemp);
			}
			else if(Calculator.getLengthMode() == 3)
			{
				displayTemp = (byte)displayTemp;
				temp = Integer.toHexString(0xFF & (byte)displayTemp);
			}
			else
			{
				temp = Long.toHexString(displayTemp);
			}
		}
		
		Calculator.setDisplay(temp);
	}
	
	public void updateButtons(String disp) //when display is changed, bit buttons change
	{
		int difference = buttons.length - disp.length(); //difference in length between two
		
		for(int index = 0; index < disp.length(); index++)
		{
			//change buttons to match display's binary string
			buttons[index + difference].setText("" + disp.charAt(index));
			
			//change color
			if(disp.charAt(index) == '1')
			{
				buttons[index + difference].setForeground(new Color(62, 125, 168));
			}
			else
			{
				buttons[index + difference].setForeground(Color.BLACK);
			}
		}
	}
	
	public void resetButtons() //when display is cleared, all buttons set to 0
	{
		for(int index = 0; index < buttons.length; index++)
		{
			buttons[index].setText("0");
			buttons[index].setForeground(Color.BLACK);
		}
	}
	
	public void updateButtons() //when length mode is changed, adjust buttons
	{
		if(Calculator.getLengthMode() == 0)
		{
			//enable all buttons
			for(int index = 0; index < 56; index++)
			{
				buttons[index].setEnabled(true);
			}
			Calculator.setDisplay(Calculator.getDisplay());
		}
		else if(Calculator.getLengthMode() == 1)
		{
			//disable first two rows
			for(int index = 0; index < 32; index++)
			{
				buttons[index].setText("0");
				buttons[index].setForeground(Color.BLACK);
				buttons[index].setEnabled(false);
			}
			
			if(Calculator.getDisplay().length() > 0)
			{
				updateDisplay();
			}
		}
		else if(Calculator.getLengthMode() == 2)
		{
			//disable third row
			for(int index = 32; index < 48; index++)
			{
				buttons[index].setText("0");
				buttons[index].setForeground(Color.BLACK);
				buttons[index].setEnabled(false);
			}
			
			if(Calculator.getDisplay().length() > 0)
			{
				updateDisplay();
			}
		}
		else if(Calculator.getLengthMode() == 3)
		{
			//disable first half of last row
			for(int index = 48; index < 56; index++)
			{
				buttons[index].setText("0");
				buttons[index].setForeground(Color.BLACK);
				buttons[index].setEnabled(false);
			}
			
			if(Calculator.getDisplay().length() > 0)
			{
				updateDisplay();
			}
		}
	}
	
	public void paintComponent(Graphics g) //paints background for panel (looks messy without it)
	{
		g.setColor(new Color(209, 209, 209));
		g.fillRect(0, 0, Calculator.getWidth(), 400);
	}
}
