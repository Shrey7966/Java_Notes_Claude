package Demo;

import java.util.ArrayList;
import java.util.function.Predicate;

public class Demo2 {

	public static void main(String[] args) {

		ArrayList<Employee> al = new ArrayList<Employee>();

		al.add(new Employee("Shreyas", 50000, 4));
		al.add(new Employee("Khushi", 70000, 5));
		al.add(new Employee("Brun", 150000, 3));
		al.add(new Employee("SGKS", 650000, 1));

		Predicate<Employee> p = e -> (e.salary > 20000 && e.experience > 2);

		// System.out.println(p.test(e1));

		for (Employee e : al) {
			if (p.test(e)) {
				System.out.println(e.toString());
				;
			}
		}
		
		
		int[] a = {5,10,15,20,25,30,35,40,45,50,55,60};
		
		Predicate<Integer> p1 = i ->i%2==0;
		Predicate<Integer> p2 = i-> i>50;
		
		
		for(int item:a) {
			if(p1.or(p2).test(item)) {
				System.out.println(item);
			}
			
		}

	}

}
