package dbhandler;
import hospital.LabTechnician;
import hospital.Pharmacist;
import hospital.Receptionist;
import hospital.Room;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
public class ReceptionistDBHandler
{
	//Single object creation
	private static ReceptionistDBHandler receptionistDBHandler = null;
	private DBManager dbManager;
	
	private ReceptionistDBHandler()
	{
		this.dbManager=DBManager.getDBManager();
	}
	
	public static ReceptionistDBHandler getReceptionistDBHandler()
	{
		if(receptionistDBHandler == null)
		{
			receptionistDBHandler = new ReceptionistDBHandler();
		}
		return receptionistDBHandler;
	}
	
	// Query Methods	
	public boolean saveReceptionist(Receptionist d) throws ClassNotFoundException, SQLException
	{
		Connection conn = this.dbManager.connect();
		
		String query1 = "SELECT COUNT(*) AS COUNT FROM Receptionist where username=?";
		String query2 = "INSERT INTO Receptionist (rname, raddress, cnic, phoneNumber, age, gender, passwordd, workExperience, username) VALUES "
				+ "(?,?,?,?,?,?,?,?,?)";
		String query3 = "UPDATE Receptionist "
				+ " SET rname = ?, raddress=?, cnic=?, phoneNumber=?, age=?, gender=?, passwordd=?, workExperience=? "
				+ " Where username=?";
		String query4 = "DELETE FROM ReceptionistSchedule WHERE username = ?";
		String query5 = "INSERT INTO ReceptionistSchedule (username, Dayy) VALUES (?,?)";
		
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
	
	public boolean removeReceptionist(String username) throws ClassNotFoundException, SQLException
	{
		Connection conn = this.dbManager.connect();
		String query = "DELETE FROM Receptionist WHERE username= ? ;";
		String query2 = "DELETE FROM ReceptionistSchedule WHERE username=?";
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
	
	public ArrayList<Receptionist> getReceptionists() throws ClassNotFoundException, SQLException
	{
		ArrayList<Receptionist> list = new ArrayList<>();
		Connection conn = this.dbManager.connect();
		String query = "SELECT rname,username,gender,workExperience FROM Receptionist";
		Statement st = conn.createStatement();
		ResultSet r = st.executeQuery(query);
		
		while(r.next())
		{
			String lname = r.getString("rname");
			String username = r.getString("username");
			String gander = r.getString("gender");
			int workExp = r.getInt("workExperience");
			Receptionist d = new Receptionist();
			d.setName(lname);
			d.setUsername(username);
			d.setGender(gander);
			d.setWorkExperience(workExp);
			
			//Getting Schedule
			String query2 = "SELECT Dayy FROM ReceptionistSchedule WHERE username=?";
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
		r.close();
		st.close();
		conn.close();
		return list;
	}
	
	public ArrayList<Receptionist> getReceptionistViaName(String name) throws ClassNotFoundException, SQLException
	{
		ArrayList<Receptionist> list = new ArrayList<>();
		Connection conn = this.dbManager.connect();
		String query = "SELECT rname,username,gender,workExperience FROM Receptionist where rname like ?";
		PreparedStatement st = conn.prepareStatement(query);
		st.setString(1, '%'+name+'%');
		ResultSet r = st.executeQuery();
		
		while(r.next())
		{
			String pname = r.getString("pname");
			String username = r.getString("username");
			String gander = r.getString("gender");
			int workExp = r.getInt("workExperience");
			Receptionist d = new Receptionist();
			d.setName(pname);
			d.setUsername(username);
			d.setGender(gander);
			d.setWorkExperience(workExp);;
			
			//Getting Schedule
			String query2 = "SELECT Dayy FROM PharmacistSchedule WHERE username=?";
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
		r.close();
		st.close();
		conn.close();
		return list;
	}
	
	public Receptionist getReceptionist(String username) throws ClassNotFoundException, SQLException
	{
		Receptionist d = new Receptionist();
		Connection conn = this.dbManager.connect();
		String query = "SELECT * FROM Receptionist Where username = ?";
		PreparedStatement st = conn.prepareStatement(query);
		st.setString(1, username);
		ResultSet r = st.executeQuery();
		if(!r.next())
		{
			return null;
		}
		
		String lname = r.getString("rname");
		String nusername = r.getString("username");
		String gander = r.getString("gender");
		int workExp = r.getInt("workExperience");
		String phone = r.getString("phoneNumber");
		String address = r.getString("raddress");
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
		String query2 = "SELECT Dayy FROM ReceptionistSchedule WHERE username=?";
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
	
	//public Room getRoom(int receptionistID)
	//{
		//return null;
	//}
	
    
}