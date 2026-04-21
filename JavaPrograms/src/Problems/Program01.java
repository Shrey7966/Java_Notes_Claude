package Problems;

import java.util.HashMap;
import java.util.Map;

public class Program01 {
	
	public static void main(String[] args) {
		
		String str = "I am Learning learning java java progarmming";
		
		HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
		
		String[] splitArr = str.split(" ");
		
		for(String item:splitArr) {
				hashMap.put(item, hashMap.getOrDefault(item, 0)+1);
		}
		
		for(Map.Entry<String, Integer> entry:hashMap.entrySet()) {
			System.out.println(entry.getKey() + " -> " + entry.getValue());
		}
		
	
		
	}

}
