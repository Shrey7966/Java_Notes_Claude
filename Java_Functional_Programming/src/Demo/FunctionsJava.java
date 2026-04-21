package Demo;

import java.util.ArrayList;
import java.util.function.Function;
import java.util.function.Predicate;

public class FunctionsJava {
	
	public static void main(String[] args) {
		Function<Integer, Integer> f = n-> n*n;
		System.out.println(f.apply(6));
		
		
		Function<String, Integer> f1 = n->n.length();
		
		System.out.println(f1.apply("Shreyas"));
		
		
		ArrayList<Employee> al = new ArrayList<Employee>();

		al.add(new Employee("Shreyas", 50000, 4));
		al.add(new Employee("Khushi", 70000, 5));
		al.add(new Employee("Brun", 150000, 3));
		al.add(new Employee("SGKS", 650000, 1));
		
		
		Function<Integer, Integer> f2 = s->{
			Integer bonus;
			
			if(s <= 50000) {
				bonus = s*10/100;
				return bonus;
			} else if (s <=100000) {
				bonus = s*20/100;
				return bonus;
			}else if (s <=150000) {
				bonus = s*30/100;
				return bonus;
			}else {
				bonus = s*40/100;
				return bonus;
			}
			
		};
		
		Predicate<Integer> p = b->b>10000;
		
		
		for(Employee item: al)
		{
			Integer bonus = f2.apply(item.salary);
			
			if(p.test(bonus)) {
				System.out.println(item.toString() + " bonus -> "+ bonus);
			}

			
		}
		
		
	}
	

}
