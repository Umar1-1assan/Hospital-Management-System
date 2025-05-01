package application.Admin;

import java.sql.SQLException;

import hospital.User;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import manager.AdminManager;

public interface CotrollerIF 
{
	void setAdminManager(AdminManager a);
	AdminManager getAdminManager();
	void loadDeatails() throws ClassNotFoundException, SQLException ;
	void setUser(User u);
	User getUser();
	default void showAlert(String title, String text)
	{
		Alert successAlert = new Alert(AlertType.INFORMATION);
        successAlert.setTitle(title);
        successAlert.setHeaderText(null);
        successAlert.setContentText(text);
        successAlert.showAndWait();
	}

}
