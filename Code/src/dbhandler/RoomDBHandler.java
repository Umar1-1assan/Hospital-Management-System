package dbhandler;
import hospital.Room;
import hospital.Bed;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class RoomDBHandler
{
	//Single object creation
	private static RoomDBHandler roomDBHandler = null;
	private DBManager dbManager;
	
	private RoomDBHandler()
	{
		this.dbManager=DBManager.getDBManager();
	}
	
	
	public static RoomDBHandler getRoomDBHandler()
	{
		if(roomDBHandler == null)
		{
			roomDBHandler = new RoomDBHandler();
			return roomDBHandler;
		}
		else return roomDBHandler;
	}
	
	// Query Methods
	//public void saveRoom(Room p)
	//{
		//No Need
	//}
	
	//public ArrayList<Room> getRooms()
	//{
		//No need
	//}
	
	
	//public Room getRoom(int roomID)
	//{
		//return null;
	//}
	
	public float getBedPrice(int roomNo, int bedNo) throws SQLException, ClassNotFoundException 
	{
		Connection conn = this.dbManager.connect();
		String query1 = "SELECT price from Bed where roomNo = ? and bedNo= ?";
		PreparedStatement st = conn.prepareStatement(query1);
		st.setInt(1, roomNo);
		st.setInt(2, bedNo);
		
		ResultSet r = st.executeQuery();
		if(!r.next())
		{
			r.close();
			st.close();
			conn.close();
			return -1;
		}
		float price = r.getFloat("price");
		r.close();
		st.close();
		conn.close();
		return price;
		
	}
	public ArrayList<Room> getAvailableBeds()  throws SQLException, ClassNotFoundException 
	{
		Connection conn = this.dbManager.connect();
		String query1 = "SELECT DISTINCT roomNo FROM Bed b Where B.available=1;";
		String query2 = "SELECT roomNo,bedNo,price FROM Bed B WHERE B.available=1;";
		
		ArrayList<Room> rooms = new ArrayList<>();
		
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(query1);
		
		while(rs.next())
		{
			Room r = new Room();
			r.setRoomNumber(rs.getInt("roomNo"));
			rooms.add(r);
		}
		
		rs = st.executeQuery(query2);
		
		while(rs.next())
		{
			Bed b = new Bed(rs.getInt("bedNo"),rs.getFloat("price"),true);
			for(Room r: rooms)
			{
				if(r.getRoomNumber() == rs.getInt("roomNo"))
				{
					r.addBed(b);
				}
			}
		}
		st.close();
		conn.close();
		return rooms;
	}
	public boolean setBedAvailable(int roomNo, int bedNo, boolean available)throws SQLException, ClassNotFoundException 
	{
		Connection conn = this.dbManager.connect();
		String query = "SELECT COUNT(*) FROM BED WHERE roomNo=? and bedNo=? and available=1";
		
		//First check if such bed exists
		if(available==false)
		{
			PreparedStatement st1 = conn.prepareStatement(query);
			st1.setInt(1, roomNo);
			st1.setInt(2, bedNo);
		
			ResultSet r = st1.executeQuery();
			r.next();
			if(r.getInt(1) == 0)
			{
				r.close();
				st1.close();
				return false;
			}
			r.close();
			st1.close();
		}
		
		String query1 = "UPDATE Bed SET available= ? where roomNo=? and bedNo = ?";
		PreparedStatement st = conn.prepareStatement(query1);
		int flag=0;
		if(available == true)
		{
			flag=1;
		}
		
		st.setInt(1, flag);
		st.setInt(2, roomNo);
		st.setInt(3, bedNo);
		
		if(st.executeUpdate() > 0)
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
	
	public ArrayList<Integer> getRooms() throws SQLException, ClassNotFoundException
	{
		Connection conn = this.dbManager.connect();
		String query1 = "SELECT roomNo FROM ROOM";
		
		ArrayList<Integer> rooms = new ArrayList<>();
		
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(query1);
		
		while(rs.next())
		{
			rooms.add(rs.getInt(1));
		}
		
		
		st.close();
		rs.close();
		conn.close();
		return rooms;
	}
	
    
}