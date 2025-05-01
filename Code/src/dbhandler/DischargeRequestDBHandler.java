package dbhandler;
import hospital.*;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
public class DischargeRequestDBHandler
{
	//Single object creation
	private static DischargeRequestDBHandler dischargeRequestDBHandler = null;
	private DBManager dbManager;
	
	private DischargeRequestDBHandler()
	{
		this.dbManager = DBManager.getDBManager();
	}
	
	public static DischargeRequestDBHandler getDischargeRequestDBHandler()
	{
		if(dischargeRequestDBHandler == null)
		{
			dischargeRequestDBHandler = new DischargeRequestDBHandler();
		}
		return dischargeRequestDBHandler;
	}

	
	// Query Methods
	public boolean saveDischargeRequest(DischargeRequest d) throws ClassNotFoundException, SQLException
	{
		String query = "INSERT INTO DischargeRequest (dateTimee, bedAllocationNo, statuss) VALUES"
				+ " (?, ?, ?)";
		Connection conn = this.dbManager.connect();
		PreparedStatement st = conn.prepareStatement(query);
		st.setString(1, d.getDateTime());
		st.setInt(2,d.getBedAllocation().getBedAllocationNumber());
		st.setString(3, "Pending");
		
		if(st.executeUpdate() > 0)
		{
			st.close();
			conn.close();
			return true;
		}
		st.close();
		conn.close();
		return false;
	}
	
	public ArrayList<DischargeRequest> getDischargeRequests() throws ClassNotFoundException, SQLException
	{

		ArrayList<DischargeRequest> list = new ArrayList<>();
		Connection conn = this.dbManager.connect();
		
		String query = "SELECT d.requestNumber, b.bedAllocationNumber, p.pname,p.cnic, d.dateTimee FROM DischargeRequest d JOIN bedAllocation b"
				+ " ON d.bedAllocationNo = b.bedAllocationNumber JOIN Patient p ON b.patCnic = p.cnic WHERE b.statuss = 'Valid';";
		Statement st = conn.createStatement();
		ResultSet r = st.executeQuery(query);
		
		while(r.next())
		{
			DischargeRequest d = new DischargeRequest();
			int reqNo = r.getInt("requestNumber");
			int bedAllocNo = r.getInt("bedAllocationNumber");
			String pname = r.getString("pname");
			String dateTime = r.getString("dateTimee");
			String cnic = r.getString("cnic");
			
			BedAllocation b = new BedAllocation();
			b.getPatient().setName(pname);
			b.getPatient().setCnic(cnic);
			b.setBedAllocationNumber(bedAllocNo);
			d.setRequestNumber(reqNo);
			d.setDateTime(dateTime);
			
			d.setBedAllocation(b);
			list.add(d);
		}
		r.close();
		st.close();
		conn.close();
		
		return list;
	}
	
	public ArrayList<DischargeRequest> getDischargeRequest(String Patname) throws ClassNotFoundException, SQLException
	{
		ArrayList<DischargeRequest> list = new ArrayList<>();
		Connection conn = this.dbManager.connect();
		
		String query = "SELECT d.requestNumber, b.bedAllocationNumber, p.pname, p.cnic, d.dateTimee FROM DischargeRequest d JOIN bedAllocation b"
				+ " ON d.bedAllocationNo = b.bedAllocationNumber JOIN Patient p ON b.patCnic = p.cnic WHERE p.pname like ? AND b.statuss = 'Valid';";
		PreparedStatement st = conn.prepareStatement(query);
		st.setString(1,'%'+Patname+'%');
		ResultSet r = st.executeQuery(query);
		
		while(r.next())
		{
			DischargeRequest d = new DischargeRequest();
			int reqNo = r.getInt("requestNumber");
			int bedAllocNo = r.getInt("bedAllocationNumber");
			String pname = r.getString("pname");
			String dateTime = r.getString("dateTimee");
			String cnic = r.getString("cnic");
			
			BedAllocation b = new BedAllocation();
			b.getPatient().setName(pname);
			b.getPatient().setCnic(cnic);
			b.setBedAllocationNumber(bedAllocNo);
			d.setRequestNumber(reqNo);
			d.setDateTime(dateTime);
			
			d.setBedAllocation(b);
			list.add(d);
		}
		r.close();
		st.close();
		conn.close();
		
		return list;
	}
	
	//public DischargeRequest getDischargeRequest(int dischargeRequestID)
	//{
		//return null;
	//}
	
	public int getMaxDischargeRequestNumber() throws ClassNotFoundException, SQLException
	{
		Connection conn = this.dbManager.connect();
		String query = "SELECT MAX(requestNumber) AS reqNo FROM DischargeRequest";
		Statement st = conn.createStatement();
		
		ResultSet r = st.executeQuery(query);
		r.next();
		int no = r.getInt("reqNo");
		r.close();
		st.close();
		conn.close();
		return no;
	}
	public boolean setStatus(int reqNo, String status) throws ClassNotFoundException, SQLException
	{
		Connection conn = this.dbManager.connect();
		String query = "UPDATE DischargeRequest SET statuss=? Where requestNumber=?;";
		
		PreparedStatement st = conn.prepareStatement(query);
		st.setInt(2, reqNo);
		st.setString(1, status);
		
		if(st.executeUpdate()>0)
		{
			st.close();
			conn.close();
			return true;
		}
		else
		{
			st.close();
			conn.close();
			return false;
		}
		
	}
	public boolean isPendingRequest(int bedAllocNo) throws ClassNotFoundException, SQLException
	{
		Connection conn = this.dbManager.connect();
		String query = "SELECT COUNT(*) AS [Count] FROM DischargeRequest d JOIN bedAllocation b ON d.bedAllocationNo = b.bedAllocationNumber "
				+ " AND b.bedAllocationNumber = ? AND d.statuss='Pending';";
		
		PreparedStatement st = conn.prepareStatement(query);
		st.setInt(1, bedAllocNo);
		
		ResultSet r = st.executeQuery();
		r.next();
		int count = r.getInt("Count");
		r.close();
		if(count>0)
		{
			st.close();
			conn.close();
			return true;
		}
		else 
		{
			st.close();
			conn.close();
			return false;
		}
	}
	
    
}