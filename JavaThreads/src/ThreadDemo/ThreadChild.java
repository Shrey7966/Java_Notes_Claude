package ThreadDemo;

public class ThreadChild extends Thread{
	
	@Override
	public void run() {
		
		for(int i=0 ;i<=10;i++) {
			System.out.println("inside ChildThreadOne ---> " + 	Thread.currentThread() +"  " + i);
		}
		
	}

}
