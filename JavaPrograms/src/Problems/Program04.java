package Problems;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Program04 {

	public static void main(String[] args) {
	
		//two strings are Anagram
		String str1 = "army";
		String str2 = "mary";
		
		char[] charArray1 = str1.toLowerCase().toCharArray();
		char[] charArray2= str2.toLowerCase().toCharArray();
		
		
		Arrays.sort(charArray1);
		Arrays.sort(charArray2);
		
		if(Arrays.equals(charArray1, charArray2)) {
			System.out.println("Its Anagram");
		}else {
			System.out.println("No Its Not Anagram");
		}

	}

}
