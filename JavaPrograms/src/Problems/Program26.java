package Problems;

public class Program26 {

	public static void main(String[] args) {

		// reverse String

		String s = "Shreyas";

		char[] charArray = s.toCharArray();

		for (int i = charArray.length - 1; i >= 0; i--) {
			System.out.print(s.charAt(i));
		}

	}

}
