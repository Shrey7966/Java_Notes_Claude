package Problems_Streams;

import java.util.Arrays;
import java.util.Comparator;

public class Problem_01 {

	public static void main(String[] args) {

		String s = "Hello Shreyas how are you ?";

		String maxLength = Arrays.stream(s.split(" ")).max(Comparator.comparing(String::length)).get();

		System.out.println(maxLength);

	}

}
