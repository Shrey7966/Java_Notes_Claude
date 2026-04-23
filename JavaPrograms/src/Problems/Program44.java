package Problems;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Program44 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s = "S$$$hrey@@a@$$$S";

		String result = s.chars().filter(Character::isLetter) // keep only letters
				.mapToObj(c -> String.valueOf((char) c)).collect(Collectors.joining());

		System.out.println(result); // SShreya

		String s1 = "S$$$hrey@@a@$$$S";
		
		s1 = s1.replaceAll("[^a-zA-Z0-9]", "");
		System.out.println(s1);
		
		
	}

}
