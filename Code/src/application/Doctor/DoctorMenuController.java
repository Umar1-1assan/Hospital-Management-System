package application.Doctor;

import java.io.IOException;
import java.sql.SQLException;

import hospital.Doctor;
import hospital.Receptionist;
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
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import manager.CheckupPatientManager;

public class DoctorMenuController implements DoctorControllerIF
{
	private CheckupPatientManager checkUpPatientManager;
	private User user;
	
	

    public CheckupPatientManager getCheckupPatientManager() {
		return checkUpPatientManager;
	}

	public void setCheckupPatientManager( CheckupPatientManager checkUpPatientManager) {
		this.checkUpPatientManager = checkUpPatientManager;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
    @FXML
    private Button Admit_Request_btn;

    @FXML
    private Button Discharge_Request_btn;

    @FXML
    private Button Home_btn;

    @FXML
    private Rectangle Image;

    @FXML
    private Button LogOut_btn;

    @FXML
    private TextField Phone;

    @FXML
    private TextField Speciality_f;

    @FXML
    private Button View_Queue_btn;

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
    private VBox vb;

    @FXML
    void LogOut(MouseEvent event) throws IOException 
    {
    	FXMLLoader loader;
    	Parent parent;
    	loader = new FXMLLoader(getClass().getResource("/application/Login.fxml"));
        parent = loader.load();
        Stage stage = (Stage) this.LogOut_btn.getScene().getWindow();
        stage.setScene(new Scene(parent));
    }


    @FXML
    void Admit_Request(MouseEvent event) throws ClassNotFoundException, SQLException {
    	loadPage("Admit_Request");
    }

    @FXML
    void Discharge_Request(MouseEvent event) throws ClassNotFoundException, SQLException {
    	loadPage("Discharge_Request");
    }
    
    @FXML
    void Doctor_menu(MouseEvent event) {
    	bp.setCenter (ap);
    }
    
    @FXML
    void View_Queue(MouseEvent event) throws ClassNotFoundException, SQLException {
    	loadPage("View_Queue");
    	
    }
    
    private void loadPage (String page) throws ClassNotFoundException, SQLException 
   	{
    	 Parent root = null;
         FXMLLoader loader = new FXMLLoader(getClass().getResource(page + ".fxml"));
         try {
             root = loader.load();
             if (page.equals("View_Queue")) 
             {
                 ViewQueueController Controller = loader.getController();
                 Controller.initialize(bp); 
             }
             DoctorControllerIF controller = loader.getController();
             controller.setCheckupPatientManager(checkUpPatientManager);
             controller.setUser(user);
             controller.loadDetails();
         } catch (IOException ex) {
             ex.printStackTrace(); 
         }
         bp.setCenter(root);
   	}

    @Override
	public void loadDetails() throws ClassNotFoundException, SQLException 
    {
    	Doctor d = this.checkUpPatientManager.showProfile(this.user.getUsername());
    	//r.print();
    	
    	if(d!=null)
    	{
    		this.ussername_f .setText(d.getUsername());
    		this.name_f.setText(d.getName());
    		this.age_f .setText(Integer.toString(d.getAge()));
    		this.Speciality_f .setText((d.getSpeciality()));
    		this.Phone.setText(d.getPhoneNumber());
    		this.gender_f .setText(d.getGender());
    		this.addr_f .setText(d.getAddress());
    		this.cnic_f.setText(d.getCnic());
    	}
	}


}
