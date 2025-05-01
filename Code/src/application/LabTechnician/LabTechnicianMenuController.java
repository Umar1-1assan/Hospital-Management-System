package application.LabTechnician;

import java.io.IOException;
import java.sql.SQLException;

import application.Doctor.ShowPrevVisitController;
import application.Doctor.ViewQueueController;
import hospital.Doctor;
import hospital.LabTechnician;
import hospital.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import manager.PerformTestManager;

public class LabTechnicianMenuController implements LabTechnicianControllerIF 
{

	User user;
	PerformTestManager performTestManager;

    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public PerformTestManager getPerformTestManager() {
		return performTestManager;
	}

	public void setPerformTestManager(PerformTestManager performTestManager) {
		this.performTestManager = performTestManager;
	}


    @FXML
    private Button Gen_Report;

    @FXML
    private Button Home;

    @FXML
    private Rectangle Image;

    @FXML
    private Button LogOut;

    @FXML
    private TextField Phone;

    @FXML
    private TextField Speciality_f;

    @FXML
    private Button ViewQueue;

    @FXML
    private TextField addr_f;

    @FXML
    private TextField age_f;

    @FXML
    private AnchorPane ap;

    @FXML
    private BorderPane bp;

    @FXML
    private TextField cnic_f;

    @FXML
    private TextField gender_f;

    @FXML
    private TextField name_f;

    @FXML
    private TextField ussername_f;
    

    @FXML
    void LogOut(MouseEvent event) throws IOException
    {
    	FXMLLoader loader;
    	Parent parent;
    	loader = new FXMLLoader(getClass().getResource("/application/Login.fxml"));
        parent = loader.load();
        Stage stage = (Stage) this.LogOut.getScene().getWindow();
        stage.setScene(new Scene(parent));
    }


    @FXML
    void Gen_Report(MouseEvent event) throws ClassNotFoundException, IOException, SQLException {
    	loadPage("lab_reports");
    }

    @FXML
    void LabTech_menu(MouseEvent event) {
    	bp.setCenter (ap);
    }

    @FXML
    void ViewQueue(MouseEvent event) throws IOException, ClassNotFoundException, SQLException {
    	loadPage("ViewQueue");
    }
    
    private void loadPage (String page) throws IOException, ClassNotFoundException, SQLException
	{
    	 Parent root = null;
         FXMLLoader loader = new FXMLLoader(getClass().getResource(page + ".fxml"));

         root = loader.load();
         if (page.equals("ViewQueue")) 
         {
        	 View_QueueController Controller = loader.getController();
        	 Controller.initialize(bp); 
         }
         else if(page.equals("lab_reports"))
         {
        	 LabReportsController controller = loader.getController();
        	 controller.setB_pane(bp);
         }
         LabTechnicianControllerIF controller = loader.getController();
         controller.setUser(user);
         controller.setPerformTestManager(performTestManager);
         controller.loadDetails();
         bp.setCenter(root);
	}
    
    public void loadDetails() throws ClassNotFoundException, SQLException 
    {
    	LabTechnician d = this.performTestManager.showProfile(this.user.getUsername());
    	
    	if(d!=null)
    	{
    		this.ussername_f .setText(d.getUsername());
    		this.name_f.setText(d.getName());
    		this.age_f .setText(Integer.toString(d.getAge()));
    		this.Speciality_f .setText(Integer.toString(d.getWorkExperience()));
    		this.Phone.setText(d.getPhoneNumber());
    		this.gender_f .setText(d.getGender());
    		this.addr_f .setText(d.getAddress());
    		this.cnic_f.setText(d.getCnic());
    	}
	}

}
