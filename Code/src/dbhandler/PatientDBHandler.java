package dbhandler;
import hospital.Patient;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
public class PatientDBHandler
{
	//Single object creation
	private static PatientDBHandler patientDBHandler = null;
	private DBManager dbManager;
	
	private PatientDBHandler( )
	{
		this.dbManager=DBManager.getDBManager();
	}
	
	public static PatientDBHandler getpatientDBHandler()
	{
		if(patientDBHandler == null)
		{
			patientDBHandler = new PatientDBHandler();
		}
		return patientDBHandler;
	}
	
	// Query Methods
	//public void registerPatient(Patient p)
	//{
		
	//}
	
	public boolean savePatient(Patient p) throws ClassNotFoundException, SQLException
	{
		String checkQuery = "SELECT COUNT(*) FROM Patient WHERE cnic = ?";
        String insertQuery = "INSERT INTO Patient (pname, paddress, cnic, phoneNumber, age, gender, bloodGroup) " +
                             "VALUES (?, ?, ?, ?, ?, ?, ?)";
        String updateQuery = "UPDATE Patient SET pname = ?, paddress = ?, phoneNumber = ?, age = ?, " +
                             "gender = ?, bloodGroup = ? WHERE cnic = ?";

        try (Connection connection = dbManager.connect();
             PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {

            // Check if patient exists
            checkStatement.setString(1, p.getCnic());
            ResultSet resultSet = checkStatement.executeQuery();
            resultSet.next();
            boolean patientExists = resultSet.getInt(1) > 0;

            if (patientExists) {
                // Update existing patient
                try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                    updateStatement.setString(1, p.getName());
                    updateStatement.setString(2, p.getAddress());
                    updateStatement.setString(3, p.getPhoneNumber());
                    updateStatement.setInt(4, p.getAge());
                    updateStatement.setString(5, p.getGender());
                    updateStatement.setString(6, p.getBloodGroup());
                    updateStatement.setString(7, p.getCnic());

                    int rowsUpdated = updateStatement.executeUpdate();
                    return rowsUpdated > 0;
                }
            } else {
                // Insert new patient
                try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                    insertStatement.setString(1, p.getName());
                    insertStatement.setString(2, p.getAddress());
                    insertStatement.setString(3, p.getCnic());
                    insertStatement.setString(4, p.getPhoneNumber());
                    insertStatement.setInt(5, p.getAge());
                    insertStatement.setString(6, p.getGender());
                    insertStatement.setString(7, p.getBloodGroup());

                    int rowsInserted = insertStatement.executeUpdate();
                    return rowsInserted > 0;
                }
            }
        }
	}
	
	public ArrayList<Patient> getPatients() throws ClassNotFoundException, SQLException
	{
		String query = "SELECT pname, cnic, bloodGroup, age, gender FROM Patient";
        ArrayList<Patient> patients = new ArrayList<>();

        try (Connection connection = dbManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Patient patient = new Patient();
                patient.setName(resultSet.getString("pname"));
                patient.setCnic(resultSet.getString("cnic"));
                patient.setBloodGroup(resultSet.getString("bloodGroup"));
                patient.setAge(resultSet.getInt("age"));
                patient.setGender(resultSet.getString("gender"));

                patients.add(patient);
            }
        }

        return patients;
	}
	
	public Patient getPatient(String cnic) throws ClassNotFoundException, SQLException
	{
		String query = "SELECT * FROM Patient where cnic = ?";
		Connection conn = this.dbManager.connect();
		PreparedStatement st = conn.prepareStatement(query);
		st.setString(1, cnic);
		
		ResultSet r = st.executeQuery();
		if(!r.next())
		{
			r.close();
			st.close();
			conn.close();
			return null;
		}
		
		Patient p = new Patient();
		p.setAddress(r.getString("paddress"));
		p.setAge(r.getInt("age"));
		p.setBloodGroup(r.getString("bloodGroup"));
		p.setCnic(cnic);
		p.setGender(r.getString("gender"));
		p.setName(r.getString("pname"));
		p.setPhoneNumber(r.getString("phoneNumber"));
		
		r.close();
		st.close();
		conn.close();

        return p;
	}
	public ArrayList<Patient> getPatientViaName(String name) throws ClassNotFoundException, SQLException
	{
		String query = "SELECT pname, cnic, bloodGroup, age, gender FROM Patient WHERE pname like ?";
        ArrayList<Patient> patients = new ArrayList<>();

        try (Connection connection = dbManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, '%'+name+'%');

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Patient patient = new Patient();
                    patient.setName(resultSet.getString("pname"));
                    patient.setCnic(resultSet.getString("cnic"));
                    patient.setBloodGroup(resultSet.getString("bloodGroup"));
                    patient.setAge(resultSet.getInt("age"));
                    patient.setGender(resultSet.getString("gender"));

                    patients.add(patient);
                }
            }
        }

        return patients;
	}
	
	public Patient getPatientViaAdmNo(int admReqNo) throws ClassNotFoundException, SQLException
	{
		  String query = "SELECT p.pname, p.cnic, p.bloodGroup, p.age, p.gender " +
                  "FROM Patient p " +
                  "JOIN AdmissionRequest ar ON p.cnic = ar.patCnic " +
                  "WHERE ar.requestNumber = ?";
   Patient patient = null;

   try (Connection connection = dbManager.connect();
        PreparedStatement preparedStatement = connection.prepareStatement(query)) {

       preparedStatement.setInt(1, admReqNo);

       try (ResultSet resultSet = preparedStatement.executeQuery()) {
           if (resultSet.next()) {
               patient = new Patient();
               patient.setName(resultSet.getString("pname"));
               patient.setCnic(resultSet.getString("cnic"));
               patient.setBloodGroup(resultSet.getString("bloodGroup"));
               patient.setAge(resultSet.getInt("age"));
               patient.setGender(resultSet.getString("gender"));
           }
       }
   }

   return patient; 
	}
	
	public boolean exists(String username) throws ClassNotFoundException, SQLException
	{
		String query = "SELECT COUNT(*) FROM Patient WHERE cnic = ? ;";
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