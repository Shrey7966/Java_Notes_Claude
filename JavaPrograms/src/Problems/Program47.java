package Problems;

public class Program47 {

	public static void main(String[] args) {
	
		String s = "Hello Shreyas how are you ?";
		
		String reverse = "";
		
		String[] split = s.split(" ");
		
		for(String words : split) {
			
			for(int i = words.length()-1; i>=0 ;i--) {
				reverse = reverse + words.charAt(i);
			}
			
			reverse = reverse + " ";
			
		}
		System.out.println(reverse);
		
		

	}

}
