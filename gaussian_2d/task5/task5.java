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
 * Satyajit Deshmukhh
 * UTA ID: 1001417727
 *
 */
public class task5 {

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

    //   String s = "C:/Users/liquidf4ntasy/Desktop/satellite_training.txt";

	            Scanner console = new Scanner(System.in);           

	            System.out.println("File to be read: ");
	           String inputFile = console.next();
	     //       String inputFile = s;
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
	            
	   //         System.out.println("Number of rows: " + lines);
	            int rows = lines;
	            int columns = words/lines;
	   //         System.out.println("Number of columns: " + columns);
	          
		

				float[][] myArray = new float[rows][columns];
				
			    Set<Integer> set = new HashSet<Integer>();
			    

		        
				int x=0, y=0;
	        //	System.out.println("making array of size [rows][columns]: [" + rows + "]"+ "[" + (columns+3) + "]");

				BufferedReader pin = new BufferedReader(new FileReader(file));	//reading files in specified directory
					String line;
					int count=0;
					while ((line = pin.readLine()) != null)	//file reading
					{   
						count++;
						String[] values = line.replaceAll("^[\\s]+|[\\s]+$", "").split("[\\s]+"); 
						int k=values.length;
			        	//System.out.println("columns " + values.length );
			        	y=0;
						for (int i=0;i <k;i++)
						{
		        		float newvalue = Float.parseFloat(values[i]);
						myArray[x][y] = newvalue;
						if(i==k-1)
						{	set.add(Math.round(newvalue));
						}
						//System.out.print(myArray[x][y] + " ");
						y=y+1;
						}
					//	System.out.println("");
						x=x+1;
					}
		//			System.out.println("Total records added in matrix: " + count);
		//			System.out.println("No of classes: " + set.size());
		//			System.out.println("1st : " + myArray[0][0]);
		//			System.out.println("set: " + myArray.length);
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
        	    		// cov

				        
		        	    	for (int j = 0; j < myArray[i].length-1; j++) 
		        	    	{
		        	    		// Variance step 3 -> square numbers and add up. and store in same column
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
			        	       System.out.print("\n");
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
		        	   // 	System.out.print( finalArray[i][j] + ", ");
		        	    }
		        	}

				    //print test array
		    /*
	        	       System.out.print("\n \n Variance step 3  Array Below:");
				    for (int i = 0; i < VArray.length; i++) 
				    {
			        	       System.out.print("\n");
		        	    for (int j = 0; j < VArray[i].length; j++) 
		        	    {
		        	    	System.out.print( VArray[i][j] + ", ");
		        	    }
		        	    
				    }
		        	
	        	       System.out.print("\n \n FINAL VARIANCE Z Array Step 2 Below:");
				    for (int i = 0; i < ZArray.length; i++) 
				    {
			        	       System.out.print("\n");
		        	    for (int j = 0; j < ZArray[i].length; j++) 
		        	    {
		        	    	System.out.print( ZArray[i][j] + ", ");
		        	    }
		        	    
				    }
		   */

				    
				    
				  for(int m=0;m<ClassLabels.length;m++)  
				  {	    
				    double[] Xs = new double[(int) (finalArray[m][columns])];
			        double[] Ys = new double[(int) (finalArray[m][columns])];
			//       System.out.println("length= " + Xs.length);
			       
			       int r=0;
			        for(int p=0;p<myArray.length;p++)
			        {
			        	
			        		if(myArray[p][columns-1] == Float.valueOf((Integer) ClassLabels[m]))
			        			{ Xs[r] = myArray[p][0];
			        			  Ys[r] = myArray[p][1];
			        			 r++;
			        			}
			        	
			        } 

			        double Xmean = 0d;
			        double Ymean = 0d;

			        double x00 = 0d;
			        double x01 = 0d;
			        double x10 = 0d;
			        double x11 = 0d;
 
			        Xmean = finalArray[m][0];
			        Ymean = finalArray[m][1];
			    //    System.out.println("means ofX x & y: "+ Xmean + "," + Ymean + " --> length: " + Xs.length);
			        
			        for (int i = 0; i < Xs.length; i++) {
			            x00 = x00 + Math.pow(Xs[i] - Xmean, 2);
			            x01 = x01 + (Xs[i] - Xmean)*(Ys[i] - Ymean);
			            x10 = x10 + (Ys[i] - Ymean)*(Xs[i] - Xmean);
			            x11 = x11 + Math.pow(Ys[i] - Ymean, 2);
			        }
			        x00 = x00/Xs.length;
			        x01 = x01/Xs.length;
			        x10 = x10/Xs.length;
			        x11 = x11/Xs.length;
			        
			        finalArray[m][(columns+2)] = (float) x01;

			        BigDecimal roundfinalPrice3 = new BigDecimal(finalArray[m][(columns+2)]).setScale(2,BigDecimal.ROUND_HALF_UP);
        		    finalArray[m][(columns+2)] = roundfinalPrice3.floatValue();
			     //   System.out.printf ("%f %f%n%f %f", x00, x01, x10, x11);
			        
			     //   System.out.println("stored here: " + finalArray[m][(columns+2)] );
				  }
				    
				  
				  
				    // print required results
				    for (int i = 0; i < finalArray.length; i++) 
				    {
			      //  	       System.out.print("\n");
		        	    for (int j = 0; j < 1; j++) 
		        	    {
							int c=0;
							c = Math.round(finalArray[i][columns-1]);
		        	    	System.out.println("Class " + c +  ", mean = [" + finalArray[i][j] +", " + finalArray[i][(j+1)] + "], sigma = [" + ZArray[i][j] + ", " + finalArray[i][(columns+2)] +", " + finalArray[i][(columns+2)]+ ", " + ZArray[i][j+1] + "]");

		        	   // 	System.out.print( VArray[i][j] + ", ");
		        	    	
		        	    	
		        	    }
		        	    
				    }
				    
	}
}