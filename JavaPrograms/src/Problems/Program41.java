package Problems;

import java.util.Arrays;
import java.util.OptionalInt;
import java.util.stream.IntStream;

public class Program41 {

	public static void main(String[] args) {

		int[] arr = { 1, 2, 3, 4, 5, 6 };
		int toFind = 5;

		OptionalInt first = Arrays.stream(arr).filter(x -> x == toFind).findFirst();
		
		int orElse = Arrays.stream(arr).filter(x -> x == toFind).findFirst().orElse(-1);
		
		int index = IntStream.range(0, arr.length).filter(i->arr[i]==toFind).findFirst().orElse(-1);

		System.out.println(first.isPresent());
		System.out.println(orElse);
		System.out.println(index);

	}

}
