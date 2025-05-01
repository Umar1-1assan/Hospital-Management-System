package hospital;
import java.util.ArrayList;

public class BedAllocation
{
	private int roomNo;
	private Patient patient;
	private ArrayList<Due> dues;
	private int bedAllocationNumber;
	private int bedNo;
	private String status;
	
	
	public BedAllocation()
	{
		roomNo=0;
		patient = new Patient();
		dues = new ArrayList<>();
		this.bedAllocationNumber = -1;
		this.bedNo=-1;
		this.status = "Valid";
	}
	
	public BedAllocation(int roomNo, Patient patient, ArrayList<Due>dues,int bedAllocNo, String status)
	{
		this.roomNo=roomNo;
		this.patient=patient;
		this.dues=dues;
		this.bedAllocationNumber=bedAllocNo;
		this.status=status;
	}
	
	public boolean addDue(String desc, float price, String dateTime)
	{
		int dueNo = 1;
		for(int i=0; i<this.dues.size();i++)
		{
			if(dues.get(i).getDueNumber()>=dueNo)
			{
				dueNo=dues.get(i).getDueNumber()+1;
			}
		}
		this.dues.add(new Due(desc,price,"Pending",dateTime,dueNo));
		return true;
	}
	
	public boolean payDue(int dueNo)
	{
		for(Due d : this.dues)
		{
			if(d.getDueNumber()==dueNo)
			{
				d.setStatus("Paid");
				return true;
			}
		}
		return false;
	}
	public String getPatientCnic()
	{
		return this.patient.getCnic();
	}
	public int getRoomNo()
	{
		return this.roomNo;
	}
	
	//Getter Setter

	public void setRoomNo(int roomNo) {
		this.roomNo = roomNo;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public ArrayList<Due> getDues() {
		return dues;
	}

	public void setDues(ArrayList<Due> dues) {
		this.dues = dues;
	}

	public int getBedAllocationNumber() {
		return bedAllocationNumber;
	}

	public void setBedAllocationNumber(int bedAllocationNumber) {
		this.bedAllocationNumber = bedAllocationNumber;
	}

	public int getBedNo() {
		return bedNo;
	}

	public void setBedNo(int bedNo) {
		this.bedNo = bedNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}