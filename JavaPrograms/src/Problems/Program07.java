package Problems;

public class Program07 {

	public static void main(String[] args) {
		
		// remove last 4 character from String
		
		String str = " www.shreyas.com";
		
		String substring = str.substring(str.length()-4, str.length());
		
		System.out.println(substring);

	}
}
