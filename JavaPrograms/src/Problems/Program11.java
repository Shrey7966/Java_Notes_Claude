package Problems;

public class Program11 {

	public static void main(String[] args) {

		// largest and smallest element of array

		int[] arr = { 99, 87, 65, 36, 17, 108, 76 };

		int max = arr[0];
		int min = arr[0];

		for (int i : arr) {
			if (i > max) {
				max = i;
			}
			if (i < min) {
				min = i;
			}
		}

		System.out.println("max -> " + max);
		System.out.println("min -> " + min);
	}

}
