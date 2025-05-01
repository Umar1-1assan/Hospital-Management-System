package hospital;

public class LabVisit
{
	int visitNo;
	private String dateTime;
	private String testName;
	private String report;
	private String status;	//Pending Working Completed
	private int sampleNumber;
	private Patient patient;
	private LabTechnician labTechnician;
	

	public LabVisit()
	{
		dateTime="";
		testName="";
		report="";
		sampleNumber=0;
		status="Pending";
		patient = new Patient();
		labTechnician = new LabTechnician();
	}
	public LabVisit(int vNo, String dateTime, String testName, String report, String status, int sampleNumber, Patient patient, LabTechnician labTech )
	{
		this.visitNo=vNo;
		this.dateTime=dateTime;
		this.testName=testName;
		this.report=report;
		this.sampleNumber=sampleNumber;
		this.status=status;
		this.patient=patient;
		this.labTechnician=labTech;
	}
	
	public int getVisitNo() {
		return visitNo;
	}
	public void setVisitNo(int visitNo) {
		this.visitNo = visitNo;
	}
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	public LabTechnician getLabTechnician() {
		return labTechnician;
	}
	public void setLabTechnician(LabTechnician labTechnician) {
		this.labTechnician = labTechnician;
	}
	
	
	//Getter Setters
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public String getTestName() {
		return testName;
	}
	public void setTestName(String testName) {
		this.testName = testName;
	}
	public String getReport() {
		return report;
	}
	public void setReport(String report) {
		this.report = report;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getSampleNumber() {
		return sampleNumber;
	}
	public void setSampleNumber(int sampleNumber) {
		this.sampleNumber = sampleNumber;
	}

	
	
	
	
	
}