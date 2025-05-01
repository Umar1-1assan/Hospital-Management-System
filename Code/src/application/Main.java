package application;
	
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.layout.BorderPane;


public class Main extends Application 
{
	@Override
	public void start(Stage primaryStage) 
	{
		try {
			 // Load the FXML file
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
	        Parent root = loader.load();
	        primaryStage.setScene(new Scene(root));
	        primaryStage.show();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
}
	
	/*
	 * public class Login_controller {
	 * 
	 * @FXML private void handleLoginButtonAction() { String username =
	 * usernameField.getText(); System.out.println("Logging in with username: " +
	 * username); // Add additional logic here } }
	 */
}
