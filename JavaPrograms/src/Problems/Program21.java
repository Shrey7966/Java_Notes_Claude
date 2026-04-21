package Problems;

public class Program21 {

	public static void main(String[] args) {

		int num = 10;

		System.out.println(factorial(num));

	}

	public static int factorial(int num) {
		if (num <= 1)
			return 1;
		else
			return num * factorial(num-1);

	}

}
