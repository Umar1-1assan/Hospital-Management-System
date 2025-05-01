package hospital;
import java.sql.SQLException;
import java.util.ArrayList;

import dbhandler.DoctorDBHandler;
import dbhandler.DoctorVisitDBHandler;
import dbhandler.PatientDBHandler;
public class DoctorQueue
{
	static private DoctorQueue dQueue = null;
	private ArrayList<DoctorVisit> doctorQueue;
	
	private DoctorQueue()
	{
		this.doctorQueue=new ArrayList<>();
	}
	
	public static DoctorQueue getDoctorQueue() 
	{
		if(dQueue == null)
		{
			dQueue = new DoctorQueue();
		}
		return dQueue;
	}
	
	//public void setDoctorQueue(ArrayList<DoctorVisit> doctorQueue) 
	//{
		//this.doctorQueue = doctorQueue;
	//}

	public boolean createDoctorVisit(String patID, String doctorID, String issue, String dateTime) throws ClassNotFoundException, SQLException
	{
		
		for(DoctorVisit d: this.doctorQueue)
		{
			if(d.getPatient().getCnic().equals(patID))
			{
				return false;
			}
		}
		Patient p = PatientDBHandler.getpatientDBHandler().getPatient(patID);
		Doctor d = DoctorDBHandler.getDoctorDBHandler().getDoctor(doctorID);
		if(p==null || d==null)
		{
			System.out.print("Not found\n");
			return false;
		}
		int id = DoctorVisitDBHandler.getDoctorVisitDBHandler().getMaxVisitNo()+1;
		int nid = this.getMaxNum();
		if (nid >= id)
		{
			id = nid+1;
		}
		
		String prescription="";
		this.doctorQueue.add(new DoctorVisit(id,dateTime,issue,"Pending",prescription,p,d));
		return true;
	}
	public ArrayList<DoctorVisit> getQueuedDoctorVisits()
	{
		return this.doctorQueue;
	}
	public DoctorVisit getVisit(int visitNo)
	{
		for(DoctorVisit v : doctorQueue)
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
		for(int i=0; i<this.doctorQueue.size();i++)
		{
			if(doctorQueue.get(i).visitNo == visitNo)
			{
				doctorQueue.remove(i);
				break;
			}
		}
	}
	
	public int getMaxNum()
	{
		int num=0;
		for(DoctorVisit d : this.doctorQueue)
		{
			if(d.getVisitNo()>num)
			{
				num = d.getVisitNo();
			}
		}
		return num;
	}
	
}