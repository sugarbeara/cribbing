package cribpack;
//sugarbeara on github
import java.math.BigInteger;
import java.util.Scanner;

public class Decryptcrib {
	public static void main(String args[]) {
		Scanner scan = new Scanner(System.in);
		String cipher1 = "", cipher2 = "", drag = "", crib = "", test = "", text = "", key = "";
		int padding = 0;
		//Enter cipher text 1
		System.out.println("Enter ciphertext 1: ");
		cipher1 = scan.nextLine();
		//Enter cipher text 2
	    System.out.println("Enter ciphertext 2: ");
		cipher2 = scan.nextLine();
		
		 //str.length() for padding
	    if(cipher1.length() > cipher2.length()) {
	    	padding = cipher1.length() - cipher2.length();
	    	for(int i = 0; i < padding; i++) {
	    		cipher2 = cipher2 + "0";
	    	}
	    }else{
	    	padding = cipher2.length() - cipher1.length();
	    	for(int i = 0; i < padding; i++) {
	    		cipher1 = cipher1 + "0";
	      }
	    }
        //XOR ciphers
        drag = XORValues(cipher1, cipher2);
        //System.out.println(drag);
		 
		 //Cribbing loop
        System.out.println("Enter word (or -999 to exit):");
		crib = scan.nextLine();
		 while(!crib.contentEquals("-999")){
			test = AtoH(crib); //convert to hex for XOR
			for(int i = 0; i < drag.length() - test.length(); i++) {//-test.length()???
				String sub = drag.substring(i, i + test.length());//to see the xor against entire mixed message
				String toSee = XORValues(test, sub); //xor
				String readable = HtoA(toSee);//back to ascii for readability
				System.out.println(i + " " + readable); //for crib assessment
			}
		    //word for cribbing
			System.out.println("Enter word (or -999 to exit):");
			crib = scan.nextLine();	
		 }
		 
		 System.out.println("What is the true text?");//to find key
		 text = scan.nextLine();
		 String hexText = AtoH(text); 
		 System.out.println("Enter matching ciphertext:");
		 String cipherMatch = scan.nextLine();
		 
		  System.out.println(hexText);
		  System.out.println(cipherMatch);
		  key = XORValues(cipherMatch, hexText);
		 
		 System.out.println(key);//for finding all ciphertexts
		 System.out.println("Enter the true key:");
		 String trueKey = scan.nextLine();
		 int keyLen = trueKey.length();
		 System.out.println("Enter ciphertext to continue or -999 to exit:");
		 String breaker = scan.nextLine();
		 while(!breaker.equals("-999")) {
			 for(int i = 0; i < breaker.length(); i++) {
					if(keyLen == i)
						i = 0;
					if(trueKey.length() == breaker.length())
						break;
					trueKey += trueKey.charAt(i);
			 }
			 String mess = XORValues(trueKey, breaker);
			 System.out.println(HtoA(mess));
			 System.out.println("Enter next message to continue or -999 to exit:");
			 breaker = scan.nextLine();
		 }
		 System.out.println("The program has concluded.");
		//be finished be free
	}
	
	//XOR values
	public static String XORValues(String str1, String str2) {
		BigInteger b1 = new BigInteger(str1, 16);
		BigInteger b2 = new BigInteger(str2, 16);
		BigInteger xor = b1.xor(b2);
		String xResult = xor.toString(16);
		return xResult;
	}
	
	public static String AtoH(String ascii) {
		char[] chars = ascii.toCharArray();//convert ascii to hex
	    StringBuilder hexString = new StringBuilder();
	    for (char ch : chars) {
	        hexString.append(Integer.toHexString((int) ch));
	    }
	 
	    return hexString.toString();
	}
	
	public static String HtoA(String hexa) {//hex to ascii
		StringBuilder output = new StringBuilder("");
		 if(hexa.length() % 2 != 0) {//for case that hex is not even number of digits
			 char last = hexa.charAt(hexa.length()-1);
			 hexa = hexa.substring(0, hexa.length() -1) + "0" + last;
		 }
	      for (int i = 0; i < hexa.length(); i += 2)
	      {
	         String str = hexa.substring(i, i + 2);
	         output.append((char) Integer.parseInt(str, 16));
	      }
	      return output.toString();
	   }
}
