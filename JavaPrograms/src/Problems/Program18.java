package Problems;

public class Program18 {

	public static void main(String[] args) {

		// reverse a number

		int num = 98765;
		int result = 0;

		while (num > 0) {

			result = num % 10 + result * 10; // 5 + 0 = 5 | 4 + 50 = 54 | 3 + 540 = 543
			num = num / 10; // 1234 | 123
		}
		System.out.println(result);

	}

}
