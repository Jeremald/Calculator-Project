/* This class handles calculating operations for octal values. This class extends the operators
 * class.
 */
public class OctOperators extends Operators
{
	public void calcAdd()
	{
		long opp1 = Long.parseUnsignedLong(Calculator.getResult(Calculator.getResultLength() - 2), 8);
		long opp2 = Long.parseUnsignedLong(Calculator.getResult(Calculator.getResultLength() - 1), 8);
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
		
		Calculator.addResult(Long.toOctalString(result)); //Store result for further calculations
		Calculator.setDisplay(Long.toOctalString(result)); //Display the result
	}
	
	public void calcSubtract()
	{
		long opp1 = Long.parseUnsignedLong(Calculator.getResult(Calculator.getResultLength() - 2), 8);
		long opp2 = Long.parseUnsignedLong(Calculator.getResult(Calculator.getResultLength() - 1), 8);
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
		
		Calculator.addResult(Long.toOctalString(result)); //Store result for further calculations
		Calculator.setDisplay(Long.toOctalString(result)); //Display the result
	}
	
	public void calcMultiply()
	{
		long opp1 = Long.parseUnsignedLong(Calculator.getResult(Calculator.getResultLength() - 2), 8);
		long opp2 = Long.parseUnsignedLong(Calculator.getResult(Calculator.getResultLength() - 1), 8);
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
		
		Calculator.addResult(Long.toOctalString(result)); //Store result for further calculations
		Calculator.setDisplay(Long.toOctalString(result)); //Display the result
	}
	
	public void calcDivide()
	{
		long opp1 = Long.parseUnsignedLong(Calculator.getResult(Calculator.getResultLength() - 2), 8);
		long opp2 = Long.parseUnsignedLong(Calculator.getResult(Calculator.getResultLength() - 1), 8);
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
		
			Calculator.addResult(Long.toOctalString(result)); //Store result for further calculations
			Calculator.setDisplay(Long.toOctalString(result)); //Display the result
		}
	}
	
	public void calcMod()
	{
		long opp1 = Long.parseUnsignedLong(Calculator.getResult(Calculator.getResultLength() - 2), 8);
		long opp2 = Long.parseUnsignedLong(Calculator.getResult(Calculator.getResultLength() - 1), 8);
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
		
		Calculator.addResult(Long.toOctalString(result)); //Store result for further calculations
		Calculator.setDisplay(Long.toOctalString(result)); //Display the result
	}
	
	public void negate()
	{
		long opp1 = Long.parseUnsignedLong(Calculator.getDisplay(), 8);
		long result = -1 * opp1;
		Calculator.setDisplay(Long.toOctalString(result)); //Display the result
	}
}
