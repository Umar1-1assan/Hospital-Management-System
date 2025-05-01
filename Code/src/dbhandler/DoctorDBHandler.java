package dbhandler;
import hospital.Doctor;
import hospital.Nurse;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class DoctorDBHandler
{
	//Single object creation
	private static DoctorDBHandler doctorDBHandler = null;
	private DBManager dbManager;
	
	private DoctorDBHandler( )
	{
		this.dbManager=DBManager.getDBManager();
	}
	
	public static DoctorDBHandler getDoctorDBHandler()
	{
		if(doctorDBHandler == null)
		{
			doctorDBHandler = new DoctorDBHandler();
			return doctorDBHandler;
		}
		return doctorDBHandler;
	}
	
	// Query Methods	
	public boolean saveDoctor(Doctor d) throws ClassNotFoundException, SQLException
	{
		Connection conn = this.dbManager.connect();
		
		String query1 = "SELECT COUNT(*) AS COUNT FROM Doctor where username=?";
		String query2 = "INSERT INTO Doctor (dname, daddress, cnic, phoneNumber, age, gender, passwordd, speciality, username) VALUES "
				+ "(? ,? ,? ,? ,? , ? ,? ,? ,?)";
		String query3 = "UPDATE Doctor "
				+ " SET dname = ?, daddress=?, cnic=?, phoneNumber=?, age=?, gender=?, passwordd=?, speciality=? "
				+ " Where username=?";
		String query4 = "DELETE FROM DoctorSchedule WHERE username = ?";
		String query5 = "INSERT INTO DoctorSchedule (username, Dayy) VALUES (?,?)";
		
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
		st.setString(8,d.getSpeciality());
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
		if(count>0)
			return true;
		else return false;
	}
	
	public ArrayList<Doctor> getDoctors() throws ClassNotFoundException, SQLException
	{
		ArrayList<Doctor> list = new ArrayList<>();
		Connection conn = this.dbManager.connect();
		String query = "SELECT dname,username,gender,speciality FROM Doctor";
		Statement st = conn.createStatement();
		ResultSet r = st.executeQuery(query);
		
		while(r.next())
		{
			String dname = r.getString("dname");
			String username = r.getString("username");
			String gander = r.getString("gender");
			String speciality = r.getString("speciality");
			Doctor d = new Doctor();
			d.setName(dname);
			d.setUsername(username);
			d.setGender(gander);
			d.setSpeciality(speciality);
			
			//Getting Schedule
			String query2 = "SELECT Dayy FROM DoctorSchedule WHERE username=?";
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
	
	public ArrayList<Doctor> getDoctorViaName(String name) throws ClassNotFoundException, SQLException
	{
		ArrayList<Doctor> list = new ArrayList<>();
		Connection conn = this.dbManager.connect();
		String query = "SELECT dname,username,gender,speciality FROM Doctor WHERE dname like ? ;";
		PreparedStatement st = conn.prepareStatement(query);
		st.setString(1, '%'+name+'%');
		ResultSet r = st.executeQuery();
		
		while(r.next())
		{
			String dname = r.getString("dname");
			String username = r.getString("username");
			String gander = r.getString("gender");
			String speciality = r.getString("speciality");
			Doctor d = new Doctor();
			d.setName(dname);
			d.setUsername(username);
			d.setGender(gander);
			d.setSpeciality(speciality);
			
			//Getting Schedule
			String query2 = "SELECT Dayy FROM DoctorSchedule WHERE username=?";
			PreparedStatement stat = conn.prepareStatement(query2);
			stat.setString(1, username);
			ResultSet set = stat.executeQuery();
			while(set.next())
			{
				String day = set.getString("Dayy");
				d.getSchedule().addDay(day);
			}
			stat.close();
			set.close();		
			list.add(d);
		}
		r.close();
		st.close();
		conn.close();
		return list;
	}
	
	public Doctor getDoctor(String username) throws ClassNotFoundException, SQLException
	{
		Doctor d = new Doctor();
		Connection conn = this.dbManager.connect();
		String query = "SELECT * FROM Doctor WHERE username = ? ;";
		PreparedStatement st = conn.prepareStatement(query);
		st.setString(1, username);
		ResultSet r = st.executeQuery();
		
		if(!r.next())
		{
			return null;
		}
		
		String dname = r.getString("dname");
		String nusername = r.getString("username");
		String gander = r.getString("gender");
		String speciality = r.getString("speciality");
		String phone = r.getString("phoneNumber");
		String address = r.getString("daddress");
		int age = r.getInt("age");
		String cnic = r.getString("cnic");
		String pass = r.getString("passwordd");
		
		d.setName(dname);
		d.setUsername(nusername);
		d.setGender(gander);
		d.setSpeciality(speciality);
		d.setCnic(cnic);
		d.setPassword(pass);
		d.setSpeciality(speciality);
		d.setAge(age);
		d.setPhoneNumber(phone);
		d.setAddress(address);
		
		//Getting Schedule
		String query2 = "SELECT Dayy FROM DoctorSchedule WHERE username=?";
		PreparedStatement stat = conn.prepareStatement(query2);
		stat.setString(1, nusername);
		ResultSet set = stat.executeQuery();
		while(set.next())
		{
			String day = set.getString("Dayy");
			d.getSchedule().addDay(day);
		}
		stat.close();
		r.close();
		st.close();
		conn.close();
		return d;
	}
	
	public boolean removeDoctor(String username) throws ClassNotFoundException, SQLException
	{
		Connection conn = this.dbManager.connect();
		String query = "DELETE FROM DOCTOR WHERE username= ? ;";
		String query2 = "DELETE FROM DOCTORSCHEDULE WHERE username=?";
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
		String query = "SELECT COUNT(*) FROM Doctor WHERE username = ? ;";
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