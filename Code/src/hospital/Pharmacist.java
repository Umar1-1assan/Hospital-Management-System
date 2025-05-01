package hospital;

public class Pharmacist extends User
{
	private int workExperience;

	public Pharmacist()
	{
		this.workExperience = -1;
	}
	public Pharmacist(String nam,String add,String cnic, String phone, int ag, Schedule sch, String gen,  int exp, String usernam, String pass)
	{
		super(nam,add,cnic,phone,ag,gen,usernam,pass, sch);
		this.workExperience = exp;
	}
	
	public int getWorkExperience() {
		return workExperience;
	}
	public void setWorkExperience(int workExperience) {
		this.workExperience = workExperience;
	}
		
}