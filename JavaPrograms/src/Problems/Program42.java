package Problems;

public class Program42 {

	public static void main(String[] args) {

		int[] arr = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };

		int left = 0;
		int right = arr.length-1; // 10
		int num=1;
		boolean flag = false;

		while (left <= right) { // 0<1
			int mid = (left + right) / 2; // 0
			
			if(arr[mid]==num ) { // 3 false
				System.out.println(num + " has been found !!");
				flag = true;
				break;
			}
			
			if (arr[mid]<num) { // 3<2
				left = mid+1;
			}if (arr[mid]>num)
				right = mid-1; // 1
			}
		
		System.out.println(flag==false ? num + " Not found" : num + "Found");
		}
	

	}


