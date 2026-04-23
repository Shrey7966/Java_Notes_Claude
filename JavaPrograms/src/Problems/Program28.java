package Problems;

public class Program28 {

	public static void main(String[] args) {
		// ReverseString
		
		String name = "madam";
		String reverse = "";
		
		char[] charArray = name.toCharArray();
		
		for(int i = charArray.length-1; i>=0 ;i--) {
			reverse+=name.charAt(i);
		}
		
		Boolean isPalindrome = name.equals(reverse)?true:false;
		
		System.out.println("isPalindrome-> "+ isPalindrome);

	}

}
