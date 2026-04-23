package Problems;

import java.util.Arrays;

public class Program36 {

	public static void main(String[] args) {

		int[] arr = { 1, 2, 3, 5, 6, 7 };

		for (int item : arr) {
			System.out.println((item & 1) == 0 ? "Even " + item : "Odd " + item);
		}
		
		System.out.println("Streams : ");
		
		Arrays.stream(arr).filter(x-> ((x&1)==0)).forEach(x->System.out.println(x+ " Even"));
		Arrays.stream(arr).filter(x-> ((x&1)==1)).forEach(x->System.out.println(x+ " Odd"));

	}

}
