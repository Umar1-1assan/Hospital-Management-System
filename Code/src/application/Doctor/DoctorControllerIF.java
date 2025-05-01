package application.Doctor;

import java.sql.SQLException;

import hospital.User;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import manager.CheckupPatientManager;

public interface DoctorControllerIF 
{
	public CheckupPatientManager getCheckupPatientManager();
	public void setCheckupPatientManager(CheckupPatientManager c);
	public User getUser();
	public void setUser(User u);
	default void showAlert(String title, String text)
	{
		Alert successAlert = new Alert(AlertType.INFORMATION);
        successAlert.setTitle(title);
        successAlert.setHeaderText(null);
        successAlert.setContentText(text);
        successAlert.showAndWait();
	}
	public void loadDetails() throws ClassNotFoundException, SQLException;
}
