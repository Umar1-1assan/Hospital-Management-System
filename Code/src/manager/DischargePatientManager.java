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

public class DischargePatientManager
{
	private BedAllocation bedAllocation;
	
	public DischargePatientManager()
	{
		bedAllocation=null;
	}
	
	public void openBedAllocation(int allocNo) throws ClassNotFoundException, SQLException
	{
		this.bedAllocation =  BedAllocationDBHandler.getBedAllocationDBHandler().getBedAllocation(allocNo);
		//**//
	}
	public void payDue(int dueNo)
	{
		this.bedAllocation.payDue(dueNo);
	}
	public void saveBedAllocation() throws ClassNotFoundException, SQLException
	{
		BedAllocationDBHandler.getBedAllocationDBHandler().saveBedAllocation(bedAllocation);
		this.bedAllocation=null;
	}
	
	public BedAllocation getBedAllocation() {
		return bedAllocation;
	}

	public void setBedAllocation(BedAllocation bedAllocation) {
		this.bedAllocation = bedAllocation;
	}

	//Check if null then Show error
	public BedAllocation dischargePatient(int disReqNo,int bedAllocNo) throws ClassNotFoundException, SQLException
	{
		BedAllocation b = BedAllocationDBHandler.getBedAllocationDBHandler().getBedAllocation(bedAllocNo);
		//Checking if all dues are paid
		for(Due due : b.getDues())
		{
			if(due.getStatus().equalsIgnoreCase("UnPaid"))
			{
				return null;
			}
		}
		
		
		b.setStatus("Invalid");
		BedAllocationDBHandler.getBedAllocationDBHandler().saveBedAllocation(b);
		DischargeRequestDBHandler.getDischargeRequestDBHandler().setStatus(disReqNo,new String("Completed"));
		RoomDBHandler.getRoomDBHandler().setBedAvailable(b.getRoomNo(), b.getBedNo(), true);
		return b;			
	}
	
	public ArrayList<DischargeRequest> getDischargeRequests() throws ClassNotFoundException, SQLException
	{
		return DischargeRequestDBHandler.getDischargeRequestDBHandler().getDischargeRequests();
	}
	
	public ArrayList<DischargeRequest> getDischargeRequests(String patName) throws ClassNotFoundException, SQLException
	{
		return DischargeRequestDBHandler.getDischargeRequestDBHandler().getDischargeRequest(patName);
	}
}