package Demo;

import java.util.function.Consumer;

public class ConsumerJava {
	
	public static void main(String[] args) {
		
		Consumer<String> c = s-> System.out.print(s);
		
		c.accept("Shreyas");
		
	}

}
