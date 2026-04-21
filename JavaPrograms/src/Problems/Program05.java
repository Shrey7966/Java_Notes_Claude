package Problems;

import java.util.LinkedHashMap;
import java.util.Map;

public class Program05 {
	
	public static void main(String[] args) {
		
		// first non repeated character
		
		String str = "shreyas";
		
		char[] charArray = str.toLowerCase().toCharArray();
				
		Map<Character,Integer> map = new LinkedHashMap<>();
		
		for(Character c : charArray) {
			
			map.put(c, map.getOrDefault(c, 0) +1);
			
		}
		System.out.println(map);
		
		for(Map.Entry<Character,Integer> entry : map.entrySet()) {
			if(entry.getValue() ==1) {
				System.out.println("First non repeated character is + " + entry.getKey());
				break;
			}
		}
		
	}

}
