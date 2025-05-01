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

public class AdmitPatientManager
{
	public AdmitPatientManager()
	{
		
	}
	public ArrayList<Room> getRooms() throws ClassNotFoundException, SQLException
	{
		return RoomDBHandler.getRoomDBHandler().getAvailableBeds();
	}
	
	public ArrayList<Room> getAvailableBeds() throws ClassNotFoundException, SQLException
	{
		return RoomDBHandler.getRoomDBHandler().getAvailableBeds();
	}
	
	public ArrayList<AdmissionRequest> getAdmissionRequests() throws ClassNotFoundException, SQLException
	{
		return AdmissionRequestDBHandler.getAdmissionRequestDBHandler().getAdmissionRequests();
	}
	
	public ArrayList<AdmissionRequest> getAdmissionRequest(String patName) throws ClassNotFoundException, SQLException
	{
		return AdmissionRequestDBHandler.getAdmissionRequestDBHandler().getAdmissionRequest(patName);
	}
	
	public BedAllocation admitPatient(int reqNo, int roomNo, int bedNo, String dateTime) throws ClassNotFoundException, SQLException
	{
		Patient p = PatientDBHandler.getpatientDBHandler().getPatientViaAdmNo(reqNo);
		int allocNo = BedAllocationDBHandler.getBedAllocationDBHandler().getMaxBedAllocationNumber()+1;
		float price = RoomDBHandler.getRoomDBHandler().getBedPrice(roomNo, bedNo);
		
		if(price==-1)
		{
			return null;
		}
		
		ArrayList<Due> list = new ArrayList<>();
		String status = "Valid";
		
		BedAllocation b = new BedAllocation(roomNo,p,list, allocNo,status);
		b.setBedNo(bedNo);
		b.addDue(new String("Bed Charges"), price, dateTime);
		
		RoomDBHandler.getRoomDBHandler().setBedAvailable(roomNo, bedNo, false);
		
		BedAllocationDBHandler.getBedAllocationDBHandler().saveBedAllocation(b);
		
		AdmissionRequestDBHandler.getAdmissionRequestDBHandler().markCompleted(reqNo);
		return b;
	}
}