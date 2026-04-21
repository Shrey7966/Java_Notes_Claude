package Problems;

public class Program19 {

	public static void main(String[] args) {
		
		
		//Fibbinocc series
		
		// 0 1 1 2 3 4 8 13 21
		
		int first = 0;
		int second =1;
		int n =10;
		
		for (int i=1;i<=n;i++) {
			System.out.print(first + " "); // 0  1  1 3
			int third = first+second; //1 1+1 3 5
			first = second; //1 1 2 3
			second = third; //1 2 3 5
			
		}
		
		
		
		
		

	}

}
