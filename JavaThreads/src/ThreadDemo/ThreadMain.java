package ThreadDemo;

public class ThreadMain {

	public static void main(String[] args) {
		
		System.out.println("Main Thread Started");
		ThreadChild threadChild = new ThreadChild();
		threadChild.start();
		
		Thread thread = new Thread(new ChildThreadTwo());
		thread.start();
		System.out.println("Main Thread Ended");

	}

}
