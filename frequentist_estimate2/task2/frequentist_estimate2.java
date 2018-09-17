/** 
 * frequentist_estimate2 application generates a random string and estimates the probability distribution of the generated data.
 * Displays the range of the probabilities of 10000 simulations
 */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;

public class frequentist_estimate2 {

	public static void main(String[] args) {

		String str = "ab";
		ArrayList<Character> chars = new ArrayList<Character>();
		
		ArrayList <Float> palist = new ArrayList<Float>();

		 float prob=0;
		 int max=100;
		 int min=1;
		 int diff=max-min;
		 Random rn = new Random();
		 int i=0;
		 int j=0;
  for (int k=0;k<10000;k++)
  {
	  int count=0;
		    for (j=0;j<3100;j++)
		    {   i = rn.nextInt(diff+1);
		    	i+=min;
                if(i>10)
            		chars.add(str.charAt(1));
                else
                	{ chars.add(str.charAt(0));
                	  count= count+1;
                	}
		    }
		    prob = (float)count/j;
		 // round off the probability to 4 decimal places and store value in arraylist
			BigDecimal roundfinalPrice = new BigDecimal(prob).setScale(4,BigDecimal.ROUND_HALF_UP);
		    palist.add(roundfinalPrice.floatValue());
  }
  int q=0,w=0,e=0,r=0,t=0;
  for (int f=0;f<palist.size();f++)
  {	  
	  if (palist.get(f)> 0.12f)
		  q++;
		else if (palist.get(f)> 0.11f)
			w++;
		else if (palist.get(f)< 0.08f)
			e++;
		else if (palist.get(f)< 0.09f)
			r++;
		else    
		    t++;
  
  }
  
	System.out.println("In "+e+" of the simulations p(c = 'a') < 0.08.");
	System.out.println("In "+r+" of the simulations p(c = 'a') < 0.09.");
	System.out.println("In "+t+" of the simulations p(c = 'a') is in the interval [0.09, 0.11].");
	System.out.println("In "+w+" of the simulations p(c = 'a') > 0.11.");
	System.out.println("In "+q+" of the simulations p(c = 'a') > 0.12.");
}

}