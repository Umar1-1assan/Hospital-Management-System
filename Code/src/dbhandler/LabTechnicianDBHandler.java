package dbhandler;
import hospital.Doctor;
import hospital.LabTechnician;
import hospital.Nurse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class LabTechnicianDBHandler
{
	//Single object creation
	private static LabTechnicianDBHandler labTechnicianDBHandler = null;
	private DBManager dbManager;
	
	private LabTechnicianDBHandler()
	{
		this.dbManager=DBManager.getDBManager();
	}
	
	public static LabTechnicianDBHandler getLabTechnicianDBHandler()
	{
		if(labTechnicianDBHandler == null)
		{
			labTechnicianDBHandler = new LabTechnicianDBHandler();
		}
		return labTechnicianDBHandler;
	}

	
	// Query Methods	
	public boolean saveLabTechnician(LabTechnician d) throws SQLException, ClassNotFoundException
	{
		Connection conn = this.dbManager.connect();
		
		String query1 = "SELECT COUNT(*) AS COUNT FROM LabTechnician where username=?";
		String query2 = "INSERT INTO LabTechnician (lname, laddress, cnic, phoneNumber, age, gender, passwordd, workExperience, username) VALUES "
				+ "(? ,? ,? ,? ,? , ? ,? ,? ,?)";
		String query3 = "UPDATE LabTechnician "
				+ " SET lname = ?, laddress=?, cnic=?, phoneNumber=?, age=?, gender=?, passwordd=?, workExperience=? "
				+ " Where username=?";
		String query4 = "DELETE FROM LabTechSchedule WHERE username = ?";
		String query5 = "INSERT INTO LabTechSchedule (username, Dayy) VALUES (?,?)";
		
		PreparedStatement stat = conn.prepareStatement(query1);
		stat.setString(1, d.getUsername());
		PreparedStatement st;
		ResultSet r = stat.executeQuery();
		r.next();
		int count = r.getInt("COUNT");
		if(count > 0)
		{
			st = conn.prepareStatement(query3);
		}
		else st = conn.prepareStatement(query2);
		
		
		st.setString(1,d.getName());
		st.setString(2,d.getAddress());
		st.setString(3,d.getCnic());
		st.setString(4,d.getPhoneNumber());
		st.setInt(5,d.getAge());
		st.setString(6,d.getGender());
		st.setString(7,d.getPassword());
		st.setInt(8,d.getWorkExperience());
		st.setString(9,d.getUsername());
		
		st.executeUpdate();
		st.close();
		
		//Deleting previous schedule
		if(count > 0)
		{
			st = conn.prepareStatement(query4);
			st.setString(1, d.getUsername());
			st.executeUpdate();
			st.close();
		}
		
		ArrayList<String> days = d.getSchedule().getDays();
		for(String day: days)
		{
			st = conn.prepareStatement(query5);
			st.setString(1, d.getUsername());
			st.setString(2, day);
			st.executeUpdate();
			st.close();
		}
		r.close();
		stat.close();
		conn.close();
		return true;
	}
	
	public ArrayList<LabTechnician> getLabTechnicians() throws SQLException, ClassNotFoundException
	{
		ArrayList<LabTechnician> list = new ArrayList<>();
		Connection conn = this.dbManager.connect();
		String query = "SELECT lname,username,gender,workExperience FROM LabTechnician";
		Statement st = conn.createStatement();
		ResultSet r = st.executeQuery(query);
		
		while(r.next())
		{
			String lname = r.getString("lname");
			String username = r.getString("username");
			String gander = r.getString("gender");
			int workExp = r.getInt("workExperience");
			LabTechnician d = new LabTechnician();
			d.setName(lname);
			d.setUsername(username);
			d.setGender(gander);
			d.setWorkExperience(workExp);;
			
			//Getting Schedule
			String query2 = "SELECT Dayy FROM LabTechSchedule WHERE username=?";
			PreparedStatement stat = conn.prepareStatement(query2);
			stat.setString(1, username);
			ResultSet set = stat.executeQuery();
			while(set.next())
			{
				String day = set.getString("Dayy");
				d.getSchedule().addDay(day);
			}
			set.close();
			stat.close();		
			list.add(d);
		}
		st.close();
		conn.close();
		return list;
	}
	
	public ArrayList<LabTechnician> getLabTechnicianViaName(String name) throws ClassNotFoundException, SQLException
	{
		ArrayList<LabTechnician> list = new ArrayList<>();
		Connection conn = this.dbManager.connect();
		String query = "SELECT lname,username,gender,workExperience FROM LabTechnician Where lname like ?";
		PreparedStatement st = conn.prepareStatement(query);
		st.setString(1, '%'+name+'%');
		ResultSet r = st.executeQuery(query);
		
		while(r.next())
		{
			String lname = r.getString("lname");
			String username = r.getString("username");
			String gander = r.getString("gender");
			int workExp = r.getInt("workExperience");
			LabTechnician d = new LabTechnician();
			d.setName(lname);
			d.setUsername(username);
			d.setGender(gander);
			d.setWorkExperience(workExp);;
			
			//Getting Schedule
			String query2 = "SELECT Dayy FROM LabTechSchedule WHERE username=?";
			PreparedStatement stat = conn.prepareStatement(query2);
			stat.setString(1, username);
			ResultSet set = stat.executeQuery();
			while(set.next())
			{
				String day = set.getString("Dayy");
				d.getSchedule().addDay(day);
			}
			set.close();
			stat.close();		
			list.add(d);
		}
		st.close();
		conn.close();
		return list;
	}
	
	public LabTechnician getLabTechnician(String username) throws SQLException, ClassNotFoundException
	{
		LabTechnician d = new LabTechnician();
		Connection conn = this.dbManager.connect();
		String query = "SELECT * FROM LabTechnician Where username = ?";
		PreparedStatement st = conn.prepareStatement(query);
		st.setString(1, username);
		ResultSet r = st.executeQuery();
		if(!r.next())
		{
			return null;
		}
		
		String lname = r.getString("lname");
		String nusername = r.getString("username");
		String gander = r.getString("gender");
		int workExp = r.getInt("workExperience");
		String phone = r.getString("phoneNumber");
		String address = r.getString("laddress");
		int age = r.getInt("age");
		String cnic = r.getString("cnic");
		String pass = r.getString("passwordd");
		
		d.setName(lname);
		d.setUsername(nusername);
		d.setGender(gander);
		d.setWorkExperience(workExp);
		d.setAge(age);
		d.setPhoneNumber(phone);
		d.setAddress(address);
		d.setPassword(pass);
		d.setCnic(cnic);
			
		//Getting Schedule
		String query2 = "SELECT Dayy FROM LabTechSchedule WHERE username=?";
		PreparedStatement stat = conn.prepareStatement(query2);
		stat.setString(1, nusername);
		ResultSet set = stat.executeQuery();
		while(set.next())
		{
			String day = set.getString("Dayy");
			d.getSchedule().addDay(day);
		}
		set.close();
		r.close();
		stat.close();		
		st.close();
		conn.close();
		return d;
	}
	
	public boolean removeLabTechnician(String username) throws ClassNotFoundException, SQLException
	{
		Connection conn = this.dbManager.connect();
		String query = "DELETE FROM LabTechnician WHERE username= ? ;";
		String query2 = "DELETE FROM LabTechSchedule WHERE username=?";
		PreparedStatement st = conn.prepareStatement(query2);
		st.setString(1, username);
		
		int count = st.executeUpdate();
		
		st = conn.prepareStatement(query);
		st.setString(1, username);
		st.executeUpdate();
		
		st.close();
		conn.close();
		
		if(count>0)
			return true;
		else return false;
	}
	
	public boolean exists(String username) throws ClassNotFoundException, SQLException
	{
		String query = "SELECT COUNT(*) FROM LabTechnician WHERE username = ? ;";
		Connection conn = this.dbManager.connect();
		PreparedStatement st = conn.prepareStatement(query);
		st.setString(1, username);
		ResultSet r = st.executeQuery();
		if(r.next() && r.getInt(1)>=1)
		{
			r.close();
			st.close();
			conn.close();
			return true;
		}
		r.close();
		st.close();
		conn.close();
		
		return false;
	}
	
    
}