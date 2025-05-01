package dbhandler;
import hospital.Sale;
import hospital.MedicineSale;
import hospital.Medicine;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
public class SaleDBHandler
{
	//Single object creation
	private static SaleDBHandler saleDBHandler = null;
	private DBManager dbManager;
	
	private SaleDBHandler()
	{
		this.dbManager=DBManager.getDBManager();
	}
	
	public static SaleDBHandler getSaleDBHandler()
	{
		if(saleDBHandler == null)
		{
			saleDBHandler = new SaleDBHandler();
		}
		return saleDBHandler;
	}
	
	// Query Methods
	public void saveSale(Sale s) throws ClassNotFoundException, SQLException
	{
		 String insertSaleQuery = "INSERT INTO Sale (saleNumber, complete, dateTime, total) VALUES (?, ?, ?, ?)";
	        String insertMedicineSaleQuery = "INSERT INTO MedicineSale (quantity, medID, saleID) VALUES (?, ?, ?)";

	        Connection connection = dbManager.connect();

	        PreparedStatement saleStmt = connection.prepareStatement(insertSaleQuery);
	        PreparedStatement medicineSaleStmt = connection.prepareStatement(insertMedicineSaleQuery);

	        // Insert Sale
	        saleStmt.setInt(1, s.getSaleNumber());
	        saleStmt.setBoolean(2, s.isComplete());
	        saleStmt.setString(3, s.getDateTime());
	        saleStmt.setFloat(4, s.getTotal());
	        saleStmt.executeUpdate(); // 

	        // Insert MedicineSales
	        for (MedicineSale ms : s.getMedicineSales()) {
	            medicineSaleStmt.setInt(1, ms.getQuantity());
	            medicineSaleStmt.setString(2, ms.getMedicine().getId());
	            medicineSaleStmt.setInt(3, s.getSaleNumber());
	            medicineSaleStmt.executeUpdate(); 
	        }

	        saleStmt.close();
	        medicineSaleStmt.close();
	        connection.close(); 
	}
	
	public ArrayList<Sale> getSales() throws ClassNotFoundException, SQLException
	{
		ArrayList<Sale> salesList = new ArrayList<>();
        String query = "SELECT saleNumber, dateTime, total FROM Sale";
        
        Connection connection = dbManager.connect();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            int saleNumber = rs.getInt("saleNumber");
            String dateTime = rs.getString("dateTime");
            Float total = rs.getFloat("total");

            Sale sale = new Sale();
            sale.setSaleNumber(saleNumber);
            sale.setDateTime(dateTime);
            sale.setTotal(total);

            salesList.add(sale);
        }

        rs.close();
        stmt.close();
        connection.close();

        return salesList;
	}
	
	public Sale getSale(int saleID) throws ClassNotFoundException, SQLException
	{
		Sale sale = null;
        String saleQuery = "SELECT saleNumber, complete, dateTime, total FROM Sale WHERE saleNumber = ?";
        String medicineSaleQuery = "SELECT ms.quantity, ms.medID, m.name, m.quantity, m.price " +
                                   "FROM MedicineSale ms " +
                                   "JOIN Medicine m ON ms.medID = m.id " +
                                   "WHERE ms.saleID = ?";

        Connection connection = dbManager.connect();
        PreparedStatement saleStmt = connection.prepareStatement(saleQuery);
        PreparedStatement medicineSaleStmt = connection.prepareStatement(medicineSaleQuery);

        // Get Sale details
        saleStmt.setInt(1, saleID);
        ResultSet saleRs = saleStmt.executeQuery();
        
        if (saleRs.next()) {
            sale = new Sale();
            sale.setSaleNumber(saleRs.getInt("saleNumber"));
            sale.setComplete(saleRs.getBoolean("complete"));
            sale.setDateTime(saleRs.getString("dateTime"));
            sale.setTotal(saleRs.getFloat("total"));
        }

        // Get MedicineSales and corresponding Medicines
        medicineSaleStmt.setInt(1, saleID);
        ResultSet medicineSaleRs = medicineSaleStmt.executeQuery();

        ArrayList<MedicineSale> medicineSales = new ArrayList<>();
        while (medicineSaleRs.next()) {
            Medicine medicine = new Medicine();
            medicine.setId(medicineSaleRs.getString("medID"));
            medicine.setName(medicineSaleRs.getString("name"));
            medicine.setQuantity(medicineSaleRs.getInt(4));
            medicine.setPrice(medicineSaleRs.getFloat("price"));

            MedicineSale medicineSale = new MedicineSale();
            medicineSale.setQuantity(medicineSaleRs.getInt(1));
            medicineSale.setMedicine(medicine);

            medicineSales.add(medicineSale);
        }

        // Add MedicineSales to the Sale
        if (sale != null) {
            sale.setMedicineSales(medicineSales);
        }

        // Close resources
        saleRs.close();
        medicineSaleRs.close();
        saleStmt.close();
        medicineSaleStmt.close();
        connection.close();

        return sale;
	}
	
	public int getMaxSaleNumber() throws ClassNotFoundException, SQLException
	{
		int maxSaleNumber = 0;
        String query = "SELECT MAX(saleNumber) AS maxSaleNumber FROM Sale";
        
        // Establish connection
        Connection connection = dbManager.connect();
        PreparedStatement stmt = connection.prepareStatement(query);
        
        // Execute query
        ResultSet rs = stmt.executeQuery();
        
        // Retrieve the result
        if (rs.next()) {
            maxSaleNumber = rs.getInt("maxSaleNumber");
        }
        
        // Close resources
        rs.close();
        stmt.close();
        connection.close();
        
        return maxSaleNumber;
	}
	
    
}