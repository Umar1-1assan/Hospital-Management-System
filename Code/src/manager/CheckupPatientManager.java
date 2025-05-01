package manager;
//import hospital.User;
//import hospital.DoctorQueue;
//import hospital.LabQueue;
//import hospital.Patient;
import hospital.*;
import dbhandler.*;

import java.sql.SQLException;
import java.util.ArrayList;
//import dbhandler.PatientDBHandler;

public class CheckupPatientManager
{
	private DoctorQueue doctorQueue;
	private DoctorVisit doctorVisit;
	
	public CheckupPatientManager()
	{
		this.doctorQueue= DoctorQueue.getDoctorQueue();
	}
	
	public ArrayList<DoctorVisit> getQueuedDoctorVisits()
	{
		return this.doctorQueue.getQueuedDoctorVisits();
	}
	
	public DoctorVisit startCheckUp(int visitNo)
	{
		 this.doctorVisit =  this.doctorQueue.getVisit(visitNo);
		 doctorVisit.setStatus("Working");
		 return this.doctorVisit;
	}
	
	public ArrayList<DoctorVisit>getPatientVisitHistory() throws ClassNotFoundException, SQLException
	{
		String nic = this.doctorVisit.getPatient().getCnic();
		return DoctorVisitDBHandler.getDoctorVisitDBHandler().getVisitHistory(nic);	
	}
	
	public DoctorVisit openVisit(int visitNo) throws ClassNotFoundException, SQLException
	{
		return DoctorVisitDBHandler.getDoctorVisitDBHandler().getVisit(visitNo);
	}
	
	public void endCheckup(String presc) throws ClassNotFoundException, SQLException
	{
		this.doctorVisit.setPrescription(presc);
		this.doctorVisit.setStatus("Completed");
		DoctorVisitDBHandler.getDoctorVisitDBHandler().saveVisit(doctorVisit);
		this.doctorQueue.removeVisit(this.doctorVisit.getVisitNo());
		this.doctorVisit=null;
	}
	
	public boolean generateAdmissionRequest(String doctorId, String patientId, String dateTime) throws ClassNotFoundException, SQLException
	{
		if(AdmissionRequestDBHandler.getAdmissionRequestDBHandler().isPendingRequest(patientId))
		{
			return false;
		}
		
		if(BedAllocationDBHandler.getBedAllocationDBHandler().isPendingBedAllocation(patientId))
		{
			return false;
		}
		
		Patient p = PatientDBHandler.getpatientDBHandler().getPatient(patientId);
		Doctor d = DoctorDBHandler.getDoctorDBHandler().getDoctor(doctorId);
		int reqNo = AdmissionRequestDBHandler.getAdmissionRequestDBHandler().getMaxAdmissionRequestNumber()+1;
		AdmissionRequest admReq = new AdmissionRequest(dateTime, d, p, new String("Pending"), reqNo);
		AdmissionRequestDBHandler.getAdmissionRequestDBHandler().saveAdmissionRequest(admReq);
		return true;
	}
	
	public DoctorQueue getDoctorQueue() {
		return doctorQueue;
	}

	public void setDoctorQueue(DoctorQueue doctorQueue) {
		this.doctorQueue = doctorQueue;
	}

	public DoctorVisit getDoctorVisit() {
		return doctorVisit;
	}

	public void setDoctorVisit(DoctorVisit doctorVisit) {
		this.doctorVisit = doctorVisit;
	}

	public boolean generateDischargeRequest(int bedAllocID,String patientId, String dateTime) throws ClassNotFoundException, SQLException
	{
		if(DischargeRequestDBHandler.getDischargeRequestDBHandler().isPendingRequest(bedAllocID))
		{
			return false;
		}
		BedAllocation b = BedAllocationDBHandler.getBedAllocationDBHandler().getBedAllocation(bedAllocID);
		int reqNo = DischargeRequestDBHandler.getDischargeRequestDBHandler().getMaxDischargeRequestNumber()+1;
		DischargeRequest admReq = new DischargeRequest(dateTime, b,new String("Pending"), reqNo);
		DischargeRequestDBHandler.getDischargeRequestDBHandler().saveDischargeRequest(admReq);
		return true;
	}
	
	//Patients
	public ArrayList<Patient> getPatients() throws ClassNotFoundException, SQLException
	{
		return PatientDBHandler.getpatientDBHandler().getPatients();
	}
	public ArrayList<Patient> getPatient(String name) throws ClassNotFoundException, SQLException
	{
		return PatientDBHandler.getpatientDBHandler().getPatientViaName(name);
	}


	public Doctor showProfile(String username) throws ClassNotFoundException, SQLException
	{
		return DoctorDBHandler.getDoctorDBHandler().getDoctor(username);
	}
	
	public DoctorVisit getDoctorVisitHistory(int visitNo) throws ClassNotFoundException, SQLException
	{
		return DoctorVisitDBHandler.getDoctorVisitDBHandler().getVisit(visitNo);
	}
	
	public ArrayList<BedAllocation> getBedAllocations() throws ClassNotFoundException, SQLException
	{
		return BedAllocationDBHandler.getBedAllocationDBHandler().getBedAllocations();
	}
	
	public ArrayList<BedAllocation> getBedAllocations(String patName) throws ClassNotFoundException, SQLException
	{
		return BedAllocationDBHandler.getBedAllocationDBHandler().getBedAllocationViaPat(patName);
	}
	
}
