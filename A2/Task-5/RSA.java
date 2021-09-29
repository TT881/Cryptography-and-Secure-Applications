/*
 * UOW ID - 6573277
 * Name - Hay Munn Hnin Wai 
 * Task5 - RSA Encryption & Decryption
 */
 
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.Scanner;
    
public class RSA 
{
   private final static BigInteger one      = new BigInteger("1");
   private final static SecureRandom random = new SecureRandom();

   private static BigInteger privateKey;   //d
   private static BigInteger publicKey;    //e
   private static BigInteger modulus;     //Modulus n = p*q
   private static BigInteger phi;         //phi = (p-1)*(q-1)
   private static BigInteger gcdVal;
   //Compute Key 
    private static void KeyGeneration(BigInteger p, BigInteger q) throws IOException 
    {
        Boolean checkgcd ;    
        if (p.compareTo(q) < 0)     //Check p > q 
        {
                BigInteger tmp = p;
                p = q;
                q = tmp;
        }
        modulus =  p.multiply(q);  
        System.out.println("\nCompute Modulus n (pxq) : "+ modulus);
        phi = (p.subtract(one)).multiply(q.subtract(one));   // (p-1)*(q-1)
            System.out.println("Phi(n): (p-1)*(q-1) = " + phi);
            
        //Randomly generate e (Note: e < modulus and gcd(e,Phi) = 1) 
        publicKey = new BigInteger(phi.bitLength(), random);
        gcdVal = publicKey.gcd(phi);     //get gcd(e,Pi(n))= 1    
        
        //Check gcd = 1 ?
        checkgcd = Checkgcd (publicKey,modulus,gcdVal);
       while(checkgcd == false)
       {
           publicKey = new BigInteger(phi.bitLength(), random);
           gcdVal = publicKey.gcd(phi); 
           checkgcd = Checkgcd (publicKey,modulus,gcdVal);
       }
        System.out.println("e(Public-Key): " + publicKey);
        String str = "GCD of (e,phi(n)): "+ publicKey+ " and " + phi +  " is " + gcdVal;  
        System.out.println(str + "\n"); 
        
        privateKey = publicKey.modInverse(phi);   //Compute Private-Key d ---> (d= e^-1 Mod Pi(n)) 
        System.out.println("d(Private-Key): " + privateKey);
        //Write to Public-key file -->(N,e) 
        PublicKeyFile(modulus,publicKey);
        System.out.println("Public-Key(N,e) was successfully write to \"pk.txt\" file ...");      
        System.out.println( "Public Key:( " + modulus + "," + publicKey + " )");
       
        //Write to Private-Key file -->(N,p,q,d) 
        System.out.println("Private-Key(N,p,q,d) was successfully write to \"sk.txt\" file ...");
        System.out.println( "Private Key:( " + modulus + "," + p + ","+  q+ "," + privateKey + " )");
        PrivateKeyFile(modulus,p,q,privateKey);
        
    }
    private static Boolean Checkgcd(BigInteger publicKey, BigInteger modulus, BigInteger gcdVal)   //gcd(e,Pi(n)) = 1? 
    {
        boolean flag = false;
        if((publicKey.compareTo(modulus) == -1) &&(gcdVal.equals(BigInteger.ONE)))  //If e < n & gcd== 1
        {
            flag = true;
        }  
        else if((publicKey.compareTo(modulus) == -1) && (!gcdVal.equals(BigInteger.ONE)))
        { 
            //System.out.println("gcd != 1 , Compute again! ");
            flag = false;
        } 
        else if(publicKey.compareTo(BigInteger.ONE) <= 0)
        {
            flag = false;
        }
        return flag;         //Return Correct e --
    }
    //Write(N,e) To --> "pk.txt" File 
    private static void PublicKeyFile(BigInteger n, BigInteger e)throws IOException 
    {
       String NVal = String.valueOf(n);  
       String PublicKeyVal = String.valueOf(e);
       String output = NVal + "," + PublicKeyVal;
       
       FileWriter writefile = new FileWriter("pk.txt",true);
       writefile.write(output + System.lineSeparator());
       writefile.close();
    }
    //Write (N,p,q,d) To --> "sk.txt" File 
    private static void PrivateKeyFile(BigInteger n, BigInteger p,BigInteger q, BigInteger d)throws IOException 
    {
       String NVal = String.valueOf(n);  
       String pVal = String.valueOf(p);
       String qVal = String.valueOf(q);
       String PrivateKeyVal = String.valueOf(d);
       String output1 = NVal + "," + pVal + "," + qVal + "," + PrivateKeyVal;
       
       FileWriter writefile = new FileWriter("sk.txt",true);
       writefile.write(output1 + System.lineSeparator());
       writefile.close();
    }
    private static boolean CheckPrime(BigInteger n)      //Check Prime and Bitlength
    {
        boolean result;
        if (!n.isProbablePrime(7) && n.bitLength() <32)        //When n is not Prime 
        {
            System.out.println(n.toString(10) + " is NOT probably prime.");
            System.out.println("It is " + n.bitLength() + " bits in length.");
            result = false;
        }
        else if (n.isProbablePrime(7) && n.bitLength() <32)
        {
            System.out.println(n.toString(10) + " is a probably prime.");
            System.out.println("But It is Only " + n.bitLength() + " bits in length.");
            result = false;
        }
        else 
        {
            System.out.println(n.toString(10) + " is a probably prime.");
            System.out.println("It is " + n.bitLength() + " bits in length.");
            result = true;  
        }
        return result; 
    }
    //Read "mssg.txt" file 
    private static BigInteger ReadMessageFile(String mssg) throws FileNotFoundException, IOException 
    {
        BigInteger message = new BigInteger("1");
        try
        {
            Path path = Paths.get("mssg.txt");
            Scanner scanner = new Scanner(path);
            System.out.println("Read user message from \"mssg.text\" file. ");
            //read line by line
            while(scanner.hasNextLine()){
                //process each line
                String msg = scanner.nextLine();
                message = new BigInteger(msg); 
            } 
        }catch( IOException e)
        {
            System.out.println("Error in Reading Message \"mssg.txt\" File ! ");
            System.out.println(e.toString());
        }
        return message;
    }
    //Encryption 
    private static BigInteger Encrypt(BigInteger Usermessage) throws IOException 
    {
        //Read Public-Key(N,e) from "pk.txt"
        BigInteger PKey = new BigInteger("1");
        BigInteger Modulus = new BigInteger("1");
        try
        {
            Path path = Paths.get("pk.txt");
            String read = "";
            Scanner scanner = new Scanner(path);
            //read line by line
            while (scanner.hasNextLine()) 
            {
                read = scanner.nextLine();          //Print the next line
		String [] info = read.split(",");   //break the given string 
		String N = info[0] ;
                String e =info[1] ;
                
                //Convert to BigInteger 
                Modulus = new BigInteger(N);
                PKey = new BigInteger(e);
            } 
        }
        catch(IOException e )
        {
            System.out.println("Error in Reading Public-Key File!");
            System.out.println(e.toString());
        }
        BigInteger cipher = Usermessage.modPow(PKey, Modulus);
        System.out.println("========Encryption Process================");
        System.out.println("Check Public Key : " + PKey);
        System.out.println("Check Mod N: "+ Modulus);
        System.out.println("User Message to Encrypt: "+ Usermessage);
        
        WriteToCTextFile(cipher);
        return Usermessage.modPow(PKey, Modulus);    //C = (M^e Mod N) 
    }
    //Save Encrypted Message to "cipher.txt" 
    private static void WriteToCTextFile(BigInteger cipher) throws IOException 
    {
       System.out.println("\"cipher.txt\" File successfully created ....... ");
       String cipherOutput = String.valueOf(cipher);
       FileWriter writefile = new FileWriter("cipher.txt",true);
       writefile.write( cipherOutput + System.lineSeparator());
       writefile.close();
    }
   //Decryption Process 
    private static void DecryptCipher(String cfile)    //cipher.txt 
    {
        //Read Private Key file - "sk.txt" 
        BigInteger SKey = new BigInteger("1");
        BigInteger Modulus = new BigInteger("1");
        BigInteger pVal = new BigInteger("1");
        BigInteger qVal = new BigInteger("1");
        try
        {
            Path path = Paths.get("sk.txt");
            String read = "";
            Scanner scanner = new Scanner(path);
            //read line by line
            while (scanner.hasNextLine()) 
            {
                read = scanner.nextLine();          //Print the next line
		String [] info = read.split(",");   //break the given string 
		String N = info[0] ;
                String p =info[1] ;
                String q =info[2] ;
                String d =info[3] ;
                
                //Convert to BigInteger 
                Modulus = new BigInteger(N);
                pVal = new BigInteger(p);
                qVal = new BigInteger(q);
                SKey = new BigInteger(d);
            } 
        }
        catch(IOException e )
        {
            System.out.println("Error in Reading Public-Key File!");
            System.out.println(e.toString());
        }
        //Read Cipher-File "cipher.txt" 
        BigInteger Cmessage = new BigInteger("1");
        try
        {
            Path path = Paths.get(cfile);       //Read cipher.txt file 
            Scanner scanner = new Scanner(path);
            System.out.println("Read Cipher-Text from " + cfile + " file. ");
            //read line by line
            while(scanner.hasNextLine()){
                //process each line
                String c = scanner.nextLine();
                Cmessage = new BigInteger(c);     //Convert to BigInteger
            } 
        }catch( IOException e)
        {
            System.out.println("Error in Reading File ! ");
            System.out.println(e.toString());
        }

            //Decrypt Cipher-Text 
        BigInteger plainText = Cmessage.modPow(SKey, Modulus);    //M = (Ci)^d Modn 

        System.out.println("========Decryption Process================");
        System.out.println("Check Private Key : " + SKey);
        System.out.println("Check Mod N: "+ Modulus);
        System.out.println("Check Encrypted Message: "+ Cmessage);
        System.out.println("Decrypted Message: "+ plainText);
    }
   public static void main(String[] args) throws IOException 
   {
       int choice;
       boolean result;
    do{
        BigInteger p,q;
        Scanner sc = new Scanner(System.in);
        System.out.println("\n====================================================");
        System.out.println("Kindly select the below options (1/2/3/4) : ");
        System.out.println("====================================================");
        System.out.println("1. Key generation");
        System.out.println("2. Encryption");
        System.out.println("3. Decryption");
        System.out.println("4. Quit");
        System.out.print("\nEnter Option No: ");
        choice = sc.nextInt();
        
           switch (choice) 
           {
                case 1:                                    //Key Generation 
                   //Ask user to Enter p & q (up to 32 bits length)
                   System.out.print("Please Enter (32 bits-length)prime no p: ");
                   p = sc.nextBigInteger();
                   result = CheckPrime(p);
                   while(result == false)
                   {
                       System.out.println("eg; 32 bit-length Prime No = 3594625387 ");
                       System.out.print("Please Enter(32 bits-length)prime no p again: ");
                       p = sc.nextBigInteger();
                       result = CheckPrime(p);
                   }      //Enter Prime no q
                   System.out.print("\nPlease Enter(32 bits-length)prime no q: ");
                   q = sc.nextBigInteger();
                   result = CheckPrime(q);
                   while (result == false)
                   {
                       System.out.println("eg; 32 bit-length Prime No = 2492377357 ");
                       System.out.print("Please Enter (32 bits-length)prime no q: ");
                       q = sc.nextBigInteger();
                       result = CheckPrime(q);
                   }      //Generate Key 
                   KeyGeneration(p,q);
                   break;
                case 2:                                            //Encryption 
                   //Read user Message from "mssg.txt" file
                   BigInteger Usermessage = ReadMessageFile("mssg.txt");
                   System.out.println("Read Public-Key from \"pk.text\" file. ");
                   //Encrypt(message);
                   System.out.println("Encrypted Message : " + Encrypt(Usermessage));
                   
                   break;
                case 3:                                            //Decryption 
                    //Ask Uer enter cipher.txt file 
                    System.out.print("Please Enter \"cipher.txt\" file to Decrypt the Message: ");
                    String cfile = sc.next();
                    
                    //Read Private-Key "sk.txt" File to get d 
                    DecryptCipher(cfile);
                   break;
                case 4:
                   System.out.println("\nExit Program......");
                   System.exit(0);
                default:
                   break;
           }
    }while(choice != 4);
        System.out.println("");
    }   
}