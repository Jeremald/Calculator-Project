/* This class creates the panel that will hold most of the buttons on the calculator. This panel
 * will use the GridLayout Layout manager.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CalcPanel extends JPanel implements ActionListener
{
	private long max = Long.MAX_VALUE;
	private boolean changed = false; //used for buttons with changing labels
	private JButton[] buttons = new JButton[36];	
	private Operators[] ops = {new BinOperators(), new OctOperators(), new DecOperators(), new HexOperators()};
	private boolean operating = false; //used to handle input during operations
	
	/*  buttons array key:
	 *  0-15 : Buttons 1-9 and A-F
	 *  16-21 : Operators in order =, +, -, *, /, %.
	 *  22 : decimal point (always disabled in programmer calculator)
	 *  23 : + or - (Sign switcher)
	 *  24-25 : Parentheses
	 *  26-28 : CE, C, backspace
	 *  29 : Up arrow 
	 *  30-35 : Top row
	 */
	private long[] maxLength = {Long.MAX_VALUE, Integer.MAX_VALUE, Short.MAX_VALUE, Byte.MAX_VALUE};
	private long[] minLength = {Long.MIN_VALUE, Integer.MIN_VALUE, Short.MIN_VALUE, Byte.MIN_VALUE};
	
	public CalcPanel()
	{
		setLayout(new GridLayout(6, 6)); //Create 6x6 Grid Layout
		setPreferredSize(new Dimension(Calculator.getWidth(), Calculator.getHeight() - 285));
		createButtons();
		addButtons();
		setVisible(true);
	}
	
	//creates all the buttons needed for the panel.
	public void createButtons()
	{
		buttons[0] = new JButton("0");
		buttons[1] = new JButton("1");
		buttons[2] = new JButton("2");
		buttons[3] = new JButton("3");
		buttons[4] = new JButton("4");
		buttons[5] = new JButton("5");
		buttons[6] = new JButton("6");
		buttons[7] = new JButton("7");
		buttons[8] = new JButton("8");
		buttons[9] = new JButton("9");
		buttons[10] = new JButton("A");
		buttons[11] = new JButton("B");
		buttons[12] = new JButton("C");
		buttons[13] = new JButton("D");
		buttons[14] = new JButton("E");
		buttons[15] = new JButton("F");
		buttons[16] = new JButton("=");
		buttons[17] = new JButton("+");
		buttons[18] = new JButton("-");
		buttons[19] = new JButton("\u2715");
		buttons[20] = new JButton("\u2797");
		buttons[21] = new JButton("Mod");
		buttons[22] = new JButton(".");
		buttons[23] = new JButton("\u00B1");
		buttons[24] = new JButton("(");
		buttons[25] = new JButton(")");
		buttons[26] = new JButton("CE");
		buttons[27] = new JButton("C");
		buttons[28] = new JButton("\u232B");
		buttons[29] = new JButton("\u2191");
		buttons[30] = new JButton("Lsh");
		buttons[31] = new JButton("Rsh");
		buttons[32] = new JButton("Or");
		buttons[33] = new JButton("Xor");
		buttons[34] = new JButton("Not");
		buttons[35] = new JButton("And");
		
		//adjust margins to fit text where necessary
		buttons[35].setMargin(new Insets(1, 1, 1, 1));
		buttons[30].setMargin(new Insets(1, 1, 1, 1));
		buttons[31].setMargin(new Insets(1, 1, 1, 1));
		buttons[33].setMargin(new Insets(1, 1, 1, 1));
		buttons[21].setMargin(new Insets(1, 1, 1, 1));
		
		//adjust fonts where necessary
		buttons[16].setFont(new Font("Dialog", Font.PLAIN, 20));
		buttons[17].setFont(new Font("Dialog", Font.PLAIN, 20));
		buttons[18].setFont(new Font("Dialog", Font.PLAIN, 20));
		buttons[23].setFont(new Font("Dialog", Font.PLAIN, 16));
		
		for(int index = 0; index < 10; index++)
		{
			buttons[index].setFont(new Font("Dialog", Font.PLAIN, 18));
		}
		
		//turn all but top row gray
		
		for(int index = 0; index < 30; index++)
		{
			buttons[index].setBackground(new Color(209, 209, 209));
			buttons[index].addActionListener(this);
		}
		
		//match top row to background
		
		for(int index = 30; index <= 35; index++)
		{
			buttons[index].setBackground(new Color(239, 239, 239));
			buttons[index].addActionListener(this);
		}
		
		//decimal point button  always disabled in programmer calculator
		
		buttons[22].setEnabled(false);
		
		//Start in decimal mode, so letters disabled
		
		for(int index = 10; index <= 15; index++)
		{
			buttons[index].setEnabled(false);
		}
	}
	
	//add buttons to the panel
	public void addButtons()
	{
		//set sizes of buttons
		
		for(int index = 0; index < buttons.length; index++)
		{
			buttons[index].setPreferredSize(new Dimension(54, 30)); 
		}
		
		//add top row
		for(int index = 30; index <= 35; index++)
		{
			add(buttons[index]);
		}
		
		//add second row
		add(buttons[29]);
		add(buttons[21]);
		add(buttons[26]);
		add(buttons[27]);
		add(buttons[28]);
		add(buttons[20]);
		
		//add third row
		add(buttons[10]);
		add(buttons[11]);
		add(buttons[7]);
		add(buttons[8]);
		add(buttons[9]);
		add(buttons[19]);
		
		//add fourth row
		add(buttons[12]);
		add(buttons[13]);
		add(buttons[4]);
		add(buttons[5]);
		add(buttons[6]);
		add(buttons[18]);
		
		//add fifth row
		add(buttons[14]);
		add(buttons[15]);
		add(buttons[1]);
		add(buttons[2]);
		add(buttons[3]);
		add(buttons[17]);
		
		//add sixth row
		add(buttons[24]);
		add(buttons[25]);
		add(buttons[23]);
		add(buttons[0]);
		add(buttons[22]);
		add(buttons[16]);
	}
	
	public void actionPerformed(ActionEvent e) //check for when buttons are clicked
	{
		//implement number buttons
		
		if(e.getSource() == buttons[1])
		{
			//if clicked after dividing by zero
			if(Calculator.getDisplay().equals("Cannot divide by zero"))
			{
				enableOps();
			}
			
			if(operating) //replace what is on display after clicking operator
			{
				Calculator.setDisplay("");
				operating = false;
			}
			
			if(validEntry(Calculator.getDisplay() + "1"))
			{
				Calculator.setDisplay(Calculator.getDisplay() + "1");
			}
		}
		else if(e.getSource() == buttons[2])
		{
			//if clicked after dividing by zero
			if(Calculator.getDisplay().equals("Cannot divide by zero"))
			{
				enableOps();
			}
			
			if(operating) //replace what is on display after clicking operator
			{
				Calculator.setDisplay("");
				operating = false;
			}
			
			if(validEntry(Calculator.getDisplay() + "2"))
			{
				Calculator.setDisplay(Calculator.getDisplay() + "2");
			}
		}
		else if(e.getSource() == buttons[3])
		{
			//if clicked after dividing by zero
			if(Calculator.getDisplay().equals("Cannot divide by zero"))
			{
				enableOps();
			}
			
			if(operating) //replace what is on display after clicking operator
			{
				Calculator.setDisplay("");
				operating = false;
			}
			
			if(validEntry(Calculator.getDisplay() + "3"))
			{
				Calculator.setDisplay(Calculator.getDisplay() + "3");
			}
		}
		else if(e.getSource() == buttons[4])
		{
			//if clicked after dividing by zero
			if(Calculator.getDisplay().equals("Cannot divide by zero"))
			{
				enableOps();
			}
			
			if(operating) //replace what is on display after clicking operator
			{
				Calculator.setDisplay("");
				operating = false;
			}
			
			if(validEntry(Calculator.getDisplay() + "4"))
			{
				Calculator.setDisplay(Calculator.getDisplay() + "4");
			}
		}
		else if(e.getSource() == buttons[5])
		{
			//if clicked after dividing by zero
			if(Calculator.getDisplay().equals("Cannot divide by zero"))
			{
				enableOps();
			}
			
			if(operating) //replace what is on display after clicking operator
			{
				Calculator.setDisplay("");
				operating = false;
			}
			
			if(validEntry(Calculator.getDisplay() + "5"))
			{
				Calculator.setDisplay(Calculator.getDisplay() + "5");
			}
		}
		else if(e.getSource() == buttons[6])
		{
			//if clicked after dividing by zero
			if(Calculator.getDisplay().equals("Cannot divide by zero"))
			{
				enableOps();
			}
			
			if(operating) //replace what is on display after clicking operator
			{
				Calculator.setDisplay("");
				operating = false;
			}
			
			if(validEntry(Calculator.getDisplay() + "6"))
			{
				Calculator.setDisplay(Calculator.getDisplay() + "6");
			}
		}
		else if(e.getSource() == buttons[7])
		{
			//if clicked after dividing by zero
			if(Calculator.getDisplay().equals("Cannot divide by zero"))
			{
				enableOps();
			}
			
			if(operating) //replace what is on display after clicking operator
			{
				Calculator.setDisplay("");
				operating = false;
			}
			
			if(validEntry(Calculator.getDisplay() + "7"))
			{
				Calculator.setDisplay(Calculator.getDisplay() + "7");
			}
		}
		else if(e.getSource() == buttons[8])
		{
			//if clicked after dividing by zero
			if(Calculator.getDisplay().equals("Cannot divide by zero"))
			{
				enableOps();
			}
			
			if(operating) //replace what is on display after clicking operator
			{
				Calculator.setDisplay("");
				operating = false;
			}
			
			if(validEntry(Calculator.getDisplay() + "8"))
			{
				Calculator.setDisplay(Calculator.getDisplay() + "8");
			}
		}
		else if(e.getSource() == buttons[9])
		{
			//if clicked after dividing by zero
			if(Calculator.getDisplay().equals("Cannot divide by zero"))
			{
				enableOps();
			}
			
			if(operating) //replace what is on display after clicking operator
			{
				Calculator.setDisplay("");
				operating = false;
			}
			
			if(validEntry(Calculator.getDisplay() + "9"))
			{
				Calculator.setDisplay(Calculator.getDisplay() + "9");
			}
		}
		else if(e.getSource() == buttons[0])
		{
			//if clicked after dividing by zero
			if(Calculator.getDisplay().equals("Cannot divide by zero"))
			{
				enableOps();
			}
			
			if(operating) //replace what is on display after clicking operator
			{
				Calculator.setDisplay("");
				operating = false;
			}
			
			if(!(Calculator.getDisplay().equals(""))) //If there is already something in display
			{
				if(validEntry(Calculator.getDisplay() + "0"))
				{
					Calculator.setDisplay(Calculator.getDisplay() + "0");
				}
			}
		}
		else if(e.getSource() == buttons[10])
		{
			if(operating) //replace what is on display after clicking operator
			{
				Calculator.setDisplay("");
				operating = false;
			}
			
			Calculator.setDisplay(Calculator.getDisplay() + "A");
		}
		else if(e.getSource() == buttons[11])
		{
			if(operating) //replace what is on display after clicking operator
			{
				Calculator.setDisplay("");
				operating = false;
			}
			
			Calculator.setDisplay(Calculator.getDisplay() + "B");
		}
		else if(e.getSource() == buttons[12])
		{
			if(operating) //replace what is on display after clicking operator
			{
				Calculator.setDisplay("");
				operating = false;
			}
			
			Calculator.setDisplay(Calculator.getDisplay() + "C");
		}
		else if(e.getSource() == buttons[13])
		{
			if(operating) //replace what is on display after clicking operator
			{
				Calculator.setDisplay("");
				operating = false;
			}
			
			Calculator.setDisplay(Calculator.getDisplay() + "D");
		}
		else if(e.getSource() == buttons[14])
		{
			if(operating) //replace what is on display after clicking operator
			{
				Calculator.setDisplay("");
				operating = false;
			}
			
			Calculator.setDisplay(Calculator.getDisplay() + "E");
		}
		else if(e.getSource() == buttons[15])
		{
			if(operating) //replace what is on display after clicking operator
			{
				Calculator.setDisplay("");
				operating = false;
			}
			
			Calculator.setDisplay(Calculator.getDisplay() + "F");
		}
		
		//implement erase buttons and backspace buttons
		
		else if(e.getSource() == buttons[26]) //CE
		{
			//if clicked after dividing by zero
			if(Calculator.getDisplay().equals("Cannot divide by zero"))
			{
				enableOps();
			}
			
			Calculator.setDisplay(""); //Clear display
		}
		
		else if(e.getSource() == buttons[27]) //C
		{
			//if clicked after dividing by zero
			if(Calculator.getDisplay().equals("Cannot divide by zero"))
			{
				enableOps();
			}
			
			Calculator.setDisplay(""); //Clear display
			Calculator.clearResults(); //Clear out the current operation
			Calculator.clearEquation();
			ops[Calculator.getMode()].pReset();
			Operators.clearOperations();
		}
		
		else if(e.getSource() == buttons[28]) //backspace
		{
			//if clicked after dividing by zero
			if(Calculator.getDisplay().equals("Cannot divide by zero"))
			{
				enableOps();
				Calculator.setDisplay("");
				Calculator.clearResults(); //Clear out the current operation
				ops[Calculator.getMode()].pReset();
			}
			else
			{
			//remove last character from string
			Calculator.setDisplay(Calculator.getDisplay().substring(0, Calculator.getDisplay().length() - 1));
			}
		}
		
		//implement up arrow button
		
		else if(e.getSource() == buttons[29])
		{
			swapLabels();
			if(changed)
			{
				buttons[29].setForeground(new Color(62, 125, 168));
			}
			else
			{
				buttons[29].setForeground(Color.BLACK);
			}
		}
		
		//implement operator buttons
		
		else if(e.getSource() == buttons[16]) // = button
		{
			//if clicked after dividing by zero
			if(Calculator.getDisplay().equals("Cannot divide by zero"))
			{
				enableOps();
				Calculator.setDisplay("");
				Calculator.clearResults(); //Clear out the current operation
				ops[Calculator.getMode()].pReset();
			}
			else
			{
				ops[Calculator.getMode()].equals();
				operating = true;
			}
		}
		
		else if(e.getSource() == buttons[17]) // + button
		{
			if(operating)
			{
				Operators.clearOperations();
				Calculator.popEquation();
				Calculator.popEquation();
			}
			
			ops[Calculator.getMode()].add();
			operating = true;
		}
		
		else if(e.getSource() == buttons[18]) // - button
		{
			if(operating)
			{
				Operators.clearOperations();
				Calculator.popEquation();
				Calculator.popEquation();
			}
			
			ops[Calculator.getMode()].subtract();
			operating = true;
		}
		
		else if(e.getSource() == buttons[19]) // * button
		{
			if(operating)
			{
				Operators.clearOperations();
				Calculator.popEquation();
				Calculator.popEquation();
			}
			
			ops[Calculator.getMode()].multiply();
			operating = true;
		}
		
		else if(e.getSource() == buttons[20]) // / button
		{
			if(operating)
			{
				Operators.clearOperations();
				Calculator.popEquation();
				Calculator.popEquation();
			}
			
			ops[Calculator.getMode()].divide();
			operating = true;
		}
		
		else if(e.getSource() == buttons[21]) // % button
		{
			if(operating)
			{
				Operators.clearOperations();
				Calculator.popEquation();
				Calculator.popEquation();
			}
			
			ops[Calculator.getMode()].mod();
			operating = true;
		}
		
		else if(e.getSource() == buttons[23]) // +- button
		{
			ops[Calculator.getMode()].negate();
		}
		
		//implement parenthesis button
		
		else if(e.getSource() == buttons[24])
		{
			ops[Calculator.getMode()].pStart();
			
			if(operating)
			{
				Calculator.setDisplay("");
			}
			
			else
			{
				operating = true;
			}
		}
		
		else if(e.getSource() == buttons[25])
		{
			ops[Calculator.getMode()].pEnd();
		}
	}
	
	public void swapLabels()
	{
		if(changed)
		{
			buttons[30].setText("Lsh");
			buttons[31].setText("Rsh");
			changed = false;
		}
		else
		{
			buttons[30].setText("RoL");
			buttons[31].setText("RoR");
			changed = true;
		}
	}
	
	public void binButtons() //disable buttons for binary mode
	{
		for(int index = 2; index <= 15; index++)
		{
			buttons[index].setEnabled(false);
		}
	}
	
	public void octButtons() //disable and enable buttons for octal mode
	{
		//Enable 2 - 7
		for(int index = 2; index <= 7; index++)
		{
			buttons[index].setEnabled(true);
		}
		
		//Disable 8 - F
		for(int index = 8; index <= 15; index++)
		{
			buttons[index].setEnabled(false);
		}
	}
	
	public void decButtons() //disable and enable buttons for decimal mode
	{
		//Enable 2 - 9
		for(int index = 2; index <= 9; index++)
		{
			buttons[index].setEnabled(true);
		}
		
		//Disable A - F
		for(int index = 10; index <= 15; index++)
		{
			buttons[index].setEnabled(false);
		}
	}
	
	public void hexButtons() //disable and enable buttons for hexadecimal mode
	{
		//Enable 2 - F
		for(int index = 2; index <= 15; index++)
		{
			buttons[index].setEnabled(true);
		}
	}
	
	/*This class makes sure that adding the indicated number won't make the dispay string
	 * longer than it can be for the length mode that the calculator is in.
	 */
	public boolean validEntry(String entry)
	{
		boolean valid = true;
		
		long val1 = 0;
		long val2 = 0;
		
		//get values, convert to decimal
		if(Calculator.getMode() == 0)
		{
			if(Calculator.getDisplay().length() > 0)
			{
				val1 = Long.parseLong(Calculator.getDisplay(), 2);
			}
			val2 = Long.parseLong(entry, 2);
		}
		
		else if(Calculator.getMode() == 1)
		{
			if(Calculator.getDisplay().length() > 0)
					{
						val1 = Long.parseLong(Calculator.getDisplay(), 8);
					}
			val2 = Long.parseLong(entry, 8);
		}
		
		else if(Calculator.getMode() == 2)
		{
			if(Calculator.getDisplay().length() > 0)
			{
				val1 = Long.parseLong(Calculator.getDisplay());
			}
			val2 = Long.parseLong(entry);
		}
		
		else if(Calculator.getMode() == 3)
		{
			if(Calculator.getDisplay().length() > 0)
			{
				val1 = Long.parseLong(Calculator.getDisplay(), 16);
			}
			val2 = Long.parseLong(entry, 16);
		}

		//check if number is valid for size limit
		if(Calculator.getLengthMode() == 0)
		{
			if(val1 > 0 && val2 < 0)
			{
				valid = false;
			}
			else if(val1 < 0 && val2 > 0)
			{
				valid = false;
			}
		}
		else if(Calculator.getLengthMode() == 1)
		{
			if(val2 > maxLength[1])
			{
				valid = false;
			}
			else if(val2 < minLength[1])
			{
				valid = false;
			}
		}
		else if(Calculator.getLengthMode() == 2)
		{
			if(val2 > maxLength[2])
			{
				valid = false;
			}
			else if(val2 < minLength[2])
			{
				valid = false;
			}
		}
		else if(Calculator.getLengthMode() == 3)
		{
			if(val2 > maxLength[3])
			{
				valid = false;
			}
			else if(val2 < minLength[3])
			{
				valid = false;
			}
		}
		return valid;
	}
	
	public void disableOps() //when user divides by zero, disables the necessary buttons.
	{
		//30 - 35, 17- 21, 23, 24 - 25, 29
		for(int index = 17; index <= 21; index++)
		{
			buttons[index].setEnabled(false);
		}
		for(int index = 23; index <= 25; index++)
		{
			buttons[index].setEnabled(false);
		}
		for(int index = 29; index <= 35; index++)
		{
			buttons[index].setEnabled(false);
		}
	}
	
	public void enableOps() //Re enables buttons that were disabled from dividing by zero
	{
		//30 - 35, 17- 21, 23, 24 - 25, 29
		for(int index = 17; index <= 21; index++)
		{
			buttons[index].setEnabled(true);
		}
		for(int index = 23; index <= 25; index++)
		{
			buttons[index].setEnabled(true);
		}
		for(int index = 29; index <= 35; index++)
		{
			buttons[index].setEnabled(true);
		}
	}
	
}
