package hospital;

public class Nurse extends User
{
	private int workExperience;
	private int roomAssigned;

	public Nurse()
	{
		this.workExperience = -1;
		roomAssigned = 0;
	}
	public Nurse(String nam,String add,String cnic, String phone, int ag, Schedule sch, String gen,  int exp, String usernam, String pass, int room)
	{
		super(nam,add,cnic,phone,ag,gen,usernam,pass, sch);
		this.workExperience = exp;
		this.roomAssigned = room;
	}
	
	public int getWorkExperience() {
		return workExperience;
	}
	public void setWorkExperience(int workExperience) {
		this.workExperience = workExperience;
	}
	public int getRoomAssigned() {
		return roomAssigned;
	}
	public void setRoomAssigned(int roomAssigned) {
		this.roomAssigned = roomAssigned;
	}
	
	
	

	
}