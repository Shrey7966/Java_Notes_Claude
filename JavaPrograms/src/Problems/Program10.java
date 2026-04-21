package Problems;

public class Program10 {

	public static void main(String[] args) {

		// REVERSE EVERY WORD IN THE SENTENSE

		// String str = "How are you";
		// woH era uoy
		String str = "Hi Shreyas ";

		String revStr = "";

		String[] split = str.split(" ");

		for (String s : split) {
			for (int i = s.length() - 1; i >= 0; i--) {
				System.out.print(s.charAt(i));
			}
			System.out.print(" ");
		}

	}
}
