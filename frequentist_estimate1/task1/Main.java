/** 
 * frequentist_estimate1 application generates a random string and estimates the probability distribution of the generated data.
 * Just displays a single line p (c "a") = %4f
 */
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;

public class Main {
	public static void main(String[] args) {
		String str = "ab";
		ArrayList<Character> chars = new ArrayList<Character>();

		 int count=0;
		 float prob=0;
		 int max=100;
		 int min=1;
		 int diff=max-min;
		 Random rn = new Random();
		 int i=0;
		 int j=0;
		 // Generate random string of length 3100
		    for (j=0;j<3100;j++)
		    {   
				i = rn.nextInt(diff+1);
		    	i+=min;
				
                if(i>10)
            		chars.add(str.charAt(1));
				
                else
                	{ chars.add(str.charAt(0));
                	  count= count+1;
                	}
		    }
			
		    prob = (float)count/j;
			
			// round off the probability to 4 decimal places and print solution
		    BigDecimal roundfinalPrice = new BigDecimal(prob).setScale(4,BigDecimal.ROUND_HALF_UP);
		    System.out.println("p(c = 'a') = " + roundfinalPrice); 
											}
					}