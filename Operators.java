/* This class allows the creation of subclasses that will handle operations. All sublcasses must
 * implement an add, subtract, multiply, divide, and mod method.
 */

import java.util.*;

public abstract class Operators 
{
	private static boolean adding = false;
	private static boolean subtracting = false;
	private static boolean multiplying = false;
	private static boolean dividing = false;
	private static boolean modding = false;
	private static boolean pAdding = false;
	private static boolean pSubtracting = false;
	private static boolean pMultiplying = false;
	private static boolean pDividing = false;
	private static boolean pModding = false;
	private static ArrayList<Boolean> pOps = new ArrayList<Boolean>(); //used for handling operations for parenthesis
	private static ArrayList<String> temp = new ArrayList<String>(); //arraylist for nested parenthesis
	
	public void checkOperations()
	{
		
		if(multiplying) //if in process of multiplying, multiply, then not multiplying anymore
		{
			calcMultiply();
			multiplying = false;
		}
		if(dividing) //if in process of dividing, divide, then not dividing anymore
		{
			calcDivide();
			dividing = false;
		}
		if(modding) //if in process of modding, mod, then not modding anymore
		{
			calcMod();
			modding = false;
		}
		if(adding) //if in process of adding, add, then not adding anymore
		{
			calcAdd();
			adding = false;
		}
		if(subtracting) //if in process of subtracting, subtract, then not subtracting anymore
		{
			calcSubtract();
			subtracting = false;
		}
	}
	public void add()
	{
		if(Calculator.getDisplay().length() == 0)
		{
			Calculator.addResult("0");
			Calculator.addToEquation("0");
		}
		else
		{
			Calculator.addResult(Calculator.getDisplay()); //add current display to results
			Calculator.addToEquation(Calculator.getDisplay());
		}
		
		Calculator.addToEquation("+");
		
		checkOperations(); //check if in middle of other operation
		adding = true;
	}
	public void subtract()
	{
		if(Calculator.getDisplay().length() == 0)
		{
			Calculator.addResult("0");
			Calculator.addToEquation("0");
		}
		else
		{
			Calculator.addResult(Calculator.getDisplay()); //add current display to results
			Calculator.addToEquation(Calculator.getDisplay());
		}
		
		Calculator.addToEquation("-");
		
		checkOperations(); //check if in middle of other operation
		subtracting = true;
	}
	public void multiply()
	{
		if(Calculator.getDisplay().length() == 0)
		{
			Calculator.addResult("0");
			Calculator.addToEquation("0");
		}
		else
		{
			Calculator.addResult(Calculator.getDisplay()); //add current display to results
			Calculator.addToEquation(Calculator.getDisplay());
		}
		
		Calculator.addToEquation("*");
		
		if(adding || subtracting) //Do multiplication first
		{
			if(multiplying) //handle multiplication but not addition or subtraction yet
			{
				calcMultiply();
			}
			
			else
			{
				multiplying = true;
			}
		}
		
		else
		{
		checkOperations(); //check if in middle of other operation
		multiplying = true;
		}
	}
	public void divide()
	{
		if(Calculator.getDisplay().length() == 0)
		{
			Calculator.addResult("0");
			Calculator.addToEquation("0");
		}
		else
		{
			Calculator.addResult(Calculator.getDisplay()); //add current display to results
			Calculator.addToEquation(Calculator.getDisplay());
		}
		
		Calculator.addToEquation("/");
		
		if(adding || subtracting) //Do division first
		{
			if(dividing) //handle division but not addition or subtraction yet
			{
				calcDivide();
			}
			
			else
			{
				dividing = true;
			}
		}
		
		else
		{
		checkOperations(); //check if in middle of other operation
		dividing = true;
		}
	}
	public void mod()
	{
		if(Calculator.getDisplay().length() == 0)
		{
			Calculator.addResult("0");
			Calculator.addToEquation("0");
		}
		else
		{
			Calculator.addResult(Calculator.getDisplay()); //add current display to results
			Calculator.addToEquation(Calculator.getDisplay());
		}
		
		Calculator.addToEquation("Mod");
		
		if(adding || subtracting) //Do mod first
		{
			if(modding) //handle mod but not addition or subtraction yet
			{
				calcMod();
			}
			
			else
			{
				modding = true;
			}
		}
		
		else
		{
		checkOperations(); //check if in middle of other operation
		modding = true;
		}
	}
	public void equals()
	{
		if(Calculator.getDisplay().length() == 0)
		{
			Calculator.addResult("0");
		}
		else
		{
			Calculator.addResult(Calculator.getDisplay()); //add current display to results
		}
		
		Calculator.clearEquation();
		
		checkOperations(); //check if in middle of other operation
	}
	
	public static void clearOperations()
	{
		adding = subtracting = multiplying = dividing = modding = false;
	}
	
	public void pStart() //When left parenthesis is clicked
	{
		Calculator.addToEquation("(");
		
		if(multiplying)
		{
			//add corresponding values to arrayList. Allows nested parenthesis.
			pOps.add(true);
			pOps.add(false);
			pOps.add(false);
			pOps.add(false);
			pOps.add(false);
			
			multiplying = false;
			temp.add(Calculator.getResult(Calculator.getResultLength() - 1)); //store number in temp arraylist
		}
		
		else if(dividing)
		{
			//add corresponding values to arrayList. Allows nested parenthesis.
			pOps.add(false);
			pOps.add(true);
			pOps.add(false);
			pOps.add(false);
			pOps.add(false);
			
			dividing = false;
			temp.add(Calculator.getResult(Calculator.getResultLength() - 1)); //store number in temp arraylist
		}
		
		else if(modding)
		{
			//add corresponding values to arrayList. Allows nested parenthesis.
			pOps.add(false);
			pOps.add(false);
			pOps.add(true);
			pOps.add(false);
			pOps.add(false);
			
			modding = false;
			temp.add(Calculator.getResult(Calculator.getResultLength() - 1)); //store number in temp arraylist
		}
		
		else if(adding)
		{
			//add corresponding values to arrayList. Allows nested parenthesis.
			pOps.add(false);
			pOps.add(false);
			pOps.add(false);
			pOps.add(true);
			pOps.add(false);
			
			adding = false;
			temp.add(Calculator.getResult(Calculator.getResultLength() - 1)); //store number in temp arraylist
		}
		
		else if(subtracting)
		{
			//add corresponding values to arrayList. Allows nested parenthesis.
			pOps.add(false);
			pOps.add(false);
			pOps.add(false);
			pOps.add(false);
			pOps.add(true);		
			
			subtracting = false;
			temp.add(Calculator.getResult(Calculator.getResultLength() - 1)); //store number in temp arraylist
		}
		
		
	}
	
	public void pEnd() //When right parenthesis is clicked
	{
		Calculator.addResult(Calculator.getDisplay()); //put in last number
		Calculator.addToEquation(Calculator.getDisplay());
		Calculator.addToEquation(")");
		checkOperations(); //do any remaining operations
		
		String temp2 = Calculator.getResult(Calculator.getResultLength() - 1);
		Calculator.clearResults(); //clear results to help with next operation
		
		if(temp.size() > 0) //if there is something in temp
		{
			Calculator.addResult(temp.remove(temp.size() - 1)); //pops last temp value
		}
		
		if(pOps.size() > 0)
		{
			if(pOps.get(pOps.size() - 5)) //multiplying
			{
				multiplying = true;
			}
		
			else if(pOps.get(pOps.size() - 4)) //dividing
			{
				dividing = true;
			}
		
			else if(pOps.get(pOps.size() - 3)) //modding
			{
				modding = true;
			}
		
			else if(pOps.get(pOps.size() - 2)) //adding
			{
				adding = true;
			}
		
			else if(pOps.get(pOps.size() - 1)) //subtracting
			{
				subtracting = true;
			}
		
			for(int count = 0; count < 5; count++) //Remove operators for this set of parenthesis
			{
				pOps.remove(pOps.size() - 1);
			}
		}
	}
	
	public void pReset()
	{
		pAdding = pSubtracting = pMultiplying = pDividing = pModding = false;
		temp.clear();
	}
	
	
	public abstract void calcAdd();
	public abstract void calcSubtract();
	public abstract void calcMultiply();
	public abstract void calcDivide();
	public abstract void calcMod();
	public abstract void negate();
}
