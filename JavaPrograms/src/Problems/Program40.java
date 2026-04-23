package Problems;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Program40 {

	private static Set<Integer> collect;

	public static void main(String[] args) {

		int[] arr = { 1, 2, 3, 4, 4, 5, 6,6,8,9 };

		for (int i = 0; i < arr.length ; i++) {
			for (int j = i+1; j < arr.length ; j++) {
				if (arr[i] == arr[j]) {
					System.out.println(arr[i]);
				}
			}
		}
		
		System.out.println("Using Hashset");
		
		HashSet<Integer> hs = new HashSet<Integer>();
		HashSet<Integer> hs_stream = new HashSet<Integer>();
		
		for(int item: arr) {
			if(!hs.contains(item)) {
				hs.add(item);
			}else {
				System.out.println(item);
			}
		}
		
		System.out.println("Using Streams");
		
		collect = Arrays.stream(arr).filter(x -> !hs_stream.add(x)).boxed().collect(Collectors.toSet());
		
		System.out.println(collect);
		System.out.println("hs_stream "+ hs_stream);

	}

}
