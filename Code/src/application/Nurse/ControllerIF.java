package application.Nurse;

import java.sql.SQLException;
import hospital.User;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import manager.NurseManager;

public interface ControllerIF 
{
	public void setNurseManager(NurseManager n);
	public NurseManager getNurseManager();
	public void setUser(User u);
	public User getUser();
	public void loadDetails()  throws ClassNotFoundException, SQLException ;
	default void showAlert(String title, String text)
	{
		Alert successAlert = new Alert(AlertType.INFORMATION);
        successAlert.setTitle(title);
        successAlert.setHeaderText(null);
        successAlert.setContentText(text);
        successAlert.showAndWait();
	}
	public void setB_pane(BorderPane p);

}
