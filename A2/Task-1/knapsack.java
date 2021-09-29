/*
	UOW ID - 6573277
	Name - Hay Munn Hnin Wai
	Task 1- Trapdoor KnapSack 
*/

import java.util.*;
import java.math.BigInteger;

public class knapsack {

        private LinkedList wList;
        static int size;
        knapsack(int wInt[])            //Add the nos inside Wint
        {
                wList = new LinkedList<>();
                for (int i = 0; i < wInt.length; i++) {
                        wList.add(wInt[i]);
                }
        }
        
        String MHEncrypt(MHPublicKey pubKey, String plainText){
                String plainTextBin, cipherText;
                LinkedList b;
                Iterator bIterator;
                int cipherInt, idx;

                System.out.println("\n---------Encryption-----------");
                System.out.println("Public Key is : " + pubKey.getB().toString());
                System.out.println("User inputs contains " + plainText.length() + " characters");
                b = pubKey.getB();
                plainTextBin = "";
                cipherText = "";
                cipherInt = 0;

                for(int i=0; i<plainText.length(); i++ ){
                        plainTextBin = charToBinary(plainText.charAt(i)); 
                        cipherInt = 0;
                        bIterator = b.iterator();    //Loop through PublicKey b
                        idx = 0;
                        while(bIterator.hasNext()){
                            cipherInt += Integer.parseInt(bIterator.next().toString()) * Integer.parseInt(Character.toString(plainTextBin.charAt(idx)));
                            idx ++;
                        }
                        
                        if(cipherText.equals("")){
                            cipherText = Integer.toString(cipherInt);
                        }else{
                            cipherText = cipherText + ' ' + Integer.toString(cipherInt);
                        }     
                }       
                return cipherText;
        }
        
        /**
         * @param privKey
         * @param cipherText
         * @return plain text string
         */
        String MHDecrypt(MHPrivateKey privKey, String cipherText){
                String plainText, plainTextBin;
                String[] cipherTextArray;
                BigInteger rModInverse, plainTextBI,tmp;
                LinkedList w, plainBinList;
                Iterator wIterator, pIterator;
                
                System.out.println("\n---------Decryption-----------");
                rModInverse = privKey.getR().modInverse(privKey.getQ());
                System.out.println("RModInverse is : " + rModInverse);
                w = privKey.getW();
                System.out.println("Private Key is: " + "W = " + w + "; Q = " + privKey.getQ().toString() + "; R = " + privKey.getR().toString());
                
                plainText = "";
                plainTextBin = "";
                cipherTextArray = cipherText.split(" ");
                for(int i=0; i<cipherTextArray.length; i++){
                        plainTextBin = "";
                        cipherText = cipherTextArray[i];
                        
                        //Calculate plainTextBI = cipherText * rModInverse mod q
                        plainTextBI = (new BigInteger(cipherText)).multiply(rModInverse).mod(privKey.getQ());     
                        plainBinList = new LinkedList<>();
                        wIterator = w.descendingIterator();//reverse w, move large number to head 
                        while(wIterator.hasNext()){
                                tmp = plainTextBI.subtract(new BigInteger(wIterator.next().toString()));
                                if(tmp.compareTo(new BigInteger("0")) < 0 ){
                                        plainBinList.addFirst(0);
                                }else{
                                        plainBinList.addFirst(1);
                                        plainTextBI = tmp;
                                }
                        }
                        
                        pIterator = plainBinList.iterator();
                        while(pIterator.hasNext()){
                                plainTextBin += pIterator.next().toString();
                        }
                        plainText = plainText + Character.toString(binaryToChar(plainTextBin));
                }
                
                return plainText;
        }
        
        /**
         * @param ch a character which will be transferred to binary
         * @return binary code of the input character
         */
        String charToBinary(char ch){
                String chBin = Integer.toBinaryString(ch & 0XFF);
                    int chBinlength = chBin.length();          //Get the Binary Size 
                chBin = String.format("%8s", chBin).replace(' ', '0');
                System.out.println("Convert Plaintext to 8 bits Binary:" + chBin);      
                return chBin;
        } 
        
        /**
         * @param binStr the binary string
         * @return ASCII character of the binary string
         */
        char binaryToChar(String binStr)
        {
                char[] temp = binStr.toCharArray();
        int sum = 0;  
        for (int i = 0; i < temp.length; i++) {
            sum += (Integer.parseInt(Character.toString(temp[i])) << (temp.length - i - 1)); 
        }  
      
        return (char) sum;  
        }
         
        LinkedList getW(){
                return wList;
        }
         static boolean CheckInreasing(int[] array)
         {
            int sum= 0;
            boolean result = true;
            for(int i=1; i<array.length; i++)
            {
                sum +=array[i-1];
                if (array[i]<sum)
                result = false;
            } 
            return result;
        }
        public static void main(String[] args) 
        {
                LinkedList wList;
                String plainText, cipherText;
                MHKey key;
                BigInteger q, r;
                knapsack mhTest;
                
                //create the Super-Increasing set of numbers
                Scanner sc = new Scanner (System.in);
                System.out.print("Please the size of the Knapnack(8 is preferable size):   ");
                size = sc.nextInt();
                int [] wInt = new int [size];    //Create array 
                System.out.println("Pleas enter the Super-Increasing Knapsack: ");
                for ( int i = 0; i < size; i++ )
                {
                   System.out.printf("Enter integer a%d: ",i+1);
                   if(sc.hasNextInt()) 
                   { 
                        wInt[i]=sc.nextInt(); 
                   }
                }
                boolean result= CheckInreasing(wInt);
                while(result == false)
                {
                    System.out.println("\nNumbers have to be in Super-Increasing Order!");
                     System.out.println("Eg; Integer 2>Integer 1 ,Integer 3> Integer 2+1");
                    System.out.println("Please Enter Increasing set again:");
                    for ( int i = 0; i < size; i++ )
                    {
                        System.out.printf("Enter integer a%d: ",i+1);
                       if(sc.hasNextInt()) 
                       {
                            wInt[i]=sc.nextInt(); 
                            result = CheckInreasing(wInt);
                       }
                    }   
                }

                mhTest = new knapsack(wInt);
                wList = mhTest.getW();
                key = new MHKey(wList);
                          
                System.out.println("");
                System.out.print("Please Enter PlainText: ");
                sc.nextLine();
                plainText = sc.nextLine();
                cipherText = mhTest.MHEncrypt(key.getPubKey(), plainText);
                System.out.println("Cipher Text is : " + cipherText);
                
                plainText = mhTest.MHDecrypt(key.getPrivKey(), cipherText);
                System.out.println("Plain Text is : " + plainText);
        }
}

final class MHKey{
        MHPrivateKey privKey;
        MHPublicKey pubKey;
        
        MHKey(){
                privKey = new MHPrivateKey();
                pubKey = new MHPublicKey();
        }
        
        
        MHKey(LinkedList w){
                generateKey(w);
        }
        
        /**
         * Generate privateKey and pubKey base on super increasing list w
         * @param w
         */
        void generateKey(LinkedList w){
                BigInteger wSum, q, r;
                LinkedList b;
                Iterator wIterator;
  
                System.out.println("w is : " + w.toString());
                wSum = new BigInteger("0");
                wIterator = w.iterator();
                //Get the SUM of each node of w
                while(wIterator.hasNext()){
                        wSum = wSum.add(new BigInteger(wIterator.next().toString()));
                }
                System.out.println("wSum is : " + wSum.toString());     //get Sum of ai
                
                //Ask User Enter Modulus q 
                System.out.println("\n---------Enter Modulus q, which should be greater than wSum------------");
                System.out.print("Modulus q: ");
                Scanner sc = new Scanner(System.in);
                q = sc.nextBigInteger();
                
                while(q.compareTo(wSum) <= 0){
                    System.out.println("Fail q is :" + q.toString());
                    System.out.print("Please Enter Modulus q again:(q> wSum): ");  
                    q = sc.nextBigInteger();         
                }
                System.out.println("Success q is :" + q.toString());
                
                //Ask User Enter Multiplier r
                System.out.println("\n---------Enter Multiplier r, to make gcd(r,q) = 1------------");
                System.out.print("Multiplier r: ");
                r = sc.nextBigInteger();         
                while(Integer.parseInt(r.gcd(q).toString()) != 1)
                {
                    System.out.println("Fail r is :" + r.toString());
                    System.out.print("Please Enter a multiplier that is coprime to the Mod value : ");  
                    r = sc.nextBigInteger();
                }
                System.out.println("Success r is :" + r.toString());
                
                //Generate Public Key -->( bi = rw Mod q) 
                System.out.println("\n---------Generate Public Key b, b = rw mod q------------");
                b = new LinkedList<>();
                wIterator = w.iterator();
                while(wIterator.hasNext()){
                        b.add(r.multiply(new BigInteger(wIterator.next().toString())).mod(q));
                }
                System.out.println("b is : " + b.toString());
                
                privKey = new MHPrivateKey(w, q, r);
                pubKey = new MHPublicKey(b);
        }
        MHPrivateKey getPrivKey(){
                return privKey;
        }
        MHPublicKey getPubKey(){
                return pubKey;
        }
}
class MHPrivateKey{
        private LinkedList w;
        private BigInteger q;
        private BigInteger r;
        
        MHPrivateKey(){
                this(null, null, null);
        }
        MHPrivateKey(LinkedList w, BigInteger q, BigInteger r){
                this.w = w;
                this.q = q;
                this.r = r;
        }
        
        LinkedList getW(){
                return w;
        }
        
        BigInteger getQ(){
                return q;
        }
        
        BigInteger getR(){
                return r;
        }  
}
class MHPublicKey{
        private LinkedList b;
        
        MHPublicKey(){
                this(null);
        }
        
        MHPublicKey(LinkedList b){
                this.b = b;
        }
        
        LinkedList getB(){
                return b;
        }
}
