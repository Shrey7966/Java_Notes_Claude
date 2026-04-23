package Problems;

public class Program33 {

	public static void main(String[] args) {
		// Fibbinoci series
		// 0 1 1 2 3 5 8 13 21 34 .....

		int first = 0;
		int second = 1;
		int third = 0;
		int series = 10;

		for (int i = 0; i < series; i++) {
			
			third = first + second; // 1 2 3
			System.out.print(first + " ");
			first = second; // 1 1
			second = third; // 1 2

		}

	}

}
