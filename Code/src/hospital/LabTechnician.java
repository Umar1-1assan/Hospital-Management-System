package hospital;

public class LabTechnician extends User
{
	private int workExperience;

	public LabTechnician()
	{
		this.workExperience = -1;
	}
	public LabTechnician(String nam,String add,String cnic, String phone, int ag, Schedule sch, String gen,  int exp, String usernam, String pass)
	{
		super(nam,add,cnic,phone,ag,gen,usernam,pass,sch);
		this.workExperience = exp;
	}
	
	public int getWorkExperience() {
		return workExperience;
	}
	public void setWorkExperience(int workExperience) {
		this.workExperience = workExperience;
	}
	
	
	

	
}