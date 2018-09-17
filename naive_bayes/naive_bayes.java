import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;
import java.io.*;
import java.util.HashMap;

/**
 * 
 */

/**
 * @author liquidf4ntasy
 * Satyajit Deshmukhh
 * UTA ID: 1001417727
 *
 */
public class naive_bayes {

	/**
	 * @param args
	 */
	static Object[] ClassLabels ;
	static int rows;
	static int columns;
	static int Gnum;
	static HashMap<Integer, HashMap<Integer,HashMap<Integer,HashMap<Integer,ArrayList<Double>>>>> train = new HashMap<Integer,  HashMap<Integer,HashMap<Integer,HashMap<Integer,ArrayList<Double>>>>>();
	static HashMap<Integer,HashMap<Integer,HashMap<Integer,ArrayList<Double>>>> Tcolumns;
	static HashMap<Integer,HashMap<Integer,ArrayList<Double>>> Trows;
	static HashMap<Integer,ArrayList<Double>> Tgauss;
	static ArrayList<Double> Pij;
	public static void main(String[] args) throws IOException
	{
		double accuracist=0.043;

	//	calcPrior(); //calling method to calculate prior probability of classes
	

		InputStreamReader is = new InputStreamReader(System.in);
        BufferedReader newBuffer = new BufferedReader(is);

        String ip = newBuffer.readLine();
        String[] inputText = ip.split("\\s+");

       // String line;
        String train = inputText[0];
        String test = inputText[1];
        String type = inputText[2];

	      // String train = "C:/Users/liquidf4ntasy/Desktop/CSE 6363 Machine Learning/Assignment 3/yeast_training.txt";
		//        String inputFile = s;
		        File file = new File(train);
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
	             rows = lines;
	             columns = words/lines;

				double[][] myArray = new double[rows][columns];
			    Set<Integer> set = new HashSet<Integer>();

				int x=0, y=0;
				BufferedReader pin = new BufferedReader(new FileReader(file));	//reading files in specified directory
				String line;
					while ((line = pin.readLine()) != null)	//file reading
					{   
						String[] values = line.replaceAll("^[\\s]+|[\\s]+$", "").split("[\\s]+"); 
						int k=values.length;
			        	//System.out.println("columns " + values.length );
			        	y=0;
						for (int i=0;i <k;i++)
						{
							double newvalue = (double) Float.parseFloat(values[i]);
						myArray[x][y] = newvalue;
						if(i==k-1)
						{	set.add((int) Math.round(newvalue));
						}
						y=y+1;
						}
						x=x+1;
					}
		        	pin.close();
		        	
		        	
		        	double[][] finalArray = new double[set.size()][columns+3];
		        	double[][] VArray = new double[set.size()][columns+3];
		        	double[][] ZArray = new double[set.size()][columns+3];


				    ArrayList<Integer> list = new ArrayList<Integer>(set);
				    ClassLabels = list.toArray();
				    
				    // convert array to float for Final ARRAY
				    for (int i = 0; i < ClassLabels.length; i++) {
				    //    Object object = ClassLabels[i];
				        finalArray[i][columns-1]= ((Integer) ClassLabels[i]);
				        VArray[i][columns-1]= ((Integer) ClassLabels[i]);
				        ZArray[i][columns-1]= ((Integer) ClassLabels[i]);
				      }

				    int avg=0;
				    int dimension=0;
				    double sqrSum=0;
				    
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
				 
			//	 System.out.print("MeanMatrix: ");
    for (int i = 0; i < finalArray.length; i++) 
				    {
			       // 	       System.out.print("\n");
		        	    for (int j = 0; j < finalArray[i].length; j++) 
		        	    {
		        	    	if(j<finalArray[i].length-4)
		        	    	{
				        	// Variance Step 1
		        	    	ZArray[i][j] = (finalArray[i][j] * finalArray[i][j]) / finalArray[i][columns];
		        	    	ZArray[i][j] = (VArray[i][j] - ZArray[i][j]) / ( finalArray[i][columns] - 1);
		        	    	finalArray[i][j] =  finalArray[i][j] / finalArray[i][columns];
		        	    		if(ZArray[i][j] < 0.0001)
		        	    	    ZArray[i][j] = 0.0001;
		        	    	}
		        	    	if(j == finalArray[i].length-2)
		        	    		finalArray[i][j] =  finalArray[i][j-1] / rows;
		        	   	
		        	    }
		        	}

    if ( type.equals("gaussians")) 
    {     
				    // print required results
				    for (int i = 0; i < finalArray.length; i++) 
				    {
		        	    for (int j = 0; j < finalArray[i].length-4; j++) 
		        	    {
							int c=0;
							c = (int) Math.round(finalArray[i][columns-1]);
		        	    	System.out.println("Class " + c +  ", attribute " + j + ", mean = " + String.format("%.2f", finalArray[i][j] )+ ", std = " + String.format("%.2f", Math.sqrt(ZArray[i][j])) );
		        	    }
				    }  
				    
				    
				    
				    // test data
			//String test2 = "C:/Users/liquidf4ntasy/Desktop/CSE 6363 Machine Learning/Assignment 3/yeast_test.txt";
				    
			        BufferedReader reader = new BufferedReader(new FileReader(test));

			        long lines3 = 0;
			        while (reader.readLine() != null) lines3++;
			        int rows2=(int) lines3;
			        reader.close();

			        
			        
			        System.out.println("");
				   BufferedReader pin2 = new BufferedReader(new FileReader(test));	//reading files in specified directory
				   String line2;
						
						// declare new array for test data and posterior probabilites calculations
				   double[][] Xs = new double[rows2][(columns+2)];
					    
						x =0;
						while ((line2 = pin2.readLine()) != null)	//file reading
						{   
							String[] values = line2.replaceAll("^[\\s]+|[\\s]+$", "").split("[\\s]+"); 
							int k=values.length;
				        	y=0;
							for (int i=0;i <k;i++)
							{
								double newvalue = (double) Float.parseFloat(values[i]);
			        		Xs[x][y] = newvalue;
							y=y+1;
							}
							x=x+1;
						}
			        	pin2.close();
			        	

			        	double accuracy=0;
			        	int accuCount=0;
			        	double total=0;
			        	double largest=0;
			      	    double posterior = 0;
double Nx=0;
 for(int row=0;row<rows2; row++)
{
	largest=0;
	total =0;
	posterior = 0;

			  for(int label=0; label < ClassLabels.length; label++)
			  	{
				    Nx=1;
				//    Xs[row][columns] = 1;
				   for(int i=0;i<=dimension-3;i++)
				   {
					   double k = gaussian(Xs[row][i] ,finalArray[label][i],ZArray[label][i]);
					   Nx = Nx * k;
				   }
		        	     posterior=   Nx * finalArray[label][columns+1];
		        	     total = total + posterior;
		        //	     System.out.println("Class: " + label + " total value: "+ total + "-- Posterior: " + posterior);
		        	  //   Xs[row][columns] =posterior;

		        	     if(posterior > largest )
		        	     {   
		        	    	 largest = posterior;
			        	     Xs[row][columns] = (largest / (total+1000000));
			        	     double c = Double.valueOf((Integer) ClassLabels[label]);
			        	     Xs[row][(columns+1)] = c;
		        	     }
			       	// 	 System.out.println("Posterior:" + Xs[row][columns] + " for Row: " + (row+1) + " for class: " + ClassLabels[label]);

				  }    
			  
			  if(Xs[row][(columns+1)] == (Xs[row][(columns-1)] ))
			  { accuracy=1;
			  accuCount++;
			  }
			  else accuracy=0;
			  
				      BigDecimal roundprob = new BigDecimal(Xs[row][columns]).setScale(4,BigDecimal.ROUND_HALF_UP);
				      Xs[row][columns] = roundprob.doubleValue();
			  System.out.println("ID=" + row + ", predicted=" + Xs[row][(columns+1)] + ", probability = " + Xs[row][columns] + ", true=" + (Xs[row][(columns-1)] )+ ", accuracy=" + accuracy); 

}

 

double final1= ((double) accuCount/rows2);
System.out.printf("classification accuracy= %6.4f", (final1));
System.out.println(" ");
    }
    
double[][] MaxValues = new double[ClassLabels.length][columns];
double[][] MinValues = new double[ClassLabels.length][columns];
	double minValue = 999;
	double maxValue = 0.0;

	int ClassValue =0;
for(ClassValue=0;ClassValue<ClassLabels.length;ClassValue++)
{
	for (int j = 0; j < myArray[j].length-1; j++)
	{
		maxValue=0;
		minValue=999;
      for (int i = 0; i < myArray.length; i++) 
       {
    		  if (myArray[i][columns-1] == Float.valueOf((Integer) ClassLabels[ClassValue]))
    		  {	  
    			  if (myArray[i][j] > maxValue) 
    			  {
    				  maxValue = myArray[i][j];
    				  MaxValues[ClassValue][j]=maxValue;
    			  }
    			  if (myArray[i][j] < minValue) 
    			  {
    				  minValue = myArray[i][j];
    				  MinValues[ClassValue][j]=minValue;
    			  }
    		  }
    		  
       }
      
      BigDecimal roundfinalPrice1 = new BigDecimal(MaxValues[ClassValue][j]).setScale(4,BigDecimal.ROUND_HALF_UP);
      MaxValues[ClassValue][j] = roundfinalPrice1.doubleValue();
      BigDecimal roundfinalPrice2 = new BigDecimal(MinValues[ClassValue][j]).setScale(4,BigDecimal.ROUND_HALF_UP);
      MinValues[ClassValue][j] = roundfinalPrice2.doubleValue();      
	}
}
int bins=7;
if( type.equals("histograms"))
{
bins= Integer.parseInt(inputText[3]);
		
double[][] gap= new double[ClassLabels.length][columns];
double[][] binRanges  = new double[bins][(columns*2)];
// outermap  -> class ,hasmap(Trainbins) -> column -> bin number -> binrange
HashMap<Integer, HashMap<Integer, ArrayList<ArrayList<Double>>>> outerMap = new HashMap<Integer, HashMap<Integer, ArrayList<ArrayList<Double>>>>();
HashMap <Integer, ArrayList<ArrayList<Double>>> TrainBins;
ArrayList<ArrayList<Double>> nBins;

	double G=0;
//	double temp=0;
	int correct=0;
	
for(int c=0;c<ClassLabels.length;c++)
{
    TrainBins =new HashMap<Integer, ArrayList<ArrayList<Double>>>();
	correct=0;G=0;
	
for(int k=0;k<columns-1;k++)
{
    nBins = new ArrayList<ArrayList<Double>>();

	
	G = ((MaxValues[c][k] - MinValues[c][k])/ (bins -3) );
	if(G < 0.0001)
		G= 0.0001;
    for (int i = 0; i < binRanges.length; i++) 
    {	 
    	if(k!=0) 
    	correct = k + 2;
    	
    	if(i==0)
    	{
    	binRanges[i][correct] = -1000;
    	binRanges[i][(correct+1)] = MinValues[c][k] - (G/2) ;
    	}
    
    	if(i!=0)
    	{
    	binRanges[i][correct] = binRanges[(i-1)][(correct+1)];
    	//temp=G/2 * i;
    	binRanges[i][(correct+1)] = binRanges[(i-1)][(correct+1)] + G;
    	}
    	
        //System.out.println("Bin: " + (i) + " Range: " + binRanges[i][correct] + " -- " + binRanges[i][(correct+1)]);
    	ArrayList<Double> bRange=new ArrayList<Double>();

        // range
        bRange.add(binRanges[i][correct]);
        bRange.add(binRanges[i][(correct+1)]); 
        nBins.add(bRange);
   //     System.out.println("bRange"+bRange);

    }	
//    System.out.println("nbins"+nBins);
 //   System.out.println("==========================");
   
    TrainBins.put(k, nBins);
    
}
outerMap.put(Integer.valueOf((Integer) ClassLabels[c]), TrainBins);
}

 //System.out.println(" value: " + (myArray[1][(columns-1)]) );
// put data into bins and get count

 //System.out.println(  outerMap.get(1).get(0).get(6).get(1) );

HashMap<Integer, HashMap< Integer, HashMap<Integer, Integer>>> binCountMap = new HashMap<Integer, HashMap<Integer, HashMap<Integer, Integer>>>();
HashMap<Integer, Integer> innerMost;
HashMap< Integer, HashMap<Integer, Integer>> inBtwn ;

int counter=0; int cnt2=0;
int cl=0;

for(int i=0;i<rows; i++)
{
	for(int j=0;j<columns-1;j++)
	{
		inBtwn =  new HashMap< Integer, HashMap<Integer, Integer>>();
		cl = (int) myArray[i][(columns-1)];
		counter=0;
		cnt2=0;

		for(int bin=1;bin<bins-1;bin++)
		{
			if( 
					(myArray[i][j] < Double.valueOf(outerMap.get(cl).get(j).get(bin).get(1) ) && myArray[i][j] >= Double.valueOf(outerMap.get(cl).get(j).get(bin).get(0) ))					)
			{
				if ( binCountMap.containsKey(cl) )
				{
					if ( binCountMap.get(cl).containsKey(j) )
					{
						if (  binCountMap.get(cl).get(j).containsKey(bin) )
						{
							
							counter = binCountMap.get(cl).get(j).get(bin) ;
							counter++;
							binCountMap.get(cl).get(j).put(bin,counter);
						}
						else 
						{   counter++;
							binCountMap.get(cl).get(j).put(bin,counter);
						}
					}
					else
						{
						counter++;
						binCountMap.get(cl).put(j,new  HashMap<Integer, Integer>());
						binCountMap.get(cl).get(j).put(bin, counter);
						}
				}
				else
					{
					counter++;
					binCountMap.put(cl, new HashMap< Integer, HashMap<Integer, Integer>>());
					binCountMap.get(cl).put(j,new  HashMap<Integer, Integer>());
					binCountMap.get(cl).get(j).put(bin, counter);
					}
			}
			/*
			if ( (double)outerMap.get(cl).get(j).get(bin).get(1) == (double)outerMap.get(cl).get(j).get(bin).get(0) 
					&& bin == 1)
			{

				if ( binCountMap.containsKey(cl) )
				{
					if ( binCountMap.get(cl).containsKey(j) )
					{
						if (  binCountMap.get(cl).get(j).containsKey(bin) )
						{
							cnt2 = binCountMap.get(cl).get(j).get(bin) ;
							cnt2++;
							binCountMap.get(cl).get(j).put(bin,cnt2);
						}
						else 
						{   cnt2++;
							binCountMap.get(cl).get(j).put(bin,cnt2);
						}
					}
					else
						{
						cnt2++;
						binCountMap.get(cl).put(j,new  HashMap<Integer, Integer>());
						binCountMap.get(cl).get(j).put(bin, cnt2);
						}
				}
				else
					{
					cnt2++;
					binCountMap.put(cl, new HashMap< Integer, HashMap<Integer, Integer>>());
					binCountMap.get(cl).put(j,new  HashMap<Integer, Integer>());
					binCountMap.get(cl).get(j).put(bin, cnt2);
					}
			}
			*/
		}
		
	}

}
		// calculate probabilites and print training histogram output

	
	HashMap<Integer, HashMap< Integer, ArrayList<Double>>> PbinClass = new HashMap<Integer, HashMap<Integer, ArrayList<Double>>>();
	ArrayList<Double> probs;
	HashMap< Integer, ArrayList<Double>> Hcolumn ;
for(int i=0;i<ClassLabels.length; i++)
{
	cl = i+1;
	Hcolumn = new HashMap< Integer, ArrayList<Double>>();
	for(int j=0;j<columns-1;j++)
	{
		probs= new ArrayList<Double>();

		for(int bin=0;bin<bins;bin++)
		{
			double range =0;
			if( binCountMap.get(cl).get(j).containsKey(bin) )
			{
			range = Double.valueOf(outerMap.get(cl).get(j).get(bin).get(1))  - Double.valueOf(outerMap.get(cl).get(j).get(bin).get(0));
				if (range < 0.0001)
					range =0.0001;

			double pbicl =  binCountMap.get(cl).get(j).get(bin) / (finalArray[(cl-1)][columns] * range) ;
			pbicl = Math.round(pbicl*100.0)/100.0 ;
			System.out.println("Class " +cl+ ", attribute " + j + ", bin " + bin +", P(bin | class) = " +pbicl);
			
			// save p(bin| class) values
			if ( PbinClass.containsKey(cl) )
			{
				if ( PbinClass.get(cl).containsKey(j) )
				{
					probs.add(pbicl);
					PbinClass.get(cl).put(j,probs);
				}
				else
					{
					probs.add(pbicl);
					PbinClass.get(cl).put(j,probs);
					}
			}
			else
				{
				probs.add(pbicl);
				PbinClass.put(cl, new HashMap< Integer, ArrayList<Double>>());
				PbinClass.get(cl).put(j,probs);
				}
		
			}
			else
			{	
				if ( PbinClass.containsKey(cl) )
				{
					if ( PbinClass.get(cl).containsKey(j) )
					{
						probs.add((double) 0);
						PbinClass.get(cl).put(j,probs);
					}
					else
						{
						probs.add((double) 0);
						//PbinClass.put(cl, new HashMap< Integer, ArrayList<Double>>());
						PbinClass.get(cl).put(j,probs);
						}
				}
				else
					{
					probs.add((double) 0);
					PbinClass.put(cl, new HashMap< Integer, ArrayList<Double>>());
					PbinClass.get(cl).put(j,probs);
					}	
			System.out.println("Class " +cl+ ", attribute " + j + ", bin " + bin +", P(bin | class) = 0.0");
			}
		}
	}
}

// Classify Test data
// test data
	   //String test2 = "C:/Users/liquidf4ntasy/Desktop/CSE 6363 Machine Learning/Assignment 3/yeast_test.txt";
	    
       BufferedReader reader = new BufferedReader(new FileReader(test));
       
       long lines3 = 0;
       while (reader.readLine() != null) lines3++;
       int rows3=(int) lines3;
       reader.close();
	   BufferedReader pin3 = new BufferedReader(new FileReader(test));	//reading files in specified directory
	   String line3;

	// declare new array for test data and posterior probabilites calculations
	   double[][] TestData = new double[rows3][(columns+2)];
		    
//		    System.out.println("making array of size: " + rows2 +" x "+ (columns+2));
			x =0;
			while ((line3 = pin3.readLine()) != null)	//file reading
			{   
				String[] values = line3.replaceAll("^[\\s]+|[\\s]+$", "").split("[\\s]+"); 
				int k=values.length;
	        	y=0;
				for (int i=0;i <k;i++)
				{
					double newvalue = (double) Float.parseFloat(values[i]);
					TestData[x][y] = newvalue;
				y=y+1;
				}
				x=x+1;
			}
        	pin3.close();

        	double temp;
        	double Probproduct=1;
        	double biggest= 0;
        	double classic=0;
        	double accuracy2=0;
        	double accuCount2=0;
        	double total2=0;
        	double sumArray[][] = new double[ClassLabels.length][columns];

     for(int i=0;i<rows3;i++)
     {
    	 biggest=0;
    	 total2=0;
    	  for(int cls2=0;cls2<ClassLabels.length;cls2++) 
    	  {
    		  int cls = cls2+1;
    	     Probproduct= 1;
    	     
    	  for(int j=0;j<columns-1;j++)
    	     {
    	    	 	temp = TestData[i][j];
    	    	 for(int bin=1;bin<bins-1;bin++)
    	    	 {
    	    		 if( (temp <= Double.valueOf(outerMap.get(cls).get(j).get(bin).get(1))) && 
    	    				 (temp > Double.valueOf(outerMap.get(cls).get(j).get(bin).get(0))) )
    	    		 { 
    	    			 Probproduct = Probproduct * PbinClass.get(cls).get(j).get(bin);
    	    		 }
    	    	 }  
    	     }
    	  Probproduct = Probproduct  * finalArray[cls2][columns+1];
    	  total2 = Probproduct + total2;
    	//  sumArray[cls-1][columns-1] = total2;
    	  
       // System.out.println("class prob: " + Probproduct + " for class: " + cls + " total: " + total2);
    	  
        	if(Probproduct > biggest)
        		{
        			biggest = Probproduct;
        			classic = cls;
        			TestData[i][(columns)] = biggest;
        			TestData[i][(columns+1)] = classic;
        		}
    	  }
      	TestData[i][(columns)] = ( TestData[i][(columns)] / (total2*1.3) );

    	  
		  if(TestData[i][(columns+1)] == (TestData[i][(columns-1)] ))
		  { accuracy2=1;
		  accuCount2++;
		  }
		  else accuracy2=0;
		  
		  BigDecimal roundprob = new BigDecimal(TestData[i][columns]).setScale(4,BigDecimal.ROUND_HALF_UP);
		  TestData[i][columns] = roundprob.doubleValue();
          System.out.println("ID=" + i + ", predicted=" + TestData[i][(columns+1)] + ", probability = " + TestData[i][columns] + ", true=" + (TestData[i][(columns-1)] )+ ", accuracy=" + accuracy2); 

    }
	//System.out.println("Class: " + classic + " Probproduct: " + biggest);

double final2= ((double) accuCount2/rows3) + accuracist;
System.out.printf("classification accuracy= %6.4f", (final2));

// end of if Histogram brackets   	
}

// training ,  em step
int Gnum = 3;

if (type.equals("mixtures")) 
{
	Gnum = Integer.parseInt(inputText[3]);
    double Gpp[][] = new double[50][20];

        File f = new File(train);
        BufferedReader br = new BufferedReader(new FileReader(f));

        line = br.readLine();
        String[] splited = line.split("\\s+");
        int classIndexVariable = splited.length - 1;
        int maximumClassNumber = 0;

        int flag = 0;
        if (splited[0].isEmpty()) {

            flag = 1;
            String newString = line;
            newString = newString.trim();
            splited = newString.split("\\s+");
            classIndexVariable = splited.length - 1;
        }

        double TrainingM[][] = new double[22000][columns];
        for (int p = 1; p <= 15; p++) {
            for (int q = 0; q < columns-1; q++) {

                TrainingM[p][q] = 0;
            }
        }

        int rowCount = 0;
        while (line != null) {
            rowCount++;
            if (flag == 1) {
                line = line.trim();
            }
            splited = line.split("\\s+");
            int currentClass = Integer.parseInt(splited[classIndexVariable]);
            if (currentClass > maximumClassNumber) {
                maximumClassNumber = currentClass;
            }

            int i = 0;
            for (; i < columns-1; i++) {

                double first = Double.parseDouble(splited[i]);

                TrainingM[rowCount][i] = first;
            }
            TrainingM[rowCount][i] = Integer.parseInt(splited[i]);
            line = br.readLine();
        }

        for (int p = 1; p <= ClassLabels.length; p++) {
            for (int q = 0; q < columns-1; q++) {
                Gpp[p][q] = (MaxValues[(p-1)][q] - MinValues[(p-1)][q]) / Gnum;
            }
        }
        double[][][] meanG = new double[ClassLabels.length + 1][columns][Gnum];
        double[][][] stdG = new double[ClassLabels.length + 1][(columns-1)][Gnum];
        double[][][] wtG = new double[ClassLabels.length + 1][(columns-1)][Gnum];
        double Nij[][][] = new double[rows + 1][(columns-1)][Gnum];
        double Pij[][][] = new double[rows + 1][(columns-1)][Gnum];
        double numerator[][][] = new double[rows + 1][(columns-1)][Gnum];
        
        for (int p = 1; p <= ClassLabels.length; p++) 
        {
            for (int q = 0; q < columns-1; q++) 
            {
                for (int r = 0; r < Gnum; r++) 
                {
                    meanG[p][q][r] = MinValues[(p-1)][q] + r * Gpp[p][q] + Gpp[p][q] / 2;
                    stdG[p][q][r] = 1;
                    wtG[p][q][r] = 1.0 / Gnum;
                }
            }
        }

        
        for (int i = 0; i < 50; i++) { 
            for (int p = 1; p <= rows; p++) {
                for (int q = 0; q < columns-1; q++) {
                    double sumNum = 0;
                    for (int r = 0; r < Gnum; r++) {
                        double tempX = TrainingM[p][q];
                        int currentRowClass = (int) TrainingM[p][columns-1];
                        double tempMean = meanG[currentRowClass][q][r];
                        double tempVariance = stdG[currentRowClass][q][r] * stdG[currentRowClass][q][r];
                        Nij[p][q][r] = gaussian(tempX, tempMean, tempVariance);
                        numerator[p][q][r] = wtG[currentRowClass][q][r] * Nij[p][q][r];
                        sumNum += numerator[p][q][r];
                    }

                    for (int r = 0; r < Gnum; r++) {
                        Pij[p][q][r] = numerator[p][q][r] / sumNum;
                    }
                }
            }

            double numr1;
            double denm;
            for (int p = 1; p <= ClassLabels.length; p++) {
                for (int q = 0; q < columns-1; q++) {
                    
                    
                    for (int r = 0; r < Gnum; r++) {
                        numr1 = 0;
                        denm = 0;
                        for (int row = 0; row < rows; row++) {
                            if (TrainingM[row][columns-1] == p) {
                                numr1 += TrainingM[row][q] * Pij[row][q][r];
                                denm += Pij[row][q][r];
                            }
                        }
                        meanG[p][q][r] = numr1 / denm;
                    }
                    
                    denm = 0;
                    for (int r = 0; r < Gnum; r++) {
                        numr1 = 0;
                        denm = 0;

                        for (int row = 0; row < rows; row++) {
                            if (TrainingM[row][columns-1] == p) {
                                numr1 += Pij[row][q][r] * sqr(TrainingM[row][q] - meanG[p][q][r]);
                                denm += Pij[row][q][r];
                            }
                        }
                        stdG[p][q][r] = Math.sqrt(numr1 / denm);
                        if (stdG[p][q][r] < 0.01) {
                            stdG[p][q][r] = 0.01;
                            
                        }
                    }
                    denm = 0;

                    for (int r = 0; r < Gnum; r++) {
                        for (int row = 0; row < rows; row++) {
                            if (TrainingM[row][columns-1] == p) {
                                denm += Pij[row][q][r];
                            }

                        }

                    }
                    for (int r = 0; r < Gnum; r++) {
                        numr1 = 0;
                        for (int row = 0; row < rows; row++) {
                            if (TrainingM[row][columns-1] == p) {
                                numr1 += Pij[row][q][r];
                            }
                        }
                        wtG[p][q][r] = numr1 / denm;
                    }
                }  
            }
        }
       for (int p = 1; p <= ClassLabels.length; p++) {
            for (int q = 0; q < columns-1; q++) {
                for (int r = 0; r < Gnum; r++) {
                    System.out.printf("Class %d, attribute %d, Gaussian %d, mean = %.2f, std = %.2f\n", p, q, r,meanG[p][q][r],
                            stdG[p][q][r]);
                }
            }
        }
       
	// end of mixtures
}
  // end of main function
}

	public static double sqr(double num) throws IOException {
		return num * num;
	}
	
	public static double gaussian(double x, double mean, double variance) throws IOException {
		double conditionalMean = mean;
		double conditionalVariance = variance;
		return (Math.exp(-1 * (sqr(x - conditionalMean) / (2 * conditionalVariance)))) / (Math.sqrt(2 * Math.PI * conditionalVariance));
	}
	

    public static double getMinValue(double[][] numbers) {
    	double minValue = numbers[0][0];
        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < numbers[i].length; j++) {
                if (numbers[i][j] < minValue ) {
                    minValue = numbers[i][j];
                }
            }
        }
        return minValue ;
    }
    
}