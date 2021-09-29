In order to run Task-2
--------------------------
After Execute Kamasutra.java  (>javac Kamasutra.java) 

Firstly, use the below java command to generate "keyfile.txt" first, 
>java Kamasutra -k keyfile.txt 

After generated "keyfile.txt", now to Encrypt the PText-1.txt-->this file is from Task-1 CText-1's output
>java Kamasutra -e keyfile.txt PText-1.txt Ctext-3.txt

To Decrypt back, use the below command
>java Kamasutra -d keyfile.txt Ctext-3.txt Output.txt

(Kindly see the attacked "Kamasutra_Sample_Run" Video for more detail.)  