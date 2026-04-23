package Problems;

public class Program29 {

	public static void main(String[] args) {
		// Number of Digits in a number
		
		int num = 121;
		
		int count = 0;
		
		while(num > 0) {
			num = num /10;
			count++;
		}
		
		System.out.println("Number of Digits ->  " + count);

	}

}
