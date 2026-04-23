package Problems;

public class Program32 {

	public static void main(String[] args) {
		// Largest of 3 Numbers

		int a = 40;
		int b = 30;
		int c = 120;

		if (a > b && a > c) {
			System.out.println("a is greater");
		} else if (c > a && c > b) {
			System.out.println("c is greater");
		} else {
			System.out.println("b is greater");
		}

		int largest = (a > b && a > c) ? a : ((b > a && b > c) ? b : c);

		System.out.println(largest);

	}

}
