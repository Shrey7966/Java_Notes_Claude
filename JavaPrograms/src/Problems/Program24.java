package Problems;

public class Program24 {

	public static void main(String[] args) {
		// Swappping of variables

		// Without temp variable

		int a = 10;
		int b = 20;

		int temp;

		System.out.println("Before swapping -> " + "a " + a + ", b " + b);

		temp = a;
		a = b;
		b = temp;

		System.out.println("After swapping -> " + "a " + a + ", b " + b);

		System.out.println("Without Temp Variable");
		a = 10;
		b = 20;
		a = a + b; // 30
		b = a - b; // 10
		a = a - b; // 20

		System.out.println("After swapping -> " + "a " + a + ", b " + b);

		a = 10;
		b = 20;

		a = a * b; // 200
		b = a / b; // 10
		a = a / b; // 20

		System.out.println("After swapping -> " + "a " + a + ", b " + b);

		a = a ^ b;
		b = a ^ b;
		a = a ^ b;

		System.out.println("After swapping -> " + "a " + a + ", b " + b);

		b = a + b - (a = b);
		System.out.println("After swapping -> " + "a " + a + ", b " + b);

	}

}
