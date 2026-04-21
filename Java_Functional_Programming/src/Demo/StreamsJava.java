package Demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StreamsJava {

	public static void main(String[] args) {

		List<Integer> asList = Arrays.asList(1, 2, 3, 4, 5);

		// asList.stream().filter(x->x%2==0).forEach(e->System.out.println(e));

		// asList.stream().map(x->x+5).forEach(e->System.out.println(e));

		// List<Integer> collect =
		// asList.stream().map(x->x+5).collect(Collectors.toList());

		// System.out.println(collect);

		// asList.stream().filter(x->x%2==0).forEach(System.out::println);

		List<String> names = Arrays.asList("ShreyasGangadhar", "KhushiSrinivas", "ShruthiDilip", "Gahan", "Bruno");

		// names.stream().filter(x->x.length() < 7 && x.length()
		// >4).forEach(System.out::println);

		List<Employee> al = new ArrayList<Employee>();
		al.add(new Employee("Shreyas", 50000, 4));
		al.add(new Employee("Khushi", 70000, 5));
		al.add(new Employee("Brun", 150000, 3));
		al.add(new Employee("SGKS", 650000, 1));

		al.stream().filter(x -> x.experience > 2).forEach(n -> System.out.println(n.ename));

		al.stream().map(e -> {

			int bonus;

			if (e.salary <= 50000) {
				bonus = e.salary * 25 / 100;

			} else if (e.salary <= 60000 && e.salary > 50000) {
				bonus = e.salary * 15 / 100;

			} else if (e.salary <= 80000 && e.salary > 600000) {
				bonus = e.salary * 5 / 100;

			} else {
				bonus = e.salary * 3 / 100;

			}
			return e.toString() + " Bonus: " + bonus;

		}).forEach(System.out::println);

		names.stream().map(x -> {
			int len = x.length();
			return x + "----length----" + len;
		}).forEach(System.out::println);

		
		long count = names.stream().distinct().count();
		
		System.out.println(count);
		
		
		Integer min = asList.stream().min((v1,v2)->{
			
			return v1.compareTo(v2);
		}).get();
		
		
		System.out.println("min -> "+ min);
		
		 Integer val = asList.stream().reduce((value,accumulator)->{
			return value*accumulator;
		}).get();
		
		 System.out.println(val);
		
	}
}
