/*
 * Name - Hay Munn Hnin Wai
 * UOW ID - 6573277
 * CSI361: Assignment-1 , Task-2
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Kamasutra
{
   public static void main(String[] args) throws IOException
   {
        if( args.length > 0)
        {
          for ( int i = 0; i< args.length; i++)
          {
              System.out.print(args[i]+ " "); 
          }
          System.out.println("\n");
          if ( args[0].equals("-k"))
          {
            try
            {
                File file = new File(args[1]);   //keyfile.txt
                if ( file.createNewFile())
                {
                    System.out.println("Key File successfully created: " + file.getName());
                    
                //Write to "keyfile.txt"
                String key = Keycode_LowerCase(26);     //get key values
                FileWriter fout = new FileWriter(args[1],true);

                System.out.println("Key Values: ");     //Print out Key values
                System.out.println("=============");
                for ( int i=0; i< 13; i++)
                {
                    System.out.print(key.charAt(i));  //Print out 1st Row key-values 
                }
                System.out.println("");
                for ( int i=13; i< 26; i++)
                {
                    System.out.print(key.charAt(i));  //Print out 2nd Row key-values 
                }
                System.out.println("\n");
                //Write to "keyfile.txt"
                fout.write(key);
                fout.close();
            }
            else
            {
                System.out.println("Key File already exists.");
                System.out.println();
            }
            }
            catch(IOException e)
              {
                System.out.println("An error occurred.");
                e.printStackTrace();
              }
            
        }//End of generate keyfile.txt 
          
        //=======================Encryption-->Generate ciphertext.txt===============
        else if (args[0].equals("-e"))   //"-e keyfile.txt PText-1.txt Ctext-3.txt"
        { 
            String key= "";
            //Read "keyfile.txt"
            try{
                File file1 = new File(args[1]);     //Read "keyfile.txt"
                Scanner readKey = new Scanner(file1);
                while(readKey.hasNextLine())
                {
                    key = readKey.nextLine();    
                }
                System.out.println("Key Values: ");   //Print out Key values
                System.out.println("=============");
                for ( int i=0; i< 13; i++)
                {
                    System.out.print(key.charAt(i));  //Print out 1st Row key values 
                }
                System.out.println("");   
                for ( int i=13; i< 26; i++)
                {
                    System.out.print(key.charAt(i));  //Print out 2nd Row key values 
                }
                System.out.println("\n");
               }
            catch(IOException e)
            {
                System.out.println("An Error occurred");
                e.getStackTrace();
            }
            String data;
            String cipher;
            //Read Plain Text 
            File file = new File(args[2]);        //Read "PText-1.txt"
            Scanner read = new Scanner(file);
            
            //Create Ciphertext file 
            File Cfile = new File(args[3]);
            if(Cfile.createNewFile())
            {
                System.out.println("Cipher text file was created: "+ Cfile.getName());
            }
            else
            {
                System.out.println("CipherText file \"Ctext-3.txt\" already exists!");
            }
            System.out.println("");
            while(read.hasNextLine())            //Read in PText-1 
            {
                data = read.nextLine();     
                cipher = KamaSutra(data, key);    //Encrypt the plain data 
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(cipher).append(" "); 
                 
                //Write to CText-3.txt
                FileWriter Ciphout = new FileWriter(args[3],true);
                Ciphout.write(cipher+ " ");  
                Ciphout.close();
            } 
            read.close(); 
        }//End of generating "CText-3".txt 
          
        //==================Decryption=================================
        else if (args[0].equals ("-d"))  //-d keyfile.txt Ctext-3.txt Output.txt
        {
            String key = "";
            //Read "keyfile.txt"
            try{
                File file1 = new File(args[1]);     //Read "keyfile.txt"
                Scanner readKey = new Scanner(file1);
                while(readKey.hasNextLine())
                {
                    key = readKey.nextLine();    
                }
                System.out.println("Key Values: ");   //Print out Key values
                System.out.println("=============");
                for ( int i=0; i< 13; i++)
                {
                    System.out.print(key.charAt(i));  //Print out 1st Row key values 
                }
                System.out.println("");   
                for ( int i=13; i< 26; i++)
                {
                    System.out.print(key.charAt(i));  //Print out 2nd Row key values 
                }
                System.out.println("\n");
               }
            catch(IOException e)
            {
                System.out.println("An Error occurred");
                e.getStackTrace();
            }
            //Read CText-3.txt 
            String Cdata;
            String plain;
            File Cfile = new File(args[2]);        //Read "Ctext-3.txt"
            Scanner readCtext = new Scanner(Cfile);
            
           //Create Output file 
            File Outputfile = new File(args[3]);
            if(Outputfile.createNewFile())
            {
                System.out.println("Output text file was created: "+ Outputfile.getName());
            }
            else
            {
                System.out.println("Output file \"Output.txt\" already exists!");
            }
            System.out.println("");
            while(readCtext.hasNextLine())        
            {
                Cdata = readCtext.nextLine();     
                plain = KamaSutra(Cdata, key);    //Decrypt the Cipher text  
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(plain).append(" "); 
                    
                //Write to Output.txt
                FileWriter fout = new FileWriter(args[3],true);
                fout.write(plain+ " "); 
                System.out.println(" ");
                fout.close();
            } 
            readCtext.close(); 
        }//End of generating Decrypted Output.txt 
   }
    else if( args.length < 0)
    {
        System.out.println("Enter below command lines to run the file!");
        System.out.println(" To Generate Key File: \"java Kamasutra -k keyfile.txt\" ");
        System.out.println(" Encryption: \"java Kamasutra -e keyfile.txt PText-1.txt Ctext-3.txt\" ");
        System.out.println(" Decryption : \"java Kamasutra -d keyfile.txt Ctext-3.txt Output.txt\" ");
    }
        
}
   //Generate Key Values 
   public static String Keycode_LowerCase(int len){    //Pass in 26 Alphabet letters 
      Random r = new Random();
      String key = "";
      for (int i = 0; i < len;) {
          char c = (char) (r.nextInt(26) + 'a');
          if(!key.toString().contains(""+c)){
             key = key + c;
             i++;
          }
      }      
      return key;
   }
   
   //Kamasutra Encryption & Decryption Algorithm 
   public static String KamaSutra(String text,String key)
   {
      int keyLen = key.length()/2;
      
      // arrange random key
      char[][] keyRow = new char[2][keyLen];    
      int count=0;
      for(int x=0;x<2;x++)
      {
         for(int y=0;y<keyLen;y++){
            keyRow[x][y] = key.charAt(count);
                //System.out.print(keyRow[x][y]+" ");
            count++;
         }
         //System.out.println();
      }
      
      String sb = "";
      
      count=0;
      for(int x=0;x<text.length();x++){
         for(int y=0;y<2;y++){
            for(int z=0;z<keyLen;z++){
               if(y == 0){
                  if(text.charAt(x) == keyRow[y][z])
                     sb += keyRow[y+1][z];
               }
               else if (y == 1){
                  if(text.charAt(x) == keyRow[y][z])
                     sb += keyRow[y-1][z];
               }
            }
         }
         if(text.charAt(x) == ' ')
            sb += text.charAt(x);
      }
    return sb;
   }
}