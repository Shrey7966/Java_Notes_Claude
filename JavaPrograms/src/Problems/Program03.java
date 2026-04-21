package Problems;

import java.util.HashMap;
import java.util.Map;

public class Program03 {

	public static void main(String[] args) {
		
		// how to find duplicate character in string
		String str = "laptop" ;
		
		char[] c = str.toCharArray();
		
		Map<Character,Integer> map = new HashMap();
		
		for(char item:c) {
			if(map.containsKey(item)) {
				map.put(item, map.getOrDefault(item, 0)+1);
			}else {
				map.put(item, 1);
			}
		}
		
		for(Character item:map.keySet()) {
			
			if(map.get(item)>1) {
				System.out.println(item + " "+ map.get(item));
			}
		}
		

	}

}
