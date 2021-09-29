/*
	UOW ID - 6573277
	Name - Hay Munn Hnin Wai  
	A2 - Task2 (Fast Exponentiation Using S SX Method)  
*/

import java.util.ArrayList;
import java.util.Scanner;

// calculate Fast Exponentiation Using Square-Multiply Method 
public class fast{
	
        public static Long Fast_exp(Long base, Long exponent, Long modulus)
        {
            Long result;
            result = base;
            int bin[] = new int[32]; 
            int index = 0;
            String s = Long.toBinaryString(exponent);     //Convert exponent into Binary 
            System.out.println("Covert " + exponent + " into Binary : " +s );     
            
            ArrayList<String> aList = new ArrayList<>();   //Create ArrayList for SSX 
            for ( int a = 0; a < s.length(); a++ )          //Loop thorugh Exponent String s 
             {    
                if (s.charAt(a)== '1')
                {
                    aList.add("SX");
                }
                else if ( s.charAt(a) =='0')
                {
                   aList.add("S");
                }  
            }
            System.out.println("SSX Replacement: " + aList);

            //Drop 1st SX 
            aList.remove(0);
            System.out.println("Removed 1st SX: " + aList);
            
            //Compute Square Multiply here 
            System.out.println("\nCompute according to the S SX sequence");
            System.out.println("========================================");
            
            for (String temp : aList)    
            {
                if(temp.equals("SX"))
                {
                    result = (result*result)*base % modulus;   //If SX, Square then multiply
                    System.out.println("SX: "+ result);
                }
                else if (temp.equals("S"))
                {
                    result = (result*result) % modulus;      //If S, then Square
                    System.out.println("S: "+ result);
                }
            }
            for(int j = index-1; j>= 0; j--)
            {    
                System.out.print(bin[j]);    
            }    
            System.out.println();//new line
     
    return result;
}
    public static void main(String[] args) {
	Scanner stdin = new Scanner(System.in);
	Long a, b, p, r;
        System.out.print("Please Enter base a : ");     //Base a
        a = stdin.nextLong();
        
        int b1 = 256; 
        System.out.print("Please Enter exponent b : ");      //Exponent b
        b = stdin.nextLong(); 
        while( b <= b1)
        {
            System.out.println("Exponent b value should be greater than 256! "); 
            System.out.print("Please enter b again: ");
            b = stdin.nextLong();        
        }
        System.out.print("Please Enter Modulus p :");  //Mod p
        p = stdin.nextLong();
           
        r = Fast_exp(a, b, p);
        System.out.println(a + "^" + b + " % " + p + " = " + r.toString());
    } 
}
