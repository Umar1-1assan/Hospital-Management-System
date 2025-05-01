package manager;
//import hospital.User;
//import hospital.LabTechnicianQueue;
//import hospital.LabQueue;
//import hospital.Patient;
import hospital.*;
import dbhandler.*;

import java.sql.SQLException;
import java.util.ArrayList;
//import dbhandler.PatientDBHandler;

public class PerformTestManager
{
	private LabQueue labQueue;
	private LabVisit labVisit;
	
	public PerformTestManager()
	{
		this.labQueue = LabQueue.getLabQueue();
	}
	
	public void StartProcedure(int visitNo) throws ClassNotFoundException, SQLException
	{
		this.labVisit = this.labQueue.getVisit(visitNo);
		this.labVisit.setStatus("Working");
	}
	
	public int assignSampleNumber() throws ClassNotFoundException, SQLException
	{
		int sampNo = LabVisitDBHandler.getLabVisitDBHandler().getMaxVisitNo()+1;
		this.labVisit.setSampleNumber(sampNo);
		return sampNo;
	}
	
	public void endProcedure(String report) throws ClassNotFoundException, SQLException
	{
		this.labVisit.setReport(report);
		this.labVisit.setStatus("Completed");
		LabVisitDBHandler.getLabVisitDBHandler().saveVisit(labVisit);
		this.labQueue.removeVisit(this.labVisit.getVisitNo());
		this.labVisit=null;
	}
	
	public ArrayList<LabVisit> getLabVisits() throws ClassNotFoundException, SQLException
	{
		return LabVisitDBHandler.getLabVisitDBHandler().getVisits();
	}
	
	public ArrayList<LabVisit> getLabVisits(String patName) throws ClassNotFoundException, SQLException
	{
		return LabVisitDBHandler.getLabVisitDBHandler().getVisits(patName);
	}	

	public LabTechnician showProfile(String username) throws ClassNotFoundException, SQLException
	{
		return LabTechnicianDBHandler.getLabTechnicianDBHandler().getLabTechnician(username);
	}

	public LabQueue getLabQueue() {
		return labQueue;
	}

	public void setLabQueue(LabQueue labQueue) {
		this.labQueue = labQueue;
	}

	public LabVisit getLabVisit() {
		return labVisit;
	}

	public void setLabVisit(LabVisit labVisit) {
		this.labVisit = labVisit;
	}
	
	public LabVisit getLabVisit(int vNo) throws ClassNotFoundException, SQLException
	{
		return LabVisitDBHandler.getLabVisitDBHandler().getVisit(vNo);
	}
}
