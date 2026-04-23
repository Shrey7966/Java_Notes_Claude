package Problems;

import java.util.Arrays;
import java.util.Collections;
import java.util.OptionalInt;
import java.util.stream.IntStream;

public class Program39 {
	
	public static void main(String[] args) {
		
		int arr[] = {1,2,23,4,5};
		
		System.out.println(Arrays.stream(arr).max().getAsInt());	
		
		
		int max = arr[0];
		int min = arr[0];
		
		for(int i :arr){
			if(i>max) {
				max = i;
			}else if(i<min) {
				min=i;
			}
		}
		
		System.out.println("max -> "+ max);
		System.out.println("min -> "+ min);
		
		
	}

}
