package Problems;

public class Program38 {

	public static void main(String[] args) {

		int[] num = { 1, 2, 3, 4, 5, 7, 8 };

		int n = num.length + 1;

		int totalSum = n * (n + 1) / 2;

		int arrSum = 0;

		for (int item : num) {
			arrSum += item;
		}

		System.out.println(totalSum);
		System.out.println("Missing Number is " + (totalSum - arrSum));

	}

}
