/* Main class for the calculator. Creates the frame, manages panels.
 */

//import java.awt.Component;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


public class Calculator
{
	private static final int WIDTH = 338;
	private static final int HEIGHT = 554;
	private static String display = "";
	private static ArrayList<String> results = new ArrayList<String>();
	private static ArrayList<String> equation = new ArrayList<String>();
	private static CalcPanel calc = new CalcPanel();
	private static BitPanel bit;
	private static boolean changed = false;
	private static int mode = 2;
	// Mode Key : 0 - Binary, 1 - Octal, 2 - Decimal, 3 - Hexadecimal
	private static int bottomMode = 0;
	//Bottom mode key: 0 - Full Keypad, 1 - Bit toggling keypad
	private static int lengthMode = 0;
	//Length mode key: 0 - QWORD, 1 - DWORD, 2 - WORD, 3 - BYTE
	
	public static void main(String[] args)
	{
		JFrame frame = new JFrame();
		
		
		frame.setPreferredSize(new Dimension(WIDTH, HEIGHT)); //set dimensions of frame
		frame.setTitle("Calcuator"); //title for top of window
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//add initial panels
		
		TopPanel top = new TopPanel();
		CenterPanel center = new CenterPanel();
		
		CardLayout card = new CardLayout(); //used to switch between panels on bottom half
		JPanel bottoms = new JPanel(card);
		calc = new CalcPanel();
		bit = new BitPanel();
		
		bottoms.add(calc); 
		bottoms.add(bit);
		
		frame.pack(); //Use panels preferred sizes
		frame.setLocationRelativeTo(null); //make window open in center of screen
		
		
		frame.add(top, BorderLayout.NORTH); //add panel to frame
		frame.add(center, BorderLayout.CENTER);
		frame.add(bottoms, BorderLayout.SOUTH);
		frame.setVisible(true);

		//setup timer
		
		Timer t = new Timer(30, new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				top.repaint(); //call panel's paintComponent method
				
				if(changed) //when switching between keypads
				{
					changed = false;
					card.next(bottoms); //switch bottom panel
				}
				
			}
		});
		
		t.start();
	}
	
	//set methods
	
	public static void setDisplay(String d)
	{
		display = d;
		
		String binString = display; //used for binary keypad
		
		if(display.equals("")) //if display is cleared
		{
			resetBitButtons();
		}
		
		else
		{
			long temp = 0;
			
			//get correct parsing
			if(mode == 1)
			{
				temp = Long.parseUnsignedLong(display, 8);
			}
			else if(mode == 2)
			{
				temp = Long.parseLong(display);
			}
			else if(mode == 3)
			{
				temp = Long.parseUnsignedLong(display, 16);
			}
			
			//get correct string for length mode
			if(lengthMode == 0)
			{
				binString = Long.toBinaryString(temp);
			}
			else if(lengthMode == 1)
			{
				binString = Integer.toBinaryString((int)temp);
			}
			else if(lengthMode == 2)
			{
				binString = Integer.toBinaryString(0xFFFF & (short)temp);
			}
			else if(lengthMode == 3)
			{
				binString = Integer.toBinaryString(0xFF & (byte)temp);
			}
			
			bit.updateButtons(binString); //match bit keypad to display
		}
	}
	
	public static void setMode(int m)
	{
		mode = m;
	}
	
	public static void addResult(String value)
	{
		results.add(value);
	}
	
	public static void addToEquation(String value)
	{
		equation.add(value);
	}
	
	public static void clearResults()
	{
		results.clear();
	}
	
	public static void clearEquation()
	{
		equation.clear();
	}
	
	public static void removeLast()
	{
		results.remove(results.size() - 1);
	}
	
	public static void popEquation()
	{
		equation.remove(equation.size() - 1);
	}
	
	//when the calculator mode changes, convert the results to the new base
	public static void convertResults(int radix, int newRadix)
	{
		for(int index = 0; index < results.size(); index++)
		{
			long temp = Long.parseLong(results.get(index), radix); //old value
			
			//get new value for correct base
			if(newRadix == 2)
			{
				results.set(index, Long.toBinaryString(temp));
			}
			
			else if(newRadix == 8)
			{
				results.set(index, Long.toOctalString(temp));
			}
			
			else if(newRadix == 10)
			{
				results.set(index, "" + temp);
			}
			
			else if(newRadix == 16)
			{
				results.set(index, Long.toHexString(temp));
			}
		}
	}
	
	//when calculator mode changes, convert values in equation to the new base
	public static void convertEquation(int radix, int newRadix)
	{
		for(int index = 0; index < equation.size(); index++)
		{
			try //will only work for number
			{
				long temp = Long.parseLong(equation.get(index), radix); //old value
				
				//get correct value for the new base
				if(newRadix == 2)
				{
					equation.set(index, Long.toBinaryString(temp));
				}
				
				else if(newRadix == 8)
				{
					equation.set(index, Long.toOctalString(temp));
				}
				
				else if(newRadix == 10)
				{
					equation.set(index, "" + temp);
				}
				
				else if(newRadix == 16)
				{
					equation.set(index, Long.toHexString(temp));
				}
			}
			catch(NumberFormatException e)
			{
				//don't change operators or parenthesis
			}
		}
	}
	
	//When the calculator mode changes, change the display to the new base
	public static void convertDisplay(int radix, int newRadix)
	{
		if(display.length() > 0) //If display is empty, don't do anything
		{
			long temp; //old value
			
			//get correct old value based on the current base
			if(radix == 10)
			{
				temp = Long.parseLong(display, radix);
			}
			else
			{
				temp = Long.parseUnsignedLong(display, radix);
			}
		
			//get correct new value based on new base
			if(newRadix == 2)
			{
				display = Long.toBinaryString(temp);
			}
		
			else if(newRadix == 8)
			{
				display = Long.toOctalString(temp);
			}
		
			else if(newRadix == 10)
			{
				display = "" + temp;
			}
		
			else if(newRadix == 16)
			{
				display = Long.toHexString(temp);
			}
		}
	}
	
	//get methods
	
	public static int getWidth()
	{
		return WIDTH;
	}
	
	public static int getHeight()
	{
		return HEIGHT;
	}
	
	public static String getDisplay()
	{
		return display;
	}
	
	public static int getMode()
	{
		return mode;
	}
	
	public static String getResult(int index)
	{
		return results.get(index);
	}
	
	public static int getResultLength()
	{
		return results.size();
	}
	
	public static void negateLastResult()
	{
		results.set(results.size() - 1, -1 * Long.parseLong(results.get(results.size() - 1)) + "");
	}
	
	public static int getBottomMode()
	{
		return bottomMode;
	}
	
	public static int getLengthMode()
	{
		return lengthMode;
	}
	
	public static ArrayList<String> getEquation()
	{
		return equation;
	}
	//other methods
	
	public static void binButtons()
	{
		calc.binButtons();
	}
	
	public static void octButtons()
	{
		calc.octButtons();
	}
	
	public static void decButtons()
	{
		calc.decButtons();
	}
	
	public static void hexButtons()
	{
		calc.hexButtons();
	}
	
	public static void setBottomMode(int m)
	{
		changed = true;
		bottomMode = m;
	}
	
	public static void setLengthMode(int m)
	{
		lengthMode = m;
		updateBitButtons();
	}
	
	public static void resetBitButtons()
	{
		bit.resetButtons();
	}
	
	public static void updateBitButtons()
	{
		bit.updateButtons();
	}
	
	public static void disable() //when user tries to divide by zero
	{
		display = "Cannot divide by zero";
		calc.disableOps();
	}
}
