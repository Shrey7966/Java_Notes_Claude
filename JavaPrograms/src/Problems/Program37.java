package Problems;

public class Program37 {

	public static void main(String[] args) {
		// check 2 arrays are equal

		int[] arr1 = { 1, 2, 3, 4 };
		int[] arr2 = { 1, 2, 3, 9 };
		boolean flag = true;

		if (arr1.length == arr2.length) {
			
			for(int i = 0 ;i<arr1.length;i++) {
				
				if(arr1[i]!=arr2[i]) {
					flag = false;
				}
			}
		} else {
			System.out.println("Arrays are not equal");
		}
		System.out.println(flag);

		//System.out.println(arr1.equals(arr2));
	}

}
