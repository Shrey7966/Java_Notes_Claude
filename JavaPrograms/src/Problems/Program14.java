package Problems;

import java.util.HashSet;

public class Program14 {

	public static void main(String[] args) {

		// find duplicate elements in array

		int[] arr = { 1, 2, 3, 3, 4, 5, 5, 7, 8, 8, 9 };

		HashSet<Integer> hs = new HashSet<Integer>();

		for (int i : arr) {
			if (!(hs.contains(i))) {
				hs.add(i);
			} else {
				System.out.println(i);
			}
		}

	}

}
