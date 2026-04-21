package Problems;

import java.util.LinkedHashSet;
import java.util.Set;

public class Program17 {

	public static void main(String[] args) {

		String str = "I am am Shreyas Shreyas";
		String corrected_Str = "";

		String[] split = str.split(" ");
		Set<String> LinkedhashSet = new LinkedHashSet<String>();

		for (String s : split) {

			if (!(LinkedhashSet.contains(s))) {
				LinkedhashSet.add(s);

			}
		}

		for (String word : LinkedhashSet) {
			corrected_Str = corrected_Str.concat(word).concat(" ");
		}
		
		System.out.println(corrected_Str);
	}

}
