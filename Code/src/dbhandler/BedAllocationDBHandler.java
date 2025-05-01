package dbhandler;
import hospital.BedAllocation;
import hospital.*;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class BedAllocationDBHandler
{
	//Single object creation
	private static BedAllocationDBHandler bedAllocationDBHandler = null;
	private DBManager dbManager;
	
	private BedAllocationDBHandler()
	{
		this.dbManager = DBManager.getDBManager();
	}
	
	public static BedAllocationDBHandler getBedAllocationDBHandler()
	{
		if(bedAllocationDBHandler == null)
		{
			bedAllocationDBHandler = new BedAllocationDBHandler();
		}
		return bedAllocationDBHandler;
	}
	
	// Query Methods
	public void saveBedAllocation(BedAllocation bedAllocation) throws ClassNotFoundException, SQLException
	{
		String checkQuery = "SELECT COUNT(*) FROM BedAllocation WHERE bedAllocationNumber = ?";
        String insertBedAllocationQuery = "INSERT INTO BedAllocation (roomNo, bedNo, patCnic, statuss, bedAllocationNumber) VALUES (?, ?, ?, ?, ?)";
        String updateBedAllocationQuery = "UPDATE BedAllocation SET roomNo = ?, bedNo = ?, patCnic = ?, statuss = ? WHERE bedAllocationNumber = ?";
        
        String insertDueQuery = "INSERT INTO Due (bedAllocNo, dueNumber, descriptionn, price, statuss, dateTimee) VALUES (?, ?, ?, ?, ?, ?)";
        String updateDueQuery = "UPDATE Due SET descriptionn = ?, price = ?, statuss = ?, dateTimee = ? WHERE bedAllocNo = ? AND dueNumber = ?";

        try (Connection connection = dbManager.connect()) {
            // Check if the bed allocation already exists
            try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
                checkStmt.setInt(1, bedAllocation.getBedAllocationNumber());
                ResultSet r = checkStmt.executeQuery();
                r.next();
                if (r.getInt(1) > 0) 
                {
                    // If record exists, update it
                    try (PreparedStatement updateStmt = connection.prepareStatement(updateBedAllocationQuery)) {
                        updateStmt.setInt(1, bedAllocation.getRoomNo());
                        updateStmt.setInt(2, bedAllocation.getBedNo());
                        updateStmt.setString(3, bedAllocation.getPatient().getCnic());
                        updateStmt.setString(4, bedAllocation.getStatus());
                        updateStmt.setInt(5, bedAllocation.getBedAllocationNumber());
                        updateStmt.executeUpdate();
                    }

                    // Update dues (existing dues or new ones)
                    for (Due due : bedAllocation.getDues()) {
                        if (dueExists(connection, bedAllocation.getBedAllocationNumber(), due.getDueNumber())) {
                            // Update existing due
                            try (PreparedStatement updateDueStmt = connection.prepareStatement(updateDueQuery)) {
                                updateDueStmt.setString(1, due.getDescription());
                                updateDueStmt.setFloat(2, due.getPrice());
                                updateDueStmt.setString(3, due.getStatus());
                                updateDueStmt.setString(4, due.getDateTime());
                                updateDueStmt.setInt(5, bedAllocation.getBedAllocationNumber());
                                updateDueStmt.setInt(6, due.getDueNumber());
                                updateDueStmt.executeUpdate();
                            }
                        } else {
                            // Insert new due
                            try (PreparedStatement insertDueStmt = connection.prepareStatement(insertDueQuery)) {
                                insertDueStmt.setInt(1, bedAllocation.getBedAllocationNumber());
                                insertDueStmt.setInt(2, due.getDueNumber());
                                insertDueStmt.setString(3, due.getDescription());
                                insertDueStmt.setFloat(4, due.getPrice());
                                insertDueStmt.setString(5, due.getStatus());
                                insertDueStmt.setString(6, due.getDateTime());
                                insertDueStmt.executeUpdate();
                            }
                        }
                    }
                } else {
                    // If record does not exist, insert it
                    try (PreparedStatement insertStmt = connection.prepareStatement(insertBedAllocationQuery)) {
                        insertStmt.setInt(1, bedAllocation.getRoomNo());
                        insertStmt.setInt(2, bedAllocation.getBedNo());
                        insertStmt.setString(3, bedAllocation.getPatient().getCnic());
                        insertStmt.setString(4, bedAllocation.getStatus());
                        insertStmt.setInt(5,bedAllocation.getBedAllocationNumber());
                        /***/
                        System.out.println(bedAllocation.getRoomNo() +" " + bedAllocation.getBedNo() );
                        insertStmt.executeUpdate();
                    }

                    // Insert dues for the new bed allocation
                    for (Due due : bedAllocation.getDues()) {
                        try (PreparedStatement insertDueStmt = connection.prepareStatement(insertDueQuery)) {
                            insertDueStmt.setInt(1, bedAllocation.getBedAllocationNumber());
                            insertDueStmt.setInt(2, due.getDueNumber());
                            insertDueStmt.setString(3, due.getDescription());
                            insertDueStmt.setFloat(4, due.getPrice());
                            insertDueStmt.setString(5, due.getStatus());
                            insertDueStmt.setString(6, due.getDateTime());
                            insertDueStmt.executeUpdate();
                        }
                    }
                }
            }
        }
	}
	
	private boolean dueExists(Connection connection, int bedAllocNo, int dueNumber) throws SQLException 
	{
        String checkDueQuery = "SELECT COUNT(*) FROM Due WHERE bedAllocNo = ? AND dueNumber = ?";
        try (PreparedStatement checkDueStmt = connection.prepareStatement(checkDueQuery)) {
            checkDueStmt.setInt(1, bedAllocNo);
            checkDueStmt.setInt(2, dueNumber);
            try (ResultSet resultSet = checkDueStmt.executeQuery()) {
                return resultSet.next() && resultSet.getInt(1) > 0;
            }
        }
    }
	
	public ArrayList<BedAllocation> getBedAllocations() throws ClassNotFoundException, SQLException
	{
		String query = "SELECT ba.roomNo, ba.bedAllocationNumber, ba.bedNo, p.pname " +
                "FROM BedAllocation ba " +
                "JOIN Patient p ON ba.patCnic = p.cnic " +
                "WHERE ba.statuss = 'Valid'";
		ArrayList<BedAllocation> bedAllocations = new ArrayList<>();

		try (Connection connection = dbManager.connect();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery()) 
		{

			while (resultSet.next()) 
			{
				BedAllocation bedAllocation = new BedAllocation();
				Patient patient = new Patient();

				patient.setName(resultSet.getString("pname"));

				bedAllocation.setRoomNo(resultSet.getInt("roomNo"));
				bedAllocation.setBedAllocationNumber(resultSet.getInt("bedAllocationNumber"));
				bedAllocation.setBedNo(resultSet.getInt("bedNo"));
				bedAllocation.setPatient(patient);

				// Add to the list
				bedAllocations.add(bedAllocation);
			}
 		}

 		return bedAllocations;
	}
	
	
	public BedAllocation getBedAllocation(int bedAllocationID) throws ClassNotFoundException, SQLException
	{
		BedAllocation bedAllocation = null;
        String bedAllocationQuery = "SELECT ba.roomNo, ba.bedNo, ba.bedAllocationNumber, ba.statuss, " +
                                     "p.pname, p.paddress, p.cnic, p.phoneNumber, p.age, p.gender, p.bloodGroup " +
                                     "FROM BedAllocation ba " +
                                     "JOIN Patient p ON ba.patCnic = p.cnic " +
                                     "WHERE ba.bedAllocationNumber = ?";
        String duesQuery = "SELECT dueNumber, descriptionn, price, statuss, dateTimee " +
                           "FROM Due " +
                           "WHERE bedAllocNo = ?";

        try (Connection connection = dbManager.connect()) {

            // Fetch BedAllocation and Patient details
            try (PreparedStatement bedAllocationStmt = connection.prepareStatement(bedAllocationQuery)) {
                bedAllocationStmt.setInt(1, bedAllocationID);
                try (ResultSet bedAllocationRS = bedAllocationStmt.executeQuery()) {
                    if (bedAllocationRS.next()) {
                        bedAllocation = new BedAllocation();
                        Patient patient = new Patient();

                        // Fill patient details
                        patient.setName(bedAllocationRS.getString("pname"));
                        patient.setAddress(bedAllocationRS.getString("paddress"));
                        patient.setCnic(bedAllocationRS.getString("cnic"));
                        patient.setPhoneNumber(bedAllocationRS.getString("phoneNumber"));
                        patient.setAge(bedAllocationRS.getInt("age"));
                        patient.setGender(bedAllocationRS.getString("gender"));
                        patient.setBloodGroup(bedAllocationRS.getString("bloodGroup"));

                        // Fill BedAllocation details
                        bedAllocation.setRoomNo(bedAllocationRS.getInt("roomNo"));
                        bedAllocation.setBedNo(bedAllocationRS.getInt("bedNo"));
                        bedAllocation.setBedAllocationNumber(bedAllocationRS.getInt("bedAllocationNumber"));
                        bedAllocation.setStatus(bedAllocationRS.getString("statuss"));
                        bedAllocation.setPatient(patient);
                        bedAllocation.setDues(new ArrayList<>()); // Initialize dues list
                    }
                }
            }

            // Fetch Dues
            if (bedAllocation != null) {
                try (PreparedStatement duesStmt = connection.prepareStatement(duesQuery)) {
                    duesStmt.setInt(1, bedAllocationID);
                    try (ResultSet duesRS = duesStmt.executeQuery()) {
                        while (duesRS.next()) {
                            Due due = new Due();

                            // Fill Due details
                            due.setDueNumber(duesRS.getInt("dueNumber"));
                            due.setDescription(duesRS.getString("descriptionn"));
                            due.setPrice(duesRS.getFloat("price"));
                            due.setStatus(duesRS.getString("statuss"));
                            due.setDateTime(duesRS.getString("dateTimee"));

                            // Add Due to BedAllocation's dues list
                            bedAllocation.getDues().add(due);
                        }
                    }
                }
            }
        }

        return bedAllocation;
	}
	
	public ArrayList<BedAllocation> getBedAllocationViaPat(String patName) throws ClassNotFoundException, SQLException
	{
		String query = "SELECT ba.roomNo, ba.bedAllocationNumber, ba.bedNo, p.pname " +
                "FROM BedAllocation ba " +
                "JOIN Patient p ON ba.patCnic = p.cnic " +
                "WHERE p.pname like ? AND ba.statuss='Valid'";
		ArrayList<BedAllocation> bedAllocations = new ArrayList<>();

		try (Connection connection = dbManager.connect();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) 
		{

			preparedStatement.setString(1, '%'+patName+'%');

			try (ResultSet resultSet = preparedStatement.executeQuery()) 
			{
				while (resultSet.next()) 
				{
					BedAllocation bedAllocation = new BedAllocation();
					Patient patient = new Patient();

					// Set patient name in Patient object
					patient.setName(resultSet.getString("pname"));

					// Set attributes in BedAllocation object
					bedAllocation.setRoomNo(resultSet.getInt("roomNo"));
					bedAllocation.setBedAllocationNumber(resultSet.getInt("bedAllocationNumber"));
					bedAllocation.setBedNo(resultSet.getInt("bedNo"));
					bedAllocation.setPatient(patient);

					// Add to the list
					bedAllocations.add(bedAllocation);
				}
			}
		}

		return bedAllocations;
	}
	
	public ArrayList<BedAllocation> getBedAllocationViaNurse(String nurseID) throws ClassNotFoundException, SQLException
	{
		String query = "SELECT ba.bedAllocationNumber, ba.bedNo, ba.roomNo, p.pname " +
                "FROM BedAllocation ba " +
                "JOIN Room r ON ba.roomNo = r.roomNo " +
                "JOIN Nurse n ON n.RoomNo = r.roomNo " +
                "JOIN Patient p ON ba.patCnic = p.cnic " +
                "WHERE n.username = ? AND ba.statuss='Valid'";
		ArrayList<BedAllocation> bedAllocations = new ArrayList<>();

		try (Connection connection = dbManager.connect();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) 
		{

			preparedStatement.setString(1, nurseID);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) 
				{
					BedAllocation bedAllocation = new BedAllocation();
					Patient patient = new Patient();

					// Set patient name in Patient object
					patient.setName(resultSet.getString("pname"));

					// Set attributes in BedAllocation object
					bedAllocation.setBedAllocationNumber(resultSet.getInt("bedAllocationNumber"));
					bedAllocation.setBedNo(resultSet.getInt("bedNo"));
					bedAllocation.setRoomNo(resultSet.getInt("roomNo"));
					bedAllocation.setPatient(patient);

					// Add to the list
					bedAllocations.add(bedAllocation);
				}
			}
		}

		return bedAllocations;
	}
	
	public ArrayList<BedAllocation> getBedAllocationViaNurseAndPat(String nurseID, String patName) throws ClassNotFoundException, SQLException
	{
		String query = "SELECT ba.bedAllocationNumber, ba.bedNo, ba.roomNo, p.pname " +
                "FROM BedAllocation ba " +
                "JOIN Room r ON ba.roomNo = r.roomNo " +
                "JOIN Nurse n ON n.RoomNo = r.roomNo " +
                "JOIN Patient p ON ba.patCnic = p.cnic " +
                "WHERE n.username = ? AND ba.statuss='Valid' AND p.pname like ?";
		ArrayList<BedAllocation> bedAllocations = new ArrayList<>();

		try (Connection connection = dbManager.connect();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) 
		{

			preparedStatement.setString(1, nurseID);
			preparedStatement.setString(2, '%'+patName+'%');

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) 
				{
					BedAllocation bedAllocation = new BedAllocation();
					Patient patient = new Patient();

					// Set patient name in Patient object
					patient.setName(resultSet.getString("pname"));

					// Set attributes in BedAllocation object
					bedAllocation.setBedAllocationNumber(resultSet.getInt("bedAllocationNumber"));
					bedAllocation.setBedNo(resultSet.getInt("bedNo"));
					bedAllocation.setRoomNo(resultSet.getInt("roomNo"));
					bedAllocation.setPatient(patient);

					// Add to the list
					bedAllocations.add(bedAllocation);
				}
			}
		}

		return bedAllocations;
	}
	
	public int getMaxBedAllocationNumber() throws ClassNotFoundException, SQLException
	{
		String query = "SELECT MAX(bedAllocationNumber) AS maxNumber FROM BedAllocation";
        int maxNumber = 0;

        try (Connection connection = dbManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) 
        {

            if (resultSet.next()) 
            {
                maxNumber = resultSet.getInt("maxNumber");
            }
        }

        return maxNumber;
	}
	
	public boolean isPendingBedAllocation(String patNic) throws ClassNotFoundException, SQLException
	{
		String query = "SELECT 1 " +
                "FROM BedAllocation " +
                "WHERE patCnic = ? AND statuss = 'Valid'";
		boolean exists = false;

		try (Connection connection = dbManager.connect();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) 
		{

			preparedStatement.setString(1, patNic);

			try (ResultSet resultSet = preparedStatement.executeQuery()) 
			{
				if (resultSet.next()) 
				{
					exists = true;
				}
			}
		}

		return exists;
	}

	
    
}