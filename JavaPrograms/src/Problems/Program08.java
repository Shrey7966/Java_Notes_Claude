package Problems;

public class Program08 {

	public static void main(String[] args) {
	
		String s = "madam";
		
		System.out.println(isPalindrome(s));

	}
	
	public static boolean isPalindrome(String str) {
		
		int left = 0;
		int right = str.length()-1;
		
		while(left < right) {
			if(str.charAt(left++) !=str.charAt(right--)) {
				return false;
			}
		}
		return true;
	}

}
