package application.LabTechnician;

import java.sql.SQLException;

import hospital.User;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import manager.PerformTestManager;

public interface LabTechnicianControllerIF 
{
	public void setPerformTestManager(PerformTestManager p);
	public PerformTestManager getPerformTestManager();
	public void setUser(User u);
	public User getUser();
	public void loadDetails()throws ClassNotFoundException, SQLException ;
	default void showAlert(String title, String text)
	{
		Alert successAlert = new Alert(AlertType.INFORMATION);
        successAlert.setTitle(title);
        successAlert.setHeaderText(null);
        successAlert.setContentText(text);
        successAlert.showAndWait();
	}

}
