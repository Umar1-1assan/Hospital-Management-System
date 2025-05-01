package hospital;

public abstract class Person
{
	protected String name;
	protected String address;
	protected String cnic;
	protected String phoneNumber;
	protected int age;
	protected String gender;
	
	public Person()
	{
		name="";
		address="";
		cnic="";
		phoneNumber="";
		age=-1;
		gender = "";
	}
	
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Person(String nam,String add,String cnic, String phone, int ag, String gen)
	{
		name = nam;
		address = add;
		phoneNumber = phone;
		this.cnic = cnic;
		age = ag;
		gender = gen;
	}
	
	//Getter Setters

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCnic() {
		return cnic;
	}

	public void setCnic(String cnic) {
		this.cnic = cnic;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	
	
}