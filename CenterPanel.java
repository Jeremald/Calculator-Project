/* This class creates the panel that will hold the top row of buttons. The panel will use the
 * grid bag layout manager.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CenterPanel extends JPanel implements ActionListener
{
	private JButton[] buttons = new JButton[5];
	
	/* Button array key:
	 * 0 - Key pad panel button
	 * 1 - Binary panel button
	 * 2 - Binary panel mode button
	 * 3 - MS
	 * 4 - M
	 */
	
	//Icon objects for the button icons
	Icon keyBlue = new ImageIcon("keyPadBlue.png");
	Icon keyBlack = new ImageIcon("keyPadBlack.png");
	Icon bitBlue = new ImageIcon("bitPadBlue.png");
	Icon bitBlack = new ImageIcon("bitPadBlack.png");
	
	
	public CenterPanel()
	{
		setLayout(new GridBagLayout());
		setPreferredSize(new Dimension(Calculator.getWidth(), 30));
		createButtons();
		addButtons();
		setVisible(true);
	}
	
	//Creates buttons in panel
	public void createButtons()
	{
		//buttons[0] = new JButton("Pad");
		buttons[0] = new JButton(keyBlue);
		//buttons[1] = new JButton("Bin");
		buttons[1] = new JButton(bitBlack);
		buttons[2] = new JButton("QWORD");
		buttons[3] = new JButton("MS");
		buttons[4] = new JButton("M");
		
		buttons[4].setEnabled(false); //Always disabled in programmer calculator
		
		//set color of buttons
		for(int index = 0; index < 5; index++)
		{
			buttons[index].setBackground(new Color(239, 239, 239));
			buttons[index].addActionListener(this);
		}
		
		//set middle button text to blue
		buttons[2].setForeground(new Color(62, 125, 168));
		
		//add tool tips for first two buttons and for MS button
		buttons[0].setToolTipText("Full keypad");
		buttons[1].setToolTipText("Bit toggling keypad");
		buttons[3].setToolTipText("Memory store");
	}
	
	//adds buttons to the panel
	public void addButtons()
	{
		GridBagConstraints con = new GridBagConstraints(); //used to manage button layout
		
		con.fill = GridBagConstraints.BOTH; //makes it take up all space in cell
		con.gridx = 0; //set which cell its in
		con.gridy = 0;
		add(buttons[0], con);
		
		con.fill = GridBagConstraints.BOTH;
		con.weightx = 0.5;
		con.gridx = 1;
		con.gridy = 0;
		add(buttons[1], con);

		con.fill = GridBagConstraints.BOTH;
		con.weightx = 0.5;
		con.gridx = 2;
		con.gridy = 0;
		//con.gridwidth = 2; //make it span two cells
		add(buttons[2], con);

		con.fill = GridBagConstraints.BOTH;
		con.weightx = 0.5;
		con.gridx = 3;
		con.gridy = 0;
		add(buttons[3], con);
		
		con.fill = GridBagConstraints.BOTH;
		con.weightx = 0.5;
		con.gridx = 4;
		con.gridy = 0;
		add(buttons[4], con);
	}
	
	public void actionPerformed(ActionEvent e) //Check when buttons are clicked
	{
		if(e.getSource() == buttons[1]) //bit toggle keypad button
		{
			if(Calculator.getBottomMode() == 0)
			{
				Calculator.setBottomMode(1);
				
				//change icons
				
				buttons[0].setIcon(keyBlack);
				buttons[1].setIcon(bitBlue);
			}
		}
		
		if(e.getSource() == buttons[0]) //full keypad button
		{
			if(Calculator.getBottomMode() == 1)
			{
				Calculator.setBottomMode(0);
				
				//change icons
				
				buttons[0].setIcon(keyBlue);
				buttons[1].setIcon(bitBlack);
			}
		}
		
		if(e.getSource() == buttons[2]) //center button
		{
			//determine length mode, switch to next one
			if(Calculator.getLengthMode() == 0)
			{
				buttons[2].setText("DWORD");
				Calculator.setLengthMode(1);
			}
			else if(Calculator.getLengthMode() == 1)
			{
				buttons[2].setText("WORD");
				Calculator.setLengthMode(2);
			}
			else if(Calculator.getLengthMode() == 2)
			{
				buttons[2].setText("BYTE");
				Calculator.setLengthMode(3);
			}
			else if(Calculator.getLengthMode() == 3)
			{
				buttons[2].setText("QWORD");
				Calculator.setLengthMode(0);
			}
			
		}
	}
}
