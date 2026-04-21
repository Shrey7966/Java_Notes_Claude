package Problems;

public class Program06 {

	public static void main(String[] args) {
		
		// String Reverse
		
		String str = "shreyas";
		
		StringBuffer stringBuffer = new StringBuffer(str);
		StringBuffer reverse_str = stringBuffer.reverse();
		
		System.out.println("Solution-1 -----> " + reverse_str);
		
		System.out.print("Solution-2 -----> ");
		char[] charArray = str.toCharArray();
		
		for(int i = charArray.length-1; i>=0;i--) {
			System.out.print(charArray[i]);
		}
		
	}

}
