package hospital;
import java.sql.SQLException;
import java.util.ArrayList;

import dbhandler.LabTechnicianDBHandler;
import dbhandler.LabVisitDBHandler;
import dbhandler.PatientDBHandler;
public class LabQueue
{
	static private LabQueue lQueue = null;
	private ArrayList<LabVisit> labQueue;
	
	private LabQueue()
	{
		labQueue = new ArrayList<>();
	}
	
	public static LabQueue getLabQueue()
	{
		if(lQueue == null)
		{
			lQueue = new LabQueue();
		}
		return lQueue;
	}
	
	public boolean createLabVisit(String patID, String labTechID, String testName, String dateTime) throws ClassNotFoundException, SQLException
	{
		for(LabVisit l: this.labQueue)
		{
			if(l.getPatient().getCnic().equals(patID))
			{
				return false;
			}
		}
		Patient p = PatientDBHandler.getpatientDBHandler().getPatient(patID);
		LabTechnician d = LabTechnicianDBHandler.getLabTechnicianDBHandler().getLabTechnician(labTechID);
		if(p==null || d==null)
		{
			return false;
		}
		int id = LabVisitDBHandler.getLabVisitDBHandler().getMaxVisitNo()+1;
		
		int nid = this.getMaxNum();
		if (nid >= id)
		{
			id = nid+1;
		}
		
		int sampleNo= LabVisitDBHandler.getLabVisitDBHandler().getMaxSampleNo()+1;
		
		String report="";
		this.labQueue.add(new LabVisit(id,dateTime,testName,report,"Pending",sampleNo,p,d));
		return true;
	}
	public ArrayList<LabVisit> getQueuedLabVisits()
	{
		return this.labQueue;
	}
	public LabVisit getVisit(int visitNo)
	{
		for(LabVisit v : labQueue)
		{
			if( v.getVisitNo()==visitNo)
			{
				return v;
			}
		}
		return null;
	}
	
	public void removeVisit(int visitNo)
	{
		for(int i=0;i<this.labQueue.size();i++)
		{
			if(visitNo == labQueue.get(i).getVisitNo())
			{
				this.labQueue.remove(i);
				return;
			}
		}
	}
	public int getMaxNum()
	{
		int num=0;
		for(LabVisit d : this.labQueue)
		{
			if(d.getVisitNo()>num)
			{
				num = d.getVisitNo();
			}
		}
		return num;
	}
	
}