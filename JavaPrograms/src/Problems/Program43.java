package Problems;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Program43 {

	public static void main(String[] args) {

		int[] arr = { 7, 5, 9, 3, 2 };

		for (int i = 0; i < arr.length - 1; i++) {

			for (int j = 0; j < arr.length - 1; j++) {

				if (arr[j] > arr[j + 1]) {
					int temp = arr[j];
					arr[j] = arr[j + 1];
					arr[j + 1] = temp;

				}

			}

		}

		for (int i : arr) {
			System.out.println(i);
		}

		int[] sorted = Arrays.stream(arr).sorted().toArray();

		for (int i : sorted) {
			System.out.println(i);
		}

	}

}
