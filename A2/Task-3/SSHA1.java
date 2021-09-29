/*
 * UOW ID - 6573277
 * Name - Hay Munn Hnin Wai
 * Task3 - Collision Finding of SHA-1 Hash Function
 */
 
 /* SHA-1 Algorithm Online Reference: 
 http://www.sha1-online.com/sha1-java/	*/

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
        
public class SSHA1 
{
    //Hash the message & Convert to 36 bits SSHA1 
    public static String getSShaHex(String msg ) throws NoSuchAlgorithmException
    {
        String hashedM = SHA1(msg);     
            //System.out.println(hashedM);
        String sshaHex = hashedM.substring(32,40);          //8 Bytes = 32 bits 
        String binHashedM = convertHexToBinary(hashedM);   //Convert HashedMessage to Binary 
        String sshaBin = convertHexToBinary(hashedM);      //Convert SSHA to Binary 
               
        return sshaHex;           
    }
    public static String convertHexToBinary(String hex)
    {
        String bin = "";
        String binFragment = "";
        int iHex;
        hex = hex.trim();
        hex = hex.replaceFirst("0x", "");

        for(int i = 0; i < hex.length(); i++){
            iHex = Integer.parseInt(""+hex.charAt(i),16);
            binFragment = Integer.toBinaryString(iHex);

            while(binFragment.length() < 4){
                binFragment = "0" + binFragment;
            }
            bin += binFragment;
        }
        return bin;
    }
    static String SHA1(String input) throws NoSuchAlgorithmException 
    {
        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
        byte[] result = mDigest.digest(input.getBytes());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < result.length; i++)
        {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    public static void main(String [] args ) throws InterruptedException, NoSuchAlgorithmException{
        int x = 0;
        int matchX = -1;
        int counter = 0;
        long start = System.nanoTime();
        
        HashMap<String, Integer> hashMsg = new HashMap<>();
        
        boolean found = false; 
        System.out.println("\nCollision Finding of Hash Algorithm: ");
        System.out.println("============================================");
        
        do
        {
            counter++;
            String msg = "Mr.Yang lee owes Hay Munn " + x + " dollars ";
            String sshaHex = getSShaHex(msg);
            
            if(hashMsg.isEmpty())
            {
                hashMsg.put(sshaHex, x);
                x++;  
            }
            else
            {
                if(hashMsg.containsKey(sshaHex))
                {
                    matchX = hashMsg.get(sshaHex);
                    System.out.println("Match X:"+ matchX);
                    found =  true;
                }
                else
                {
                   hashMsg.put(sshaHex, x);
                   x++;
                }
            }
        }while(!found);
        System.out.println("Nmber of Trials: "+ counter);
        System.out.println("The pair of integers , (x,x') where (x != x') ...");
        System.out.println("x:"+ matchX);
        System.out.println("x'"+ x);
        
        String msg1 = "Mr.Yang lee owes Hay Munn " + matchX + " dollars ";
        String msg2 = "Mr.Yang lee owes Hay Munn " + x + " dollars ";
        String hashVal1 = getSShaHex(msg1);
        String hashVal2  = getSShaHex(msg2);
        System.out.println("\nTwo messages with Same Hash values: ");
        System.out.println(msg1 + "hash values: "+ hashVal1 + "\n" + msg2 + "hash values: "+ hashVal2);
        
        long end = System.nanoTime();
        // execution time
        long execution = (end - start)/1000000;
        System.out.println("\nExecution time: " + execution + " miliseconds");
    }
}
