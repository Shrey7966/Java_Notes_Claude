package Demo;

public class Employee {

	String ename;
	int salary;
	int experience;

	public Employee(String ename, int salary, int experience) {
		super();
		this.ename = ename;
		this.salary = salary;
		this.experience = experience;
	}

	@Override
	public String toString() {
		return "Employee [ename=" + ename + ", salary=" + salary + ", experience=" + experience + "]";
	}
	
	

}
