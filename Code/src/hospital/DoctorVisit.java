package hospital;

public class DoctorVisit
{
	int visitNo;
	private String dateTime;
	private String medicalIssue;
	private String status;
	private String prescription;
	private Patient patient;
	private Doctor doctor;
	
	public DoctorVisit()
	{
		dateTime="";
		medicalIssue="";
		status="Pending";
		patient = new Patient();
		doctor = new Doctor();
		//Status = Pending, Working, Completed
	}
	public DoctorVisit(int visitNo,String dt, String med, String stat, String presc, Patient patient, Doctor doctor )
	{
		this.visitNo=visitNo;
		this.dateTime = dt;
		this.medicalIssue = med;
		this.status = stat;
		this.prescription = presc;
		this.patient=patient;
		this.doctor=doctor;
	}
	
	//Getter Setters
	
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
	public Doctor getDoctor() {
		return doctor;
	}
	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public String getMedicalIssue() {
		return medicalIssue;
	}
	public void setMedicalIssue(String medicalIssue) {
		this.medicalIssue = medicalIssue;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPrescription() {
		return prescription;
	}
	public void setPrescription(String prescription) {
		this.prescription = prescription;
	}
	
	
	
	
}