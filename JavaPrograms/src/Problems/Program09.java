package Problems;

public class Program09 {
	
	public static void main(String[] args) {
		
		// reverse sentence in java
		
		String str = "We are learning Java";
		
		String[] str_arr = str.split(" ");
		
		for(int i = str_arr.length-1; i>=0;i--) {
			System.out.print(str_arr[i] + " ");
		}
		
	}

}
