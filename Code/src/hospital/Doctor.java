package hospital;

public class Doctor extends User
{
	private String speciality;
	//Add schedule
	
	public Doctor()
	{
		speciality="";
	}
	public Doctor(String nam,String add,String cnic, String phone, int ag, Schedule sch, String gen,  String spec, String usernam, String pass)
	{
		super(nam,add,cnic,phone,ag,gen,usernam,pass, sch);
		speciality = spec;
	}
	
	//Getter Setters
	public String getSpeciality() {
		return speciality;
	}
	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}
	
}