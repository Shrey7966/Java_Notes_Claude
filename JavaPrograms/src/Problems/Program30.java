package Problems;

public class Program30 {

	public static void main(String[] args) {

		int num = 1212122;

		int countEven = 0;
		int countOdd = 0;

		while (num > 0) {
			int digit = num % 10;
			var res = ((digit & 1) == 0 )?countEven++:countOdd++;
			num = num / 10;
		}
		
		System.out.println("Even "+ countEven);
		System.out.println("Odd "+ countOdd);

	}

}
