package dbhandler;
import hospital.Doctor;
import hospital.DoctorVisit;
import hospital.Patient;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
public class DoctorVisitDBHandler
{
	//Single object creation
	private static DoctorVisitDBHandler doctorVisitDBHandler = null;
	private DBManager dbManager;
	
	private DoctorVisitDBHandler()
	{
		this.dbManager=DBManager.getDBManager();
	}
	
	public static DoctorVisitDBHandler getDoctorVisitDBHandler()
	{
		if(doctorVisitDBHandler == null)
		{
			doctorVisitDBHandler = new DoctorVisitDBHandler();
		}
		return doctorVisitDBHandler;
	}
	
	// Query Methods
	public int getMaxVisitNo() throws ClassNotFoundException, SQLException
	{
		String query = "SELECT MAX(visitNo) FROM DoctorVisit";
        
        try (Connection connection = dbManager.connect();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) 
        {
            
            if (resultSet.next()) 
            {
                return resultSet.getInt(1);  // Return the max visitNo
            }
        }
        
        return 0;
	}
	
	public ArrayList<DoctorVisit> getVisitHistory(String patientID) throws ClassNotFoundException, SQLException
	{
		String query = "SELECT dv.visitNo, dv.dateTimee, dv.medicalIssue, p.pname AS patientName, d.dname AS doctorName " +
                "FROM DoctorVisit dv " +
                "JOIN Patient p ON dv.patCnic = p.cnic " +
                "JOIN Doctor d ON dv.docUsername = d.username " +
                "WHERE dv.patCnic = ?";

		ArrayList<DoctorVisit> visits = new ArrayList<>();

		try (Connection connection = dbManager.connect();
				PreparedStatement statement = connection.prepareStatement(query)) 
		{

			statement.setString(1, patientID);
			try (ResultSet resultSet = statement.executeQuery()) 
			{
				while (resultSet.next()) 
				{
					DoctorVisit visit = new DoctorVisit();

					// Setting attributes
					visit.setDateTime(resultSet.getString("dateTimee"));
					visit.setMedicalIssue(resultSet.getString("medicalIssue"));
					visit.setVisitNo(resultSet.getInt("visitNo"));
					// Setting Patient object
					Patient patient = new Patient();
					patient.setName(resultSet.getString("patientName"));
					visit.setPatient(patient);

					// Setting Doctor object
					Doctor doctor = new Doctor();
					doctor.setName(resultSet.getString("doctorName"));
					visit.setDoctor(doctor);

					visits.add(visit);
				}
			}
		}

		return visits;
	}
	//public ArrayList<DoctorVisit> getAll()
	//{
		//return null;
	//}
	public DoctorVisit getVisit(int visitNo) throws ClassNotFoundException, SQLException
	{
		 String query = "SELECT dv.visitNo, dv.dateTimee, dv.medicalIssue, dv.statuss, dv.prescription, " +
                 "p.cnic AS patientCnic, p.pname AS patientName, p.paddress AS patientAddress, " +
                 "p.phoneNumber AS patientPhone, p.age AS patientAge, p.gender AS patientGender, p.bloodGroup AS patientBloodGroup, " +
                 "d.username AS doctorUsername, d.dname AS doctorName, d.daddress AS doctorAddress, " +
                 "d.phoneNumber AS doctorPhone, d.age AS doctorAge, d.gender AS doctorGender " +
                 "FROM DoctorVisit dv " +
                 "JOIN Patient p ON dv.patCnic = p.cnic " +
                 "JOIN Doctor d ON dv.docUsername = d.username " +
                 "WHERE dv.visitNo = ?";

		 DoctorVisit visit = null;

	try (Connection connection = dbManager.connect();
       PreparedStatement statement = connection.prepareStatement(query)) {

      statement.setInt(1, visitNo);

      try (ResultSet resultSet = statement.executeQuery()) {
          if (resultSet.next()) {
              visit = new DoctorVisit();

              // Set DoctorVisit attributes
              visit.setVisitNo(resultSet.getInt("visitNo"));
              visit.setDateTime(resultSet.getString("dateTimee"));
              visit.setMedicalIssue(resultSet.getString("medicalIssue"));
              visit.setStatus(resultSet.getString("statuss"));
              visit.setPrescription(resultSet.getString("prescription"));

              // Set Patient attributes
              Patient patient = new Patient();
              patient.setCnic(resultSet.getString("patientCnic"));
              patient.setName(resultSet.getString("patientName"));
              patient.setAddress(resultSet.getString("patientAddress"));
              patient.setPhoneNumber(resultSet.getString("patientPhone"));
              patient.setAge(resultSet.getInt("patientAge"));
              patient.setGender(resultSet.getString("patientGender"));
              patient.setBloodGroup(resultSet.getString("patientBloodGroup"));
              visit.setPatient(patient);

              // Set Doctor attributes
              Doctor doctor = new Doctor();
              doctor.setUsername(resultSet.getString("doctorUsername"));
              doctor.setName(resultSet.getString("doctorName"));
              doctor.setAddress(resultSet.getString("doctorAddress"));
              doctor.setPhoneNumber(resultSet.getString("doctorPhone"));
              doctor.setAge(resultSet.getInt("doctorAge"));
              doctor.setGender(resultSet.getString("doctorGender"));
              visit.setDoctor(doctor);
          }
      }
  }

  		return visit;
	}
	public void saveVisit(DoctorVisit visit) throws ClassNotFoundException, SQLException
	{
		 String insertQuery = "INSERT INTO DoctorVisit (visitNo, dateTimee, medicalIssue, statuss, prescription, patCnic, docUsername) " +
                 "VALUES (?, ?, ?, ?, ?, ?, ?)";

		 try (Connection connection = dbManager.connect();
				 PreparedStatement statement = connection.prepareStatement(insertQuery)) 
		 {

			 // Setting parameters
			 statement.setInt(1, visit.getVisitNo());
			 statement.setString(2, visit.getDateTime());
			 statement.setString(3, visit.getMedicalIssue());	
			 statement.setString(4, visit.getStatus());
			 statement.setString(5, visit.getPrescription());
			 statement.setString(6, visit.getPatient().getCnic());
			 statement.setString(7, visit.getDoctor().getUsername());

			 // Execute the query
			 statement.executeUpdate();
		 }
	}
    
}