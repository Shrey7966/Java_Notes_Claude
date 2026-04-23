package Problems;

public class Program46 {

	public static void main(String[] args) {
	
		String s = "S$$$hrey@@a@$$$S";
		int totalLength = s.length();
		
		int totalLengthAfterRemoval = s.replace("$", "").length();
		
		System.out.println("$" + (totalLength-totalLengthAfterRemoval));

	}

}
