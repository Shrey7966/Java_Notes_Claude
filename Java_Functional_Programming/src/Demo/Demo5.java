package Demo;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Demo5 {

	public static void main(String[] args) {
		
		
		List<Integer> asList = Arrays.asList(2,4,3,1,5,4,6,7,8,9);
		
		List<Integer> list1 = asList.stream().sorted(Comparator.reverseOrder()).distinct().collect(Collectors.toList());
		
		System.out.println(list1);
		
		List<String> list2 = Arrays.asList("A","B","C");
		
		List<String> list3 = list2.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
		
		System.out.println(list3);
		
		 Set<String> set1 = list2.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toSet());
		
		System.out.println(set1);
	}

}
