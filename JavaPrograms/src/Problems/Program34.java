package Problems;

public class Program34 {

	public static void main(String[] args) {

		int num = 79;
		int count = 0;

		for (int i = 1; i <= num; i++) {
			if (num % i == 0) {
				count++;
			}

		}

		Boolean isPrime = (count == 2) ? true : false;
		System.out.println("isPrime -> " + isPrime);
		
		long round = Math.round(Math.random()*10000);
		System.out.println(round);

	}

}
