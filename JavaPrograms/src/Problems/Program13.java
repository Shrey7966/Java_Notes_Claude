package Problems;

public class Program13 {

	public static void main(String[] args) {
		// Missing number from integer array.
		int[] num = { 1, 2, 3, 5, 4, 7, 8, 9 };
		
		int n = num.length+1;
		
		int ex_sum = n*(n+1)/2;
		int ac_sum = 0;
		
		System.out.println(ex_sum);
		
		for(int i : num) {
			ac_sum+=i;
		}
		System.out.println(ac_sum);
		
		System.out.println("Missing number -> "+ (ex_sum - ac_sum));

		

	}

}
