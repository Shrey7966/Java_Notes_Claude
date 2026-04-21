package Demo;

import java.util.function.Predicate;

public class test {

	public static void main(String[] args) {

		/*
		 * Cab uber = new Uber(); uber.bookCab();
		 */

		Cab c = (a, b) -> "Booked";
		System.out.println(c.bookCab("blr", "dfw"));

		Predicate<Integer> p = (a) -> a % 2 == 0;

		System.out.println(p.test(1));

		Predicate<String> p1 = l -> l.length() > 4;

		System.out.println(p1.test("Shreyas"));

		String[] name = { "Shreyas", "Khushi", "Bruno" };
		Predicate<String> n = t -> (t.length() > 4);

		for (String item : name) {

			if (n.test(item))

				System.out.println(item);

		}

	}

}
