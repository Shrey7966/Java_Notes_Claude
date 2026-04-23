package Problems;

public class Program48 {

	public static void main(String[] args) {

		int x = 1;

		do {
			System.out.println("Retry Attempt for UPI ID : " + x);

			// if logic fails
			x++;

		} while (x <= 3);

		int i = 10;

		switch (i) {
		case 1:
			System.out.println(1);
			break;
		case 2:
			System.out.println(2);
			break;
		default:
			System.out.println(10);
		}

	}

}
