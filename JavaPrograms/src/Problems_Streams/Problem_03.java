package Problems_Streams;

import java.util.Arrays;
import java.util.Comparator;

public class Problem_03 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String s = "Hello Shreyas how are you ?";
		
		String string = Arrays.stream(s.split(" ")).sorted(Comparator.comparing(String::length).reversed()).skip(1).findFirst().get();

		System.out.println(string);
	}

}
