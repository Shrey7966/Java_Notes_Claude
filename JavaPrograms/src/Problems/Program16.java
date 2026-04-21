package Problems;

import java.util.Arrays;

public class Program16 {

	public static void main(String[] args) {
		
		int[] num = { 2,5,4,3,7,1,8,9,5};
		
		Arrays.sort(num);
	for(int i = 0;i<num.length-1;i++) {
		if(num[i] == num[i+1]) {
			System.out.println("Duplicate  "+ num[i]);
		}
	}
	}

}
