 
package application.Receptionist;
import java.io.IOException;
import java.sql.SQLException;
import hospital.Receptionist;
import hospital.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import manager.AdmitPatientManager;
import manager.DischargePatientManager;
import manager.PatientVisitManager;

public class Receptionist_menuController implements ReceptionistControllerIF
{
	private User user;
	private PatientVisitManager patientVisitManager;
	private AdmitPatientManager admitPatientManager;
	private DischargePatientManager dischargePatientManager;
	
	
    public User getUser() {
		return user;
	}

	public void setUser(User user) 
	{
		this.user = user;
	}

	public PatientVisitManager getPatientVisitManager() {
		return patientVisitManager;
	}

	public void setPatientVisitManager(PatientVisitManager patientVisitManager) {
		this.patientVisitManager = patientVisitManager;
	}

	public AdmitPatientManager getAdmitPatientManager() {
		return admitPatientManager;
	}

	public void setAdmitPatientManager(AdmitPatientManager admitPatientManager) {
		this.admitPatientManager = admitPatientManager;
	}

	public DischargePatientManager getDischargePatientManager() {
		return dischargePatientManager;
	}

	public void setDischargePatientManager(DischargePatientManager dischargePatientManager) {
		this.dischargePatientManager = dischargePatientManager;
	}
	
    public void loadDetails() throws ClassNotFoundException, SQLException
    {
    	Receptionist r = this.patientVisitManager.showProfile(this.user.getUsername());
    	//r.print();
    	
    	if(r!=null)
    	{
    		this.username.setText(r.getUsername());
    		this.name.setText(r.getName());
    		this.age.setText(Integer.toString(r.getAge()));
    		this.Experience.setText(Integer.toString(r.getWorkExperience()));
    		this.Phone.setText(r.getPhoneNumber());
    		this.Gender.setText(r.getGender());
    		this.Addr.setText(r.getAddress());
    	}
    }


    @FXML
    private AnchorPane AP;

    @FXML
    private TextField Addr;

    @FXML
    private BorderPane BP;

    @FXML
    private TextField Experience;

    @FXML
    private TextField Gender;

    @FXML
    private Label GenderLabel;

    @FXML
    private Button Home;

    @FXML
    private Rectangle Image;

    @FXML
    private Button LogOut;

    @FXML
    private TextField Phone;

    @FXML
    private Button admit_patient;

    @FXML
    private TextField age;

    @FXML
    private Button discharge_patient;

    @FXML
    private Button doctor_visit;

    @FXML
    private Button lab_visit;

    @FXML
    private TextField name;

    @FXML
    private Button pay_dues;

    @FXML
    private Button register_patient;

    @FXML
    private TextField username;
    
    @FXML
    private void initialize()
    {
    }

    
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
    void Receptionist_menu(MouseEvent event) {
    	BP.setCenter (AP);
    }

    @FXML
    void admit_patient(MouseEvent event) throws ClassNotFoundException, SQLException {
    	loadPage("admit_patient");
    }

    @FXML
    void discharge_patient(MouseEvent event) throws ClassNotFoundException, SQLException {
    	loadPage("discharge_patient");
    }

    @FXML
    void doctor_visit(MouseEvent event) throws ClassNotFoundException, SQLException {
    	loadPage("doctor_visit");
    }

    @FXML
    void lab_reports(MouseEvent event) throws ClassNotFoundException, SQLException {
    	loadPage("lab_reports");
    }

    @FXML
    void lab_visit(MouseEvent event) throws ClassNotFoundException, SQLException {
    	loadPage("lab_visit");
    }	

    @FXML
    void pay_dues(MouseEvent event) throws ClassNotFoundException, SQLException {
    	loadPage("pay_dues");
    }

    @FXML
    void reg_patient(MouseEvent event) throws ClassNotFoundException, SQLException {
    	loadPage("reg_patient");
    }
    

	private void loadPage (String page) throws ClassNotFoundException, SQLException 
	{
		Parent root = null;
		try {
			FXMLLoader loader = null;
			 loader = new FXMLLoader(getClass().getResource (page+".fxml"));
			 root = loader.load();
			 ReceptionistControllerIF controller = loader.getController();
			 controller.setAdmitPatientManager(admitPatientManager);
			 controller.setDischargePatientManager(dischargePatientManager);
			 controller.setPatientVisitManager(patientVisitManager);
			 controller.setUser(user);
			 controller.loadDetails();

		} catch (IOException ex) 
		{
			}
		BP.setCenter (root);

	}
}






