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

public class PatientVisitManager
{
	DoctorQueue doctorQueue;
	LabQueue labQueue;
	
	public PatientVisitManager()
	{
		this.doctorQueue= DoctorQueue.getDoctorQueue();
		this.labQueue= LabQueue.getLabQueue();
	}

	public DoctorQueue getDoctorQueue() {
		return doctorQueue;
	}

	public void setDoctorQueue(DoctorQueue doctorQueue) {
		this.doctorQueue = doctorQueue;
	}

	public LabQueue getLabQueue() {
		return labQueue;
	}

	public void setLabQueue(LabQueue labQueue) {
		this.labQueue = labQueue;
	}

	//Main functions
	public boolean registerPatient(String name,String add,String cnic, String phone, int age, String gender,  String bldgrp) throws ClassNotFoundException, SQLException
	{
		Patient p = new Patient(name,add,cnic,phone,age,gender,bldgrp);
		return PatientDBHandler.getpatientDBHandler().savePatient(p);
	}
	public boolean createDoctorVisit(String pid,String did, String issue, String dateTime) throws ClassNotFoundException, SQLException
	{
		return this.doctorQueue.createDoctorVisit(pid, did, issue, dateTime);
	}
	
	public boolean createLabVisit(String pid,String lid, String testname, String dateTime) throws ClassNotFoundException, SQLException
	{
		return this.labQueue.createLabVisit(pid, lid, testname, dateTime);
	}
	
	public ArrayList<LabVisit> getQueuedLabVisit()
	{
		return this.labQueue.getQueuedLabVisits();
	}
	
	public ArrayList<DoctorVisit> getQueuedDoctorVisit()
	{
		return this.doctorQueue.getQueuedDoctorVisits();
	}
	//Queries
	
	//Doctors
	public ArrayList<Doctor> getDoctors() throws ClassNotFoundException, SQLException
	{
		return DoctorDBHandler.getDoctorDBHandler().getDoctors();
	}
	public ArrayList<Doctor> getDoctor(String docName) throws ClassNotFoundException, SQLException
	{
		return  DoctorDBHandler.getDoctorDBHandler().getDoctorViaName(docName);
	}
	
	//LabTechs
	public ArrayList<LabTechnician> getLabTechnicians() throws ClassNotFoundException, SQLException
	{
		return LabTechnicianDBHandler.getLabTechnicianDBHandler().getLabTechnicians();
	}
	public ArrayList<LabTechnician> getLabTechnician(String name) throws ClassNotFoundException, SQLException
	{
		return LabTechnicianDBHandler.getLabTechnicianDBHandler().getLabTechnicianViaName(name);
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
	
	public Receptionist showProfile(String username) throws ClassNotFoundException, SQLException
	{
		return ReceptionistDBHandler.getReceptionistDBHandler().getReceptionist(username);
	}
	
	public ArrayList<BedAllocation> getBedAllocations() throws ClassNotFoundException, SQLException
	{
		return BedAllocationDBHandler.getBedAllocationDBHandler().getBedAllocations();
	}
	
	public ArrayList<BedAllocation> getBedAllocations(String patName) throws ClassNotFoundException, SQLException
	{
		return BedAllocationDBHandler.getBedAllocationDBHandler().getBedAllocationViaPat(patName);
	}
	
	public BedAllocation getBedAllocation(int id) throws ClassNotFoundException, SQLException
	{
		return BedAllocationDBHandler.getBedAllocationDBHandler().getBedAllocation(id);
	}
	
	public void saveBedAllocation(BedAllocation b) throws ClassNotFoundException, SQLException
	{
		 BedAllocationDBHandler.getBedAllocationDBHandler().saveBedAllocation(b);
	}
		
}