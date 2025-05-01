package application.Receptionist;

import java.sql.SQLException;

import hospital.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import manager.AdmitPatientManager;
import manager.DischargePatientManager;
import manager.PatientVisitManager;

interface ReceptionistControllerIF
{
	 public User getUser();

	public void setUser(User user);

	public PatientVisitManager getPatientVisitManager();

	public void setPatientVisitManager(PatientVisitManager patientVisitManager);

	public AdmitPatientManager getAdmitPatientManager();

	public void setAdmitPatientManager(AdmitPatientManager admitPatientManager);

	public DischargePatientManager getDischargePatientManager();

	public void setDischargePatientManager(DischargePatientManager dischargePatientManager);
	
	public void loadDetails() throws ClassNotFoundException, SQLException;
	
	default void showAlert(String title, String text)
	{
		Alert successAlert = new Alert(AlertType.INFORMATION);
        successAlert.setTitle(title);
        successAlert.setHeaderText(null);
        successAlert.setContentText(text);
        successAlert.showAndWait();
	}
}
