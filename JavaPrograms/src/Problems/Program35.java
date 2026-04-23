package Problems;

import java.util.ArrayList;
import java.util.Arrays;

public class Program35 {

	public static void main(String[] args) {

		int[] arr = { 1, 2, 3, 4, 5, 6 };
		int sum = 0;
		for (int item : arr) {
			sum += item;
		}

		System.out.println("Sum of Array " + sum);

		int sum2 = Arrays.stream(arr).sum();

		System.out.println(sum2);
		
		int sum3 = Arrays.stream(arr).reduce(0, Integer::sum);
		System.out.println(sum3);
		
		
		
	}

}
