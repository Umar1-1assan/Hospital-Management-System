package application;
import application.Receptionist.*;
import dbhandler.DBManager;
import hospital.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class LoginController 
{
	

    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private void handleLoginButtonAction() throws IOException, SQLException, ClassNotFoundException 
    {
    	User user = new User();
    	/*
    	if(this.usernameField.getText().charAt(0) == 'r')
    	{
    		user.setUsername("r-ali002");
    		user.setPassword("password456");
    	}
    	else if(this.usernameField.getText().charAt(0) == 'd')
    	{
    	user.setUsername("d-ahmed001");
    	user.setPassword("password123");
    	}
    	else if(this.usernameField.getText().charAt(0) == 'l')
    	{
    		user.setUsername("l-asma003");
    		user.setPassword("password123");
    	}
    	else if(this.usernameField.getText().charAt(0) == 'n')
    	{
    		user.setUsername("n-ali001");
    		user.setPassword("password123");
    	}
    	else if(this.usernameField.getText().charAt(0) == 'p')
    	{
    		user.setUsername("p-adil002");
    		user.setPassword("password456");
    	}
    	else
    	{
    		user.setUsername("a-admin1");
    		user.setPassword("admin1");
    	}
    	*/

    	user.setUsername(this.usernameField.getText());
    	user.setPassword(this.passwordField.getText());
   
    	if(!this.authenticate(user))
    	{
    		this.showAlert("Login Failed", "Incorrect username of password");
    		return;
    	}
    	Parent parent = GUIFactory.getFactory().loadMenu(user);
    	Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.setScene(new Scene(parent));
    }
    
    public void showAlert(String title, String text)
	{
		Alert successAlert = new Alert(AlertType.INFORMATION);
        successAlert.setTitle(title);
        successAlert.setHeaderText(null);
        successAlert.setContentText(text);
        successAlert.showAndWait();
	}
    
    public boolean authenticate(User u) throws SQLException, ClassNotFoundException {
        Connection conn = DBManager.getDBManager().connect();
        PreparedStatement stmt = null;
        ResultSet rs = null;


        String username = u.getUsername();
        String password = u.getPassword();

        String table = "";
        if (username.startsWith("d")) {
            table = "Doctor";
        } else if (username.startsWith("n")) {
            table = "Nurse";
        } else if (username.startsWith("a")) {
            table = "Admin";
        } else if (username.startsWith("l")) {
            table = "LabTechnician";
        } else if (username.startsWith("r")) {
            table = "Receptionist";
        } else if(username.startsWith("p")){
        	table="Pharmacist";
        }
        else {
            this.showAlert("Login Failed", "Incorrect username of password");
            return false;
        }


        String query = "SELECT * FROM " + table + " WHERE username = ? AND passwordd = ?";
        
        // Prepare statement and execute query
        stmt = conn.prepareStatement(query);
        stmt.setString(1, username);
        stmt.setString(2, password);
        
        rs = stmt.executeQuery();

        if (rs.next()) {
            return true;
        } else {
            return false;
        }
    }

}
