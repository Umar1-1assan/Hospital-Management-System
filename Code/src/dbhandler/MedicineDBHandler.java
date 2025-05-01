package dbhandler;
import hospital.Medicine;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
public class MedicineDBHandler
{
	//Single object creation
	private static MedicineDBHandler medicineDBHandler = null;
	private DBManager dbManager;
	
	private MedicineDBHandler()
	{
		this.dbManager=DBManager.getDBManager();
	}
	
	public static MedicineDBHandler getMedicineDBHandler()
	{
		if(medicineDBHandler == null)
		{
			medicineDBHandler = new MedicineDBHandler();
		}
		return medicineDBHandler;
	}
	
	// Query Methods
	public boolean saveMedicine(Medicine m) throws ClassNotFoundException, SQLException
	{
		 String checkQuery = "SELECT COUNT(*) FROM Medicine WHERE id = ?";
	     String insertQuery = "INSERT INTO Medicine (id, name, quantity, price) VALUES (?, ?, ?, ?)";
	     String updateQuery = "UPDATE Medicine SET name = ?, quantity = ?, price = ? WHERE id = ?";

	        try (Connection connection = dbManager.connect();
	             PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {

	            // Check if the medicine already exists
	            checkStmt.setString(1, m.getId());
	            try (ResultSet resultSet = checkStmt.executeQuery()) {
	                if (resultSet.next() && resultSet.getInt(1) > 0) {
	                    // Medicine exists, update it
	                    try (PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
	                        updateStmt.setString(1, m.getName());
	                        updateStmt.setInt(2, m.getQuantity());
	                        updateStmt.setFloat(3, m.getPrice());
	                        updateStmt.setString(4, m.getId());
	                        updateStmt.executeUpdate();
	                    }
	                } 
	                else 
	                {
	                    // Medicine does not exist, insert it
	                    try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
	                        insertStmt.setString(1, m.getId());
	                        insertStmt.setString(2, m.getName());
	                        insertStmt.setInt(3, m.getQuantity());
	                        insertStmt.setFloat(4, m.getPrice());
	                        insertStmt.executeUpdate();
	                    }
	                }
	            }
	        }

	        return true;
	}
	
	public ArrayList<Medicine> getMedicines() throws ClassNotFoundException, SQLException
	{
		 String query = "SELECT id, name, quantity, price FROM Medicine";
	        ArrayList<Medicine> medicines = new ArrayList<>();

	        try (Connection connection = dbManager.connect();
	             PreparedStatement stmt = connection.prepareStatement(query);
	             ResultSet resultSet = stmt.executeQuery()) {

	            while (resultSet.next()) 
	            {
	                Medicine medicine = new Medicine();
	                medicine.setId(resultSet.getString("id"));
	                medicine.setName(resultSet.getString("name"));
	                medicine.setQuantity(resultSet.getInt("quantity"));
	                medicine.setPrice(resultSet.getFloat("price"));
	                medicines.add(medicine);
	            }
	        }

	        return medicines;
	}
	
	public ArrayList<Medicine> getMedicineViaName(String medName) throws ClassNotFoundException, SQLException
	{
		 String query = "SELECT id, name, quantity, price FROM Medicine WHERE name like ?";
	        ArrayList<Medicine> medicines = new ArrayList<>();

	        try (Connection connection = dbManager.connect();
	             PreparedStatement stmt = connection.prepareStatement(query)) {

	            stmt.setString(1, '%'+medName+'%');

	            try (ResultSet resultSet = stmt.executeQuery()) {
	                while (resultSet.next()) {
	                    Medicine medicine = new Medicine();
	                    medicine.setId(resultSet.getString("id"));
	                    medicine.setName(resultSet.getString("name"));
	                    medicine.setQuantity(resultSet.getInt("quantity"));
	                    medicine.setPrice(resultSet.getFloat("price"));
	                    medicines.add(medicine);
	                }
	            }
	        }

	        return medicines;
	}
	
	public Medicine getMedicine(String medicineID) throws ClassNotFoundException, SQLException
	{
		 String query = "SELECT id, name, quantity, price FROM Medicine WHERE id = ?";
	        Medicine medicine = null;

	        try (Connection connection = dbManager.connect();
	             PreparedStatement stmt = connection.prepareStatement(query)) {

	            stmt.setString(1, medicineID);

	            try (ResultSet resultSet = stmt.executeQuery()) {
	                if (resultSet.next()) {
	                    medicine = new Medicine();
	                    medicine.setId(resultSet.getString("id"));
	                    medicine.setName(resultSet.getString("name"));
	                    medicine.setQuantity(resultSet.getInt("quantity"));
	                    medicine.setPrice(resultSet.getFloat("price"));
	                }
	            }
	        }

	        return medicine;
	}	
    
}