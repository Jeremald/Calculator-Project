/* This class handles calculating operations for decimal values. This class extends the operators
 * class.
 */
public class DecOperators extends Operators
{
	public void calcAdd()
	{
		long opp1 = Long.parseLong(Calculator.getResult(Calculator.getResultLength() - 2));
		long opp2 = Long.parseLong(Calculator.getResult(Calculator.getResultLength() - 1));
		long result;
		
		//modify values for length mode if necessary
		if(Calculator.getLengthMode() == 1)
		{
			result = (int)(opp1 + opp2);
		}
		else if(Calculator.getLengthMode() == 2)
		{
			result = (short)(opp1 + opp2);
		}
		else if(Calculator.getLengthMode() == 3)
		{
			result = (byte)(opp1 + opp2);
		}
		else
		{
			result = opp1 + opp2;
		}
		
		Calculator.addResult(""+result); //Store result for further calculations
		Calculator.setDisplay(""+result); //Display the result
	}
	
	public void calcSubtract()
	{
		long opp1 = Long.parseLong(Calculator.getResult(Calculator.getResultLength() - 2));
		long opp2 = Long.parseLong(Calculator.getResult(Calculator.getResultLength() - 1));
		long result;
		
		//modify values for length mode if necessary
		if(Calculator.getLengthMode() == 1)
		{
			result = (int)(opp1 - opp2);
		}
		else if(Calculator.getLengthMode() == 2)
		{
			result = (short)(opp1 - opp2);
		}
		else if(Calculator.getLengthMode() == 3)
		{
			result = (byte)(opp1 - opp2);
		}
		else
		{
			result = opp1 - opp2;
		}
		
		Calculator.addResult(""+result); //Store result for further calculations
		Calculator.setDisplay(""+result); //Display the result
	}
	
	public void calcMultiply()
	{
		long opp1 = Long.parseLong(Calculator.getResult(Calculator.getResultLength() - 2));
		long opp2 = Long.parseLong(Calculator.getResult(Calculator.getResultLength() - 1));
		long result;
		
		//modify values for length mode if necessary
		if(Calculator.getLengthMode() == 1)
		{
			result = (int)(opp1 * opp2);
		}
		else if(Calculator.getLengthMode() == 2)
		{
			result = (short)(opp1 * opp2);
		}
		else if(Calculator.getLengthMode() == 3)
		{
			result = (byte)(opp1 * opp2);
		}
		else
		{
			result = opp1 * opp2;
		}
		
		Calculator.removeLast(); //replace last two values with their product
		Calculator.removeLast();
		
		Calculator.addResult(""+result); //Store result for further calculations
		Calculator.setDisplay(""+result); //Display the result
	}
	
	public void calcDivide()
	{
		long opp1 = Long.parseLong(Calculator.getResult(Calculator.getResultLength() - 2));
		long opp2 = Long.parseLong(Calculator.getResult(Calculator.getResultLength() - 1));
		long result;
		
		//if trying to divide by zero
		if(opp2 == 0)
		{
			Calculator.disable();
		}
		
		else
		{
			//modify values for length mode if necessary
			if(Calculator.getLengthMode() == 1)
			{
				result = (int)(opp1 / opp2);
			}
			else if(Calculator.getLengthMode() == 2)
			{
				result = (short)(opp1 / opp2);
			}
			else if(Calculator.getLengthMode() == 3)
			{
				result = (byte)(opp1 / opp2);
			}
			else
			{
				result = opp1 / opp2;
			}
		
			Calculator.removeLast(); //replace last two values with their quotient
			Calculator.removeLast();
		
			Calculator.addResult(""+result); //Store result for further calculations
			Calculator.setDisplay(""+result); //Display the result
		}
	}
	
	public void calcMod()
	{
		long opp1 = Long.parseLong(Calculator.getResult(Calculator.getResultLength() - 2));
		long opp2 = Long.parseLong(Calculator.getResult(Calculator.getResultLength() - 1));
		long result;
		
		//modify values for length mode if necessary
		if(Calculator.getLengthMode() == 1)
		{
			result = (int)(opp1 % opp2);
		}
		else if(Calculator.getLengthMode() == 2)
		{
			result = (short)(opp1 % opp2);
		}
		else if(Calculator.getLengthMode() == 3)
		{
			result = (byte)(opp1 % opp2);
		}
		else
		{
			result = opp1 % opp2;
		}
		
		Calculator.removeLast(); //replace last two values with their mod
		Calculator.removeLast();
		
		Calculator.addResult(""+result); //Store result for further calculations
		Calculator.setDisplay(""+result); //Display the result
	}
	
	public void negate()
	{
		long opp1 = Long.parseLong(Calculator.getDisplay());
		long result = -1 * opp1;
		Calculator.setDisplay(""+result); //Display the result
	}
}
