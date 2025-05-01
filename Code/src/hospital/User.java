package hospital;

public class User extends Person
{
	protected String username;
	protected String password;
	protected Schedule schedule;
	
	
	public User()
	{
		username = "";
		password = "";
		schedule = new Schedule();
	}
	public User(String nam,String add,String cnic, String phone, int ag, String gen, String usernam, String pass, Schedule sch )
	{
		super(nam,add,cnic,phone,ag,gen);
		username=usernam;
		password=pass;
		schedule = sch;
	}
	public User(String username, String password)
	{
		this.username = username;
		this.password = password;
		
	}
	public Schedule getSchedule() {
		return schedule;
	}
	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void print() {
	    System.out.println("Username: " + username);
	    System.out.println("Password: " + password);
	    System.out.println("Name: " + name);
	    System.out.println("Address: " + address);
	    System.out.println("CNIC: " + cnic);
	    System.out.println("Phone Number: " + phoneNumber);
	    System.out.println("Age: " + age);
	    System.out.println("Gender: " + gender);
	}
	
}