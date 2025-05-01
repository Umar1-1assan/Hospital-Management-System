package dbhandler;
import hospital.LabTechnician;
import hospital.LabVisit;
import hospital.Patient;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
public class LabVisitDBHandler
{
	//Single object creation
	private static LabVisitDBHandler labVisitDBHandler = null;
	private DBManager dbManager;
	
	private LabVisitDBHandler()
	{
		this.dbManager=DBManager.getDBManager();
	}
	
	public static LabVisitDBHandler getLabVisitDBHandler()
	{
		if(labVisitDBHandler == null)
		{
			labVisitDBHandler = new LabVisitDBHandler();
		}
		return labVisitDBHandler;
	}
	
	// Query Methods
	public int getMaxVisitNo() throws ClassNotFoundException, SQLException
	{
		String query = "SELECT MAX(visitNo) AS maxVisitNo FROM LabVisit";
        int maxVisitNo = 0;

        try (Connection connection = dbManager.connect();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                maxVisitNo = resultSet.getInt("maxVisitNo");
            }
        }

        return maxVisitNo;
	}
	
	public int getMaxSampleNo() throws ClassNotFoundException, SQLException
	{
		String query = "SELECT MAX(sampleNumber) AS maxVisitNo FROM LabVisit";
        int maxVisitNo = 0;

        try (Connection connection = dbManager.connect();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                maxVisitNo = resultSet.getInt("maxVisitNo");
            }
        }

        return maxVisitNo;
	}
	
	public ArrayList<LabVisit> getVisits(String patName) throws ClassNotFoundException, SQLException
	{
		String query = "SELECT lv.visitNo, lv.dateTimee, lv.testName, p.pname AS patientName " +
                "FROM LabVisit lv " +
                "JOIN Patient p ON lv.patCnic = p.cnic " +
                "WHERE p.pname like ?";
		ArrayList<LabVisit> visits = new ArrayList<>();

		try (Connection connection = dbManager.connect();
				PreparedStatement statement = connection.prepareStatement(query)) 
		{

			statement.setString(1, '%'+patName+'%');
			try (ResultSet resultSet = statement.executeQuery()) 
			{
				while (resultSet.next()) 
				{
					LabVisit visit = new LabVisit();
					visit.setVisitNo(resultSet.getInt("visitNo"));
					visit.setDateTime(resultSet.getString("dateTimee"));
					visit.setTestName(resultSet.getString("testName"));

					Patient patient = new Patient();
					patient.setName(resultSet.getString("patientName"));
					visit.setPatient(patient);

					visits.add(visit);
				}
			}
		}

		return visits;
	}
	//public ArrayList<LabVisit> getVisits()
	//{
		//return null;
	//}
	public ArrayList<LabVisit> getVisits() throws ClassNotFoundException, SQLException
	{
		String query = "SELECT lv.visitNo, lv.dateTimee, lv.testName, p.pname AS patientName " +
                "FROM LabVisit lv " +
                "JOIN Patient p ON lv.patCnic = p.cnic ";
		ArrayList<LabVisit> visits = new ArrayList<>();

		try (Connection connection = dbManager.connect();
				PreparedStatement statement = connection.prepareStatement(query)) 
		{

			try (ResultSet resultSet = statement.executeQuery()) 
			{
				while (resultSet.next()) 
				{
					LabVisit visit = new LabVisit();
					visit.setVisitNo(resultSet.getInt("visitNo"));
					visit.setDateTime(resultSet.getString("dateTimee"));
					visit.setTestName(resultSet.getString("testName"));

					Patient patient = new Patient();
					patient.setName(resultSet.getString("patientName"));
					visit.setPatient(patient);

					visits.add(visit);
				}
			}
		}

		return visits;
	}
	public LabVisit getVisit(int visitNo) throws ClassNotFoundException, SQLException
	{
		String query = "SELECT lv.visitNo, lv.dateTimee, lv.testName, lv.report, lv.statuss, lv.sampleNumber, " +
                "p.pname AS patientName, p.cnic AS patientCnic, p.paddress AS patientAddress, " +
                "p.phoneNumber AS patientPhone, p.age AS patientAge, p.gender AS patientGender, " +
                "lt.lname AS labTechnicianName, lt.username AS labTechnicianUsername, lt.workExperience " +
                "FROM LabVisit lv " +
                "JOIN Patient p ON lv.patCnic = p.cnic " +
                "JOIN LabTechnician lt ON lv.labTechUsername = lt.username " +
                "WHERE lv.visitNo = ?";
 
		LabVisit visit = null;

		try (Connection connection = dbManager.connect();
				PreparedStatement statement = connection.prepareStatement(query)) 
		{

			statement.setInt(1, visitNo);
			try (ResultSet resultSet = statement.executeQuery()) 
			{
				if (resultSet.next()) 
				{
					visit = new LabVisit();
					visit.setVisitNo(resultSet.getInt("visitNo"));
					visit.setDateTime(resultSet.getString("dateTimee"));
					visit.setTestName(resultSet.getString("testName"));
					visit.setReport(resultSet.getString("report"));
					visit.setStatus(resultSet.getString("statuss"));
					visit.setSampleNumber(resultSet.getInt("sampleNumber"));

					// Populate Patient details
					Patient patient = new Patient();
					patient.setName(resultSet.getString("patientName"));
					patient.setCnic(resultSet.getString("patientCnic"));
					patient.setAddress(resultSet.getString("patientAddress"));
					patient.setPhoneNumber(resultSet.getString("patientPhone"));
					patient.setAge(resultSet.getInt("patientAge"));
					patient.setGender(resultSet.getString("patientGender"));
					visit.setPatient(patient);

					// Populate LabTechnician details
					LabTechnician technician = new LabTechnician();
					technician.setName(resultSet.getString("labTechnicianName"));
					technician.setUsername(resultSet.getString("labTechnicianUsername"));
					technician.setWorkExperience(resultSet.getInt("workExperience"));
					visit.setLabTechnician(technician);
				}
			}
		}

 		return visit;
	}
	public boolean saveVisit(LabVisit visit) throws ClassNotFoundException, SQLException
	{
	        String query = "INSERT INTO LabVisit (visitNo, dateTimee, testName, report, statuss, sampleNumber, patCnic, labTechUsername) " +
	                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

	        try (Connection connection = dbManager.connect();
	             PreparedStatement statement = connection.prepareStatement(query)) 
	        {

	            // Set parameters
	            statement.setInt(1, visit.getVisitNo());
	            statement.setString(2, visit.getDateTime());
	            statement.setString(3, visit.getTestName());
	            statement.setString(4, visit.getReport());
	            statement.setString(5, visit.getStatus());
	            statement.setInt(6, visit.getSampleNumber());
	            statement.setString(7, visit.getPatient().getCnic());
	            statement.setString(8, visit.getLabTechnician().getUsername());

	            // Execute query
	            statement.executeUpdate();
	        }
	        return true;
	    } 
}