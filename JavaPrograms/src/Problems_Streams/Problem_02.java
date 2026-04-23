package Problems_Streams;

import java.util.Arrays;

public class Problem_02 {
	
	public static void main(String[] args) {
		
		String s = "abbabxbabc";
		
		s.chars().distinct().mapToObj(x->(char)x).forEach(System.out::print);
		
		
	}

}
