package dbhandler;
import hospital.LabTechnician;
import hospital.Nurse;
import hospital.Pharmacist;
import hospital.Room;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
public class NurseDBHandler
{
	//Single object creation
	private static NurseDBHandler nurseDBHandler = null;
	private DBManager dbManager;
	
	private NurseDBHandler()
	{
		this.dbManager=DBManager.getDBManager();
	}
	
	public static NurseDBHandler getNurseDBHandler()
	{
		if(nurseDBHandler == null)
		{
			nurseDBHandler = new NurseDBHandler();
		}
		return nurseDBHandler;
	}
	
	// Query Methods
	
	public boolean saveNurse(Nurse d) throws ClassNotFoundException, SQLException
	{
		Connection conn = this.dbManager.connect();
		
		String query1 = "SELECT COUNT(*) AS COUNT FROM Nurse where username=?";
		String query2 = "INSERT INTO Nurse (nname, naddress, cnic, phoneNumber, age, gender, passwordd, workExperience, RoomNo, username) VALUES "
				+ "(?,?,?,?,?,?,?,?,?,?)";
		String query3 = "UPDATE Nurse "
				+ " SET nname = ?, naddress=?, cnic=?, phoneNumber=?, age=?, gender=?, passwordd=?, workExperience=? ,RoomNo=?"
				+ " Where username=?";
		String query4 = "DELETE FROM NurseSchedule WHERE username = ?";
		String query5 = "INSERT INTO NurseSchedule (username, Dayy) VALUES (?,?)";
		
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
		st.setInt(9,d.getRoomAssigned());
		st.setString(10,d.getUsername());
		
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
	
	public ArrayList<Nurse> getNurses() throws ClassNotFoundException, SQLException
	{
		ArrayList<Nurse> list = new ArrayList<>();
		Connection conn = this.dbManager.connect();
		String query = "SELECT nname,username,gender,workExperience FROM Nurse";
		Statement st = conn.createStatement();
		ResultSet r = st.executeQuery(query);
		
		while(r.next())
		{
			String nname = r.getString("nname");
			String username = r.getString("username");
			String gander = r.getString("gender");
			int workExp = r.getInt("workExperience");
			Nurse d = new Nurse();
			d.setName(nname);
			d.setUsername(username);
			d.setGender(gander);
			d.setWorkExperience(workExp);;
			
			//Getting Schedule
			String query2 = "SELECT Dayy FROM NurseSchedule WHERE username=?";
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
	
	public ArrayList<Nurse> getNurseViaName(String name) throws ClassNotFoundException, SQLException
	{
		ArrayList<Nurse> list = new ArrayList<>();
		Connection conn = this.dbManager.connect();
		String query = "SELECT nname,username,gender,workExperience FROM Nurse Where nname like ?";
		PreparedStatement st = conn.prepareStatement(query);
		st.setString(1, '%'+name+'%');
		ResultSet r = st.executeQuery(query);
		
		while(r.next())
		{
			String nname = r.getString("nname");
			String username = r.getString("username");
			String gander = r.getString("gender");
			int workExp = r.getInt("workExperience");
			Nurse d = new Nurse();
			d.setName(nname);
			d.setUsername(username);
			d.setGender(gander);
			d.setWorkExperience(workExp);;
			
			//Getting Schedule
			String query2 = "SELECT Dayy FROM Nurse WHERE username=?";
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
	
	public Nurse getNurse(String username) throws ClassNotFoundException, SQLException
	{
		Nurse d = new Nurse();
		Connection conn = this.dbManager.connect();
		String query = "SELECT * FROM Nurse Where username = ?";
		PreparedStatement st = conn.prepareStatement(query);
		st.setString(1, username);
		ResultSet r = st.executeQuery();
		if(!r.next())
		{
			return null;
		}
		
		String lname = r.getString("nname");
		String nusername = r.getString("username");
		String gander = r.getString("gender");
		int workExp = r.getInt("workExperience");
		String phone = r.getString("phoneNumber");
		String address = r.getString("naddress");
		int age = r.getInt("age");
		String cnic = r.getString("cnic");
		String pass = r.getString("passwordd");
		int RoomNo = r.getInt("RoomNo");
		
		d.setName(lname);
		d.setUsername(nusername);
		d.setGender(gander);
		d.setWorkExperience(workExp);
		d.setAge(age);
		d.setPhoneNumber(phone);
		d.setAddress(address);
		d.setPassword(pass);
		d.setCnic(cnic);
		d.setRoomAssigned(RoomNo);
			
		//Getting Schedule
		String query2 = "SELECT Dayy FROM NurseSchedule WHERE username=?";
		PreparedStatement stat = conn.prepareStatement(query2);
		stat.setString(1, nusername);
		ResultSet set = stat.executeQuery();
		while(set.next())
		{
			String day = set.getString("Dayy");
			d.getSchedule().addDay(day);
		}
		stat.close();		
		st.close();
		r.close();
		set.close();
		conn.close();
		return d;
	}
	
	//public Room getRoom(String nurseID)
	//{
		
	//}
	
	public boolean removeNurse(String username) throws ClassNotFoundException, SQLException
	{
		Connection conn = this.dbManager.connect();
		String query = "DELETE FROM Nurse WHERE username= ? ;";
		String query2 = "DELETE FROM NurseSchedule WHERE username=?";
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
	
    
}