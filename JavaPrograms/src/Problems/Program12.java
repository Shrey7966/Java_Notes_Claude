package Problems;

public class Program12 {

	public static void main(String[] args) {

		// to find second max in an array

		int[] arr = { 99, 87, 65, 36, 17, 108, 76 };

		int secondMax = arr[0];
		int max = arr[0];

		for (int i : arr) {

			if (i > max) {
				secondMax = max;
				max = i;

			}

		}

		System.out.println("Max -> " + max);
		System.out.println("SecondMax -> " + secondMax);

	}

}
