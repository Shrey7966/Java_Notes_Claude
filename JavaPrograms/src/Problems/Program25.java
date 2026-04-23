package Problems;

public class Program25 {

	public static void main(String[] args) {

		// reverse a number

		int num = 123450;
		int reverse = 0;

		while (num > 0) {
			reverse = num % 10 + reverse * 10;
			num = num / 10;
		}

		System.out.println(reverse);

		num = 123450;

		StringBuffer sb = new StringBuffer(String.valueOf(num));
		StringBuilder stringBuilder = new StringBuilder(String.valueOf(num));

		System.out.println("sb.reverse -> " + sb.reverse());
		System.out.println("sb.reverse -> " + stringBuilder.reverse());

	}

}
