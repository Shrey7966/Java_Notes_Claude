package Problems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Program02 {

	private static List<char[]> asList;

	public static void main(String[] args) {
		
		
		String str = "Java is object oriented language";
		
		// count character occurance => a
		Map<Character,Integer> map = new HashMap<>();
		
		char[] charArray = str.toCharArray();
		int count = 0;
		
		for(char item : charArray) {
			if(item == 'a') {
				count++;
			}
			
			if(map.containsKey(item)) {
				map.put(item, map.getOrDefault(item, 0)+1);
			}else {
				map.put(item, 1);
			}
		}
		System.out.println(count);
		
		for( Map.Entry<Character, Integer> entry:map.entrySet()) {
			
			System.out.println(entry.getKey() + "-----" + entry.getValue());
		}
		
		System.out.println(str.toLowerCase());
		
		long count_a = str.toLowerCase().chars().filter(x -> x == 'a').count();
		
		System.out.println(count_a);
		
		Map<Character, Long> collect = str
		.toLowerCase()
		.chars()
		.filter(ch -> ch != ' ')
		.mapToObj(ch -> (char) ch)
		.collect(Collectors.groupingBy(
				ch-> ch, Collectors.counting()
				));
		
		System.out.println(collect);
		
		
		

	}
	
	

}
