To Run Task-5
-----------------
Please Execute 
--> javac RSA.java
--> java RSA 
I'd also attached "Task5 Sample Run" Video for reference. 

Ask user to choose options: (1/2/3/4) 
(If user choose = 1) 
Key Genration
---------------
1. Ask uer to Enter 32-bits length p & q : 
2. Compute n = p*1, phi(n) = (p-1)*(q-1) 
3. Choose Random e (Note: e < n, gcd(e,Phi(n)) = 1) 
4. Public Key: ( N,e) 
5. Save ( N,e ) to ----> "pk.txt" file  
6. Find d = e^-1 Mod pi(n) 
7. Private Key: (N,p,q,d) 
8. Save ( N,p,q,d) to --> "sk.txt" file 

(If user choose = 2) 
Encryption
------------
9. Read User's "mssg.txt" File 
10. Read "pk.txt" file to get Public Key 
11. Encrypt C = (Mi ^e) d Mod n 
12. Save Encrypted message C to --> "cipher.txt" file 

(If user choose = 3) 
Decryption
--------------
13. Ask user to Enter - "cipher.txt" file to Decrypt
14. Read Encrypted Message C from "cipher.txt" file 
15. Decrypt Cipher , m = (Ci) ^ d Modn 
16. Display Discrypted Message m on Screen. 

(If user choose = 4) 
17. Exit Program ! 







