/*
 * Name - Hay Munn Hnin Wai
 * UOW ID - 6573277
 * Task-3 ( Encryption ) 
 */
import java.util.Scanner;

public class LDESDescryption{  
    
    public static int TwoBitRotateLeft(int n)
    {
        int result = n;
        switch ( n )
        {
            case 0:
                result = 0; 
                break;
            
            case 1: 
                result = 2; 
                break;
                
            case 2: 
                result = 1;
                break;
            case 3: 
                result = 3; 
                break;          
        }
        return result;
    } 
    public static String ToBinary(int x) 
    {
	return String.format("%4s", Integer.toBinaryString(x)).replaceAll(" ", "0");
    } 
    public static String ToBinary2Bit(int x) 
    {
	return String.format("%2s", Integer.toBinaryString(x)).replaceAll(" ", "0");
    } 
    public static int fourBitRotateLeft ( int n)   //Rotate Left
    {
        int result = n;
        switch(n)
        {
            case 0:
                result = 0; 
                break;
            case 1:
                result =2;
                break;
            case 2: 
                result = 4; 
                break;
             case 3:
                result = 6; 
                break;
            case 4:
                result = 8;
                break;
            case 5: 
                result = 10; 
                break;   
            case 6:
                result = 12; 
                break;
            case 7:
                result = 14;
                break;
            case 8: 
                result = 1; 
                break;
            case 9: 
                result = 3; 
                break;
            case 10:
                result = 5;
                break;
            case 11: 
                result = 7; 
                break;
            case 12: 
                result = 9; 
                break;
            case 13:
                result = 11;
                break;
            case 14: 
                result = 13; 
                break;
            case 15:
                result = 15;
                break;
        }
        return result;   
    }
    //Split the Result here 
    public static String SplitA0(String n)
    {    
        int[] intArray = new int[n.length()]; 
        for(int i=0; i < n.length(); i++)
        {
            intArray[i] = Character.getNumericValue(n.charAt(i));  
        }
        String result1 = String.valueOf(intArray[0]);
        String result2 = String.valueOf(intArray[1]);
        String A0 =  result1 + result2;  
        return A0;    
    }
    public static String SplitB0(String m)
    {
        int[] intArray1 = new int[m.length()]; 
        for(int i=0; i < m.length(); i++)
        {
            intArray1[i] = Character.getNumericValue(m.charAt(i));  
        }
        String result3 = String.valueOf(intArray1[2]);
        String result4 = String.valueOf(intArray1[3]);
        String B0 =  result3 + result4;
        
        return B0;
        
    }
    public static String SplitK0(String n)
    {    
        int[] intArray = new int[n.length()]; 
        for(int i=0; i < n.length(); i++)
        {
            intArray[i] = Character.getNumericValue(n.charAt(i));  
        }
        String K0 = String.valueOf(intArray[0]);   //K's 1st Index 
        return K0;    
    }
     public static String SplitK1(String n)
    {    
        int[] intArray = new int[n.length()]; 
        for(int i=0; i < n.length(); i++)
        {
            intArray[i] = Character.getNumericValue(n.charAt(i));  
        }
        String K1 = String.valueOf(intArray[1]);   //K's 2nd Index 
        return K1;    
    }
    public static String RoundKey1(String k0)
    {
        String RKey1 = k0+k0+k0;
        return RKey1;
    }
    public static String RoundKey2(String k1)
    {
        String RKey2 = k1+k1+k1;
        return RKey2;
    }
    public static String Expand(String exp)      //eg; 01
    { 
        int[] array = new int[exp.length()]; 
        for(int i=0; i < exp.length(); i++)
        {
           array[i] = Character.getNumericValue(exp.charAt(i));  
        }
        String x1 = String.valueOf(array[0]);
        String x2 = String.valueOf(array[1]);
        String x3 = String.valueOf(array[0]);
        String result = x1+x2+x3; 
         
        return result;                          //get 010
    }
    public static String XorFunction(String X, String Y )   
    {
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < X.length(); i++) 
      {
         sb.append(X.charAt(i)^Y.charAt(i));
      }
      return sb.toString();    
    }
    public static String SplitI2I3(String n)         //get I2I3
    {
        int[] intArray = new int[n.length()]; 
        for(int i=0; i < n.length(); i++)
        {
            intArray[i] = Character.getNumericValue(n.charAt(i));  
        }
        String result1 = String.valueOf(intArray[1]);
        String result2 = String.valueOf(intArray[2]);
        String I2I3 =  result1 + result2;  
        return I2I3;
    }
    public static String Linear(String str2)       //Linear 
    {
      String str1 = "11";
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < str1.length(); i++) {
         sb.append(str1.charAt(i)^str2.charAt(i));
      }
     return sb.toString(); 
    }
    public static int fourBitRotateRight ( int n)   //Rotate Right(4-bits)
    {
        int result = n;
        switch(n)
        {
            case 0:
                result = 0; 
                break;
            case 1:
                result = 8;
                break;
            case 2: 
                result = 1; 
                break;
             case 3:
                result = 9; 
                break;
            case 4:
                result = 2;
                break;
            case 5: 
                result = 10; 
                break;   
            case 6:
                result = 3; 
                break;
            case 7:
                result = 11;
                break;
            case 8: 
                result = 4; 
                break;
            case 9: 
                result = 12; 
                break;
            case 10:
                result = 5;
                break;
            case 11: 
                result = 13; 
                break;
            case 12: 
                result = 6; 
                break;
            case 13:
                result = 14;
                break;
            case 14: 
                result = 7; 
                break;
            case 15:
                result = 15;
                break;
        }
        return result;   
    }
    public static void main(String[] args) 
    {
        Scanner sc = new Scanner(System.in);
        for ( int j = 0; j < 4 ; j++ )
        {   
            int[] K = new int[4];     
            K[j] = j;     //K= 00,01,10,11
            System.out.println("K: "+ ToBinary2Bit(K[j]));   //K= 00,01,10,11
        
            String K1 = RoundKey1(SplitK0(ToBinary2Bit(K[j])));  //000
            String K2 = RoundKey2(SplitK1(ToBinary2Bit(K[j])));  //000
            System.out.println("Round Key1 : "+ K1);      
            System.out.println("Round Key2 : "+ K2);
                
            System.out.println(j+1 + ". Decrypt the below Cipher(C) using Key: "+ ToBinary2Bit(K[j]));  //00,01,10,11
            System.out.println("=======================================");
            for ( int i = 0; i < 16; i++  )
            {
                System.out.print("4 bits Cipher Message(C) : "+ ToBinary(i));
                int R_left = Integer.parseInt(ToBinary(i), 2);
                fourBitRotateLeft(R_left);    //Rotate left 
                System.out.println();
                ToBinary(fourBitRotateLeft(R_left));    //Convert Rotated value to binary form

                SplitA0(ToBinary(fourBitRotateLeft(R_left)));  // Split A0(00)
                SplitB0(ToBinary(fourBitRotateLeft(R_left)));  // B0      (01)

                String A0 = SplitA0(ToBinary(fourBitRotateLeft(R_left)));  
                String B0 = SplitB0(ToBinary(fourBitRotateLeft(R_left)));
                
                //Start F Function Here -------------------
                Expand(B0);   //Expand Xi
                XorFunction(Expand(B0),K2);    //get I1,I2,I3      
                SplitI2I3(XorFunction(Expand(B0),K2)); 
                Linear(SplitI2I3(XorFunction(Expand(B0),K2))); //Linear
 
                //Convert Binary to Int 
                String a = Linear(SplitI2I3(XorFunction(Expand(B0),K2)));
                int J1J2 = Integer.parseInt(a, 2);
                
                //Rotate 2-bits Left & Convert to Binary form 
                String Z = ToBinary2Bit(TwoBitRotateLeft(J1J2));   //Convert Z to Binary form   
                
                //END F Function Here --------------------
                
                //get A1B1
                String A1 = B0;
                String B1 = XorFunction(Z, A0);  //Output Z + A0 
                //System.out.println("A1B1:" + A1+B1);
                System.out.println("");
                //===========================Finish Round 1 ====================
                
                //===============Round-2 ==========================  
                Expand(B1);   //Expand Xi
                XorFunction(Expand(B1),K1);    //get I1,I2,I3      
                SplitI2I3(XorFunction(Expand(B1),K1)); 
                Linear(SplitI2I3(XorFunction(Expand(B1),K1))); //Linear
 
                //Convert Binary to Int 
                String b = Linear(SplitI2I3(XorFunction(Expand(B1),K1)));
                int j1j2 = Integer.parseInt(b, 2);
                
                //Rotate 2-bits Left & Convert to Binary form 
                String Z2 = ToBinary2Bit(TwoBitRotateLeft(j1j2));   
                
                //Convert Z to Binary form 
                //System.out.println("Output Z: "+ Z2 );
                //END F Function Here --------------------
  
                //get A2B2
                String A2 = B1;
                String B2 = XorFunction(Z2, A1);
                //System.out.println("A2B2:" + A2 + B2); //Output Z + A1      
                //===========================Finish Round-2 ====================
                //Swap B2A2 
                String temp1 = B2;
                String temp2 = A2;
                String B2A2 = temp1+temp2;

                //Rotate Right 
                int B2A2_int = Integer.parseInt(B2A2, 2);
                fourBitRotateRight(B2A2_int);
                String Plain = ToBinary(fourBitRotateRight(B2A2_int));
                System.out.println("Plain-Text Result: "+ Plain);
                System.out.println("-------------------------------");
            }
            System.out.println("======================================="
                               + "====================================");
        }
    }
}
