package hospital;

public class AdmissionRequest
{
	private String dateTime;
	private Doctor doctor;
	private Patient patient;
	private String status;
	int requestNumber;
	
	public AdmissionRequest()
	{
		dateTime = null;
		doctor = new Doctor();
		patient = new Patient();
		status="Pending";
		this.requestNumber=0;
	}
	
	public AdmissionRequest(String dateTime, Doctor d, Patient p, String status, int reqNo)
	{
		this.dateTime = dateTime;
		this.doctor = d;
		this.patient= p;
		this.status = status;
		this.requestNumber = reqNo;
	}
	
	
	public int getRequestNumber() {
		return requestNumber;
	}

	public void setRequestNumber(int requestNumber) {
		this.requestNumber = requestNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	//Getter Setters
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public Doctor getDoctor() {
		return doctor;
	}
	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	
	
	
}