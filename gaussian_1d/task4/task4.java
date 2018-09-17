import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.io.*;
/**
 * 
 */

/**
 * @author liquidf4ntasy
 * Satyajit Desmukh
 * UTA ID: 1001417727
 *
 */
public class task4 {

	/**
	 * @param args
	 * @throws IOException
	 */
	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException
	{

       // String s = "C:/Users/liquidf4ntasy/Desktop/yeast_training.txt";
	            Scanner console = new Scanner(System.in);           
	            System.out.println("File to be read: ");
	            String inputFile = console.next();
	            File file = new File(inputFile);
	            Scanner in = new Scanner(file);
	            int words=0,lines=0;

	            while(in.hasNext())
	            {
	                in.next();
	                words++;
	            }in.close();
	            in = new Scanner(file);
	            while(in.hasNextLine())
	            {
	                in.nextLine();
	                lines++;
	            }in.close();
	            
	            int rows = lines;
	            int columns = words/lines;
	          
		
				float[][] myArray = new float[rows][columns];
				
			    Set<Integer> set = new HashSet<Integer>();
			    
				int x=0, y=0;

				BufferedReader pin = new BufferedReader(new FileReader(file));	//reading files in specified directory
					String line;
					int count=0;
					while ((line = pin.readLine()) != null)	//file reading
					{   
						count++;
						String[] values = line.replaceAll("^[\\s]+|[\\s]+$", "").split("[\\s]+"); 
						int k=values.length;
			        	y=0;
						for (int i=0;i <k;i++)
						{
		        		float newvalue = Float.parseFloat(values[i]);
						myArray[x][y] = newvalue;
						if(i==k-1)
						{	set.add(Math.round(newvalue));
						}
						y=y+1;
						}
						x=x+1;
					}
		        	pin.close();
		        	
		        	
				    float[][] finalArray = new float[set.size()][columns+3];
				    float[][] VArray = new float[set.size()][columns+3];
				    float[][] ZArray = new float[set.size()][columns+3];

				    ArrayList<Integer> list = new ArrayList<Integer>(set);
				    Object[] ClassLabels = list.toArray();
				    
				    // convert array to float for Final ARRAY
				    for (int i = 0; i < ClassLabels.length; i++) {
				    //    Object object = ClassLabels[i];
				        finalArray[i][columns-1]= Float.valueOf((Integer) ClassLabels[i]);
				        VArray[i][columns-1]= Float.valueOf((Integer) ClassLabels[i]);
				        ZArray[i][columns-1]= Float.valueOf((Integer) ClassLabels[i]);
				      }

				    int avg=0;
				    int dimension=0;
				    float sqrSum=0;
				    // best working!
				 for(dimension=0 ; dimension<ClassLabels.length;dimension++)
				  { 
					 avg=0;
					 sqrSum=0;
				    for (int i = 0; i < myArray.length; i++) 
				    {
	
		        	    if (myArray[i][columns-1] == finalArray[dimension][columns-1])
		        	    {
		        	    avg = avg+1;
	        	    	finalArray[dimension][columns]=avg;

		        	    	for (int j = 0; j < myArray[i].length-1; j++) 
		        	    	{
		        	    		sqrSum = myArray[i][j] * myArray[i][j];   
		        	    		VArray[dimension][j] =VArray[dimension][j]+ sqrSum;
		        	    		// normal total calculation/summing
		        	    		finalArray[dimension][j] = myArray[i][j] + finalArray[dimension][j];
		        	    		
		        	    	}
		        	    }	
		        	    
				     }
				    
	 }				    
				    for (int i = 0; i < finalArray.length; i++) 
				    {
		        	    for (int j = 0; j < finalArray[i].length; j++) 
		        	    {
		        	    	if(j<finalArray[i].length-4)
		        	    	{
				        	// Variance Step 1
		        	    	ZArray[i][j] = (finalArray[i][j] * finalArray[i][j]) / finalArray[i][columns];
		        	    	ZArray[i][j] = (VArray[i][j] - ZArray[i][j]) / ( finalArray[i][columns] - 1);
		        	    	BigDecimal roundfinalPriceV = new BigDecimal(ZArray[i][j]).setScale(2,BigDecimal.ROUND_HALF_UP);
		        		    ZArray[i][j] = roundfinalPriceV.floatValue();
		        	    	
		        	    	finalArray[i][j] =  finalArray[i][j] / finalArray[i][columns];
		        		    BigDecimal roundfinalPrice = new BigDecimal(finalArray[i][j]).setScale(2,BigDecimal.ROUND_HALF_UP);
		        		    finalArray[i][j] = roundfinalPrice.floatValue();
		        	    	}
		        	    	
		        	    	if(j == finalArray[i].length-2)
		        	    	{
		        	    		finalArray[i][j] =  finalArray[i][j] / finalArray[i][columns];
		        	    	}
		        	    }
		        	}


				    // print required results
				    for (int i = 0; i < finalArray.length; i++) 
				    {
		        	    for (int j = 0; j < finalArray[i].length-4; j++) 
		        	    {
							int c=0;
							c = Math.round(finalArray[i][columns-1]);
		        	    	System.out.println("Class " + c +  ", dimension " + (j+1) + ", mean = " + finalArray[i][j] + ", variance = " + ZArray[i][j]);
		        	    }
				    }
				    
	}
}