package Problems;

public class Program27 {

	public static void main(String[] args) {
		//Number is Palindrome or not
		
		int num = 1222;
		
		int num_test = num;
		int reverse = 0;
		
		while(num_test>0) {
			reverse = num_test %10 + reverse *10;
			num_test/=10;
		}
		
		Boolean isPalindrome = (num==reverse)?true:false;
		
		System.out.println("Number-> "+ num);
		System.out.println("Reverse-> "+ reverse);
		
		System.out.println("isPalindrome -> "+ isPalindrome);

	}

}
