package dbhandler;
import hospital.*;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class AdmissionRequestDBHandler
{
	//Single object creation
	private static AdmissionRequestDBHandler admissionRequestDBHandler = null;
	private DBManager dbManager;
	
	private AdmissionRequestDBHandler()
	{
		this.dbManager=DBManager.getDBManager();
	}
	
	public static AdmissionRequestDBHandler getAdmissionRequestDBHandler()
	{
		if(admissionRequestDBHandler == null)
		{
			admissionRequestDBHandler = new AdmissionRequestDBHandler();
			return admissionRequestDBHandler;
		}
		else return admissionRequestDBHandler;
	}
	
	// Query Methods
	public boolean saveAdmissionRequest(AdmissionRequest a) throws ClassNotFoundException, SQLException
	{
		String query = "INSERT INTO AdmissionRequest (dateTimee, docUsername, patCnic, statuss) VALUES "
				+ " (?, ?, ?, ?)";
		Connection conn = this.dbManager.connect();
		PreparedStatement st = conn.prepareStatement(query);
		st.setString(1, a.getDateTime());
		st.setString(2,a.getDoctor().getUsername());
		st.setString(3, a.getPatient().getCnic());
		st.setString(4, "Pending");
		
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
	
	public ArrayList<AdmissionRequest> getAdmissionRequests() throws ClassNotFoundException, SQLException
	{
		ArrayList<AdmissionRequest> list = new ArrayList<>();
		Connection conn = this.dbManager.connect();
		String query = "SELECT a.requestNumber, p.pname, d.dname, a.dateTimee, p.cnic FROM AdmissionRequest a JOIN Patient p ON a.patCnic = p.cnic JOIN Doctor d"
				+" ON a.docUsername = d.username WHERE a.statuss = 'Pending'";
		Statement st = conn.createStatement();
		ResultSet r = st.executeQuery(query);
		
		while(r.next())
		{
			int reqNo = r.getInt("requestNumber");
			String patName = r.getString("pname");
			String docName = r.getString("dname");
			String dateTime = r.getString("dateTimee");
			
			AdmissionRequest a = new AdmissionRequest();
			a.setRequestNumber(reqNo);
			a.setDateTime(dateTime);
			
			Doctor d = new Doctor();
			Patient p = new Patient();
			d.setName(docName);
			p.setName(patName);
			p.setCnic(r.getString("cnic"));
			a.setDoctor(d);
			a.setPatient(p);
			
			list.add(a);
		}
		r.close();
		st.close();
		conn.close();
		
		return list;
	}
	
	public ArrayList<AdmissionRequest> getAdmissionRequest(String Patname) throws ClassNotFoundException, SQLException
	{
		ArrayList<AdmissionRequest> list = new ArrayList<>();
		Connection conn = this.dbManager.connect();
		String query = "SELECT a.requestNumber, p.pname, d.dname, a.dateTimee, p.cnic FROM AdmissionRequest a JOIN Patient p ON a.patCnic = p.cnic JOIN Doctor d"
				+" ON a.docUsername = d.username WHERE a.statuss = 'Pending' and p.pname like ?";
		PreparedStatement st = conn.prepareStatement(query);
		st.setString(1, '%'+Patname+'%');
		ResultSet r = st.executeQuery();
		
		while(r.next())
		{
			int reqNo = r.getInt("requestNumber");
			String patName = r.getString("pname");
			String docName = r.getString("dname");
			String dateTime = r.getString("dateTimee");
			
			AdmissionRequest a = new AdmissionRequest();
			a.setRequestNumber(reqNo);
			a.setDateTime(dateTime);
			
			Doctor d = new Doctor();
			Patient p = new Patient();
			d.setName(docName);
			p.setName(patName);
			p.setCnic(r.getString("cnic"));
			a.setDoctor(d);
			a.setPatient(p);
			
			list.add(a);
		}
		r.close();
		st.close();
		conn.close();
		return list;
	}
	
	//public AdmissionRequest getAdmissionRequest(int admissionRequestID) throws ClassNotFoundException, SQLException
	//{
		
	//}
	
	public int getMaxAdmissionRequestNumber() throws ClassNotFoundException, SQLException
	{
		Connection conn = this.dbManager.connect();
		String query = "SELECT MAX(requestNumber) AS reqNo FROM AdmissionRequest";
		Statement st = conn.createStatement();
		
		ResultSet r = st.executeQuery(query);
		r.next();
		int no = r.getInt("reqNo");
		r.close();
		st.close();
		conn.close();
		return no;
	}
	
	public boolean isPendingRequest(String patID) throws ClassNotFoundException, SQLException
	{
		Connection conn = this.dbManager.connect();
		String query = "SELECT COUNT(*) AS [Count] FROM AdmissionRequest a WHERE a.patCnic=? and a.statuss='Pending';";
		
		PreparedStatement st = conn.prepareStatement(query);
		st.setString(1, patID);
		
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
	
	public boolean markCompleted(int reqNo) throws ClassNotFoundException, SQLException
	{
		Connection conn = this.dbManager.connect();
		String query = "UPDATE AdmissionRequest SET statuss='Completed' Where requestNumber=?;";
		
		PreparedStatement st = conn.prepareStatement(query);
		st.setInt(1, reqNo);
		
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
	
    
}