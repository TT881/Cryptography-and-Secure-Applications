/*
 * ID - 6573277
 * Name - Hay Munn Hnin Wai
 * 
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Scanner;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
 
public class RingSig {
 
    private static SecretKeySpec secretKey;
    private static byte[] key;
 
    public static void setKey(String myKey) 
    {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16); 
            secretKey = new SecretKeySpec(key, "AES");
        } 
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } 
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    public static String encrypt(String strToEncrypt, String secret) 
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        } 
        catch (Exception e) 
        {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }
 
    public static String decrypt(String strToDecrypt, String secret) 
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } 
        catch (Exception e) 
        {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }
 public static void main(String[] args) throws IOException 
 {
 
    System.out.println("Step-1");
    System.out.println("============");
    BigInteger e1,N1;
    BigInteger e2,N2;
    
    //Public Key   
    e1 = new BigInteger("3");        //User-1 
    N1 = new BigInteger("55") ;
    e2 = new BigInteger("27");      //Signer User-2
    N2 = new BigInteger("65") ;
    //Private Key 
    BigInteger d1 = new BigInteger("27");
    BigInteger d2 = new BigInteger("29");   
    
    System.out.printf("Alice's Public Key Pair is : %d, %d ", e1, N1) ;
    System.out.println("");
    System.out.printf("Alice's Private Key Pair is : %d, %d ", d1, N1) ;
    System.out.println("");
    System.out.printf("Bob's Public Key Pair is : %d, %d ", e2, N2) ;
    System.out.println("");
    System.out.printf("Bob's Private Key Pair is : %d, %d %n", d2, N1) ;
    
    //Random glue no = 69 
    BigInteger v = new BigInteger("69");
    System.out.println("User-2 Choose a Random Glue v: " + v);
    //Message from "message.txt" 
    String msg = ReadMessageFile("message.txt");      //Get Message 
    System.out.println("User Message: "+ msg);
    System.out.println("");
    
    //Hash(message) using SHA-1
    String Hmsg = SHA1(msg);
    
    //Symmetric Encryption E using AES Algorithm 
    final String secretKey = Hmsg ;
     
    String originalString = msg;
    String encryptedString = RingSig.encrypt(originalString, secretKey) ;
    String decryptedString = RingSig.decrypt(encryptedString, secretKey) ;
     
   // System.out.println(originalString);
   //System.out.println(encryptedString);
   //System.out.println(decryptedString);
   
    System.out.println("Step 2 - Ring Singature Generation ");
    System.out.println("=======================================");
   //Pick a Random Fake Key for User-1 (xa,ya) 
    BigInteger xa = new BigInteger("4");
    BigInteger ya = new BigInteger("16");
    System.out.printf("User-2 Pick a Random Fake Key (xa,ya) to User1 : %d , %d%n", xa, ya);
    
    //Solve Ring Equation y2 = Ek^-1(v) + Ek(ya XOR glue) 
    //1. Compute (ya Xor Glue) 
    String yaStr = ya.toString(2);
    System.out.println("ya In Binary : "+ yaStr);
    String glueStr = v.toString(2);              //Convert Glue-v to Binary String 
     System.out.println("Convert Glue v to Binary: " + glueStr);
    StringBuilder sb = new StringBuilder();
    for(int i = 0; i < yaStr.length(); i++)
    {
        sb.append((yaStr.charAt(i) ^ glueStr.charAt(i)));
    }
    String result = sb.toString();
    System.out.println("( ya XOR v: ) " + result);

    //2.Compute Encrypted ---> Ek(ya Xor glue-v) 
    String result2 = RingSig.encrypt(result, secretKey);
    System.out.println("Encrypted (ya XOR v): "+ result2 );
    
        //Encrypt Glue-v with Secret Key
    String EGlue = RingSig.encrypt(glueStr,secretKey);
    System.out.println("Encrypted Glue : " +EGlue );
    
    //3.Compute Decrypted glue-v --> E^-1k(v) 
    String DecryptGlue = RingSig.decrypt(glueStr, secretKey);
    System.out.println("Decrypted (Glue): "+ DecryptGlue);
    //Convert glue to decimal 
    int DecGlue = Integer.parseInt(DecryptGlue,2);  
    
    //yb = result2 XOr DecryptGlue 
    String a = result2 , b = DecryptGlue;  
    String yb = getXOR(a,b);
    System.out.println(yb);
    
}   
    //Read "mssg.txt" file 
    private static String ReadMessageFile(String mssg) throws FileNotFoundException, IOException 
    {
        String message= " ";
        try
        {
            Path path = Paths.get(mssg);
            Scanner scanner = new Scanner(path);
            System.out.println("\nRead user message from \"message.text\" file. ");
            //read line by line
            while(scanner.hasNextLine()){
                //process each line
                message = scanner.nextLine();
            } 
        }catch( IOException e)
        {
            System.out.println("Error in Reading File ! ");
            System.out.println(e.toString());
        }
        return message;
    }
    //SHA1 Hash Function 
    public static String SHA1(String input) 
    { 
        try { 
            // getInstance() method is called with algorithm SHA-1 
            MessageDigest md = MessageDigest.getInstance("SHA-1"); 
  
            // digest() method is called 
            // to calculate message digest of the input string 
            // returned as array of byte 
            byte[] messageDigest = md.digest(input.getBytes()); 
  
            // Convert byte array into signum representation 
            BigInteger no = new BigInteger(1, messageDigest); 
  
            // Convert message digest into hex value 
            String hashtext = no.toString(16); 
  
            // Add preceding 0s to make it 32 bit 
            while (hashtext.length() < 32) { 
                hashtext = "0" + hashtext; 
            } 
  
            // return the HashText 
            return hashtext; 
        } 
  
        // For specifying wrong message digest algorithms 
        catch (NoSuchAlgorithmException e) { 
            throw new RuntimeException(e); 
        } 
    }
    //add Zeros 
    static String addZeros(String str, int n)  
    {  
        for (int i = 0; i < n; i++)  
        {  
            str = "0" + str;  
        }  
        return str; 
    }  
      
    // Function to return the XOR  
    // of the given strings  
    static String getXOR(String a, String b)  
    {  
      
        // Lengths of the given strings  
        int aLen = a.length();  
        int bLen = b.length();  
      
        // Make both the strings of equal lengths  
        // by inserting 0s in the beginning  
        if (aLen > bLen)  
        {  
            a = addZeros(b, aLen - bLen);  
        }  
        else if (bLen > aLen)  
        {  
            a = addZeros(a, bLen - aLen);  
        }  
      
        // Updated length  
        int len = Math.max(aLen, bLen);  
      
        // To store the resultant XOR  
        String res = "";  
          
        for (int i = 0; i < len; i++) 
        {  
            if (a.charAt(i) == b.charAt(i))  
                res += "0";  
            else
                res += "1";  
        }  
        return res;  
    }  
}
