package hospital;

public class Patient extends Person
{
	private String bloodGroup;
	//Add schedule
	
	public Patient()
	{
		bloodGroup="";
	}
	public Patient(String nam,String add,String cnic, String phone, int ag, String gen,  String bldgrp)
	{
		super(nam,add,cnic,phone,ag,gen);
		this.bloodGroup = bldgrp;
	}

	//Getter Setters
	public String getBloodGroup() {
		return bloodGroup;
	}
	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}
	
	
}