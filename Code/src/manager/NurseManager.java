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

public class NurseManager
{
	private BedAllocation bedAllocation;
	
	public BedAllocation getBedAllocation() {
		return bedAllocation;
	}

	public void setBedAllocation(BedAllocation bedAllocation) {
		this.bedAllocation = bedAllocation;
	}

	public NurseManager()
	{
		bedAllocation=null;
	}
	
	public ArrayList<BedAllocation> getBedAllocations(String nurseID) throws ClassNotFoundException, SQLException
	{
		return BedAllocationDBHandler.getBedAllocationDBHandler().getBedAllocationViaNurse(nurseID);
	}
	
	public ArrayList<BedAllocation> getBedAllocations(String nurseID, String patName) throws ClassNotFoundException, SQLException
	{
		return BedAllocationDBHandler.getBedAllocationDBHandler().getBedAllocationViaNurseAndPat(nurseID, patName);
	}
	
	public void openBedAllocation(int allocNo) throws ClassNotFoundException, SQLException
	{
		this.bedAllocation =  BedAllocationDBHandler.getBedAllocationDBHandler().getBedAllocation(allocNo);
	}
	public void addDue(String desc, float price, String dateTime)
	{
		this.bedAllocation.addDue(desc, price, dateTime);
	}
	public void saveBedAllocation() throws ClassNotFoundException, SQLException
	{
		BedAllocationDBHandler.getBedAllocationDBHandler().saveBedAllocation(bedAllocation);
		this.bedAllocation=null;
	}

	public Nurse showProfile(String username) throws ClassNotFoundException, SQLException
	{
		return NurseDBHandler.getNurseDBHandler().getNurse(username);
	}
	
}