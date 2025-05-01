package application.Doctor;

import java.io.IOException;
import java.sql.SQLException;

import application.Receptionist.Search_PatientController;
import hospital.DoctorVisit;
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
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import manager.CheckupPatientManager;

public class CheckupController implements DoctorControllerIF
{
	User user;
	CheckupPatientManager checkUpPatientManager;
	private BorderPane b_pane;
	
	
	public BorderPane getB_pane() {
		return b_pane;
	}

	public void setB_pane(BorderPane b_pane) {
		this.b_pane = b_pane;
	}

	public CheckupPatientManager getCheckupPatientManager() 
	{
		return checkUpPatientManager;
	}

	public void setCheckupPatientManager( CheckupPatientManager checkUpPatientManager) 
	{
		this.checkUpPatientManager = checkUpPatientManager;
	}
	
	public void setUser(User user)
	{
		this.user = user;
	}
	
	public User getUser()
	{
		return this.user;
	}
	   @FXML
	    private TextArea Prescription_f;

	    @FXML
	    private TextField age_f;

	    @FXML
	    private Button check_prev_btn;

	    @FXML
	    private TextField cnic_f;

	    @FXML
	    private TextField dateTime_f;

	    @FXML
	    private TextField doctor_name_f;

	    @FXML
	    private TextField medical_Issue_f;

	    @FXML
	    private TextField patient_name_f;

	    @FXML
	    private Button print_btn;

	    @FXML
	    private Button save_btn;
	    
	    @Override
		public void loadDetails() 
		{
			DoctorVisit visit = this.checkUpPatientManager.getDoctorVisit();
	    	
	    	if(visit!=null)
	    	{
	    		this.patient_name_f.setText(visit.getPatient().getName());
	    		this.age_f.setText(Integer.toString(visit.getPatient().getAge()));
	    		this.cnic_f.setText(visit.getPatient().getCnic());
	    		this.doctor_name_f.setText(visit.getDoctor().getName());
	    		this.dateTime_f.setText(visit.getDateTime());
	    		this.medical_Issue_f.setText(visit.getMedicalIssue());
	    	}
		}
	    
	@FXML
	void Print(MouseEvent e)
	{
		this.showAlert("Operation Successful", "Printed");
	}

    @FXML
    void Save(MouseEvent event) throws ClassNotFoundException, SQLException 
    {
    	this.checkUpPatientManager.endCheckup(this.Prescription_f.getText());
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("View_Queue.fxml"));
    	Parent root = null;
        try {
            root = loader.load();
            
        } catch (IOException ex) {
            ex.printStackTrace(); 
        }
        b_pane.setCenter(root);
        
        ViewQueueController controller = loader.getController();
        controller.initialize(b_pane);
        controller.setUser(user);
        controller.setCheckupPatientManager(checkUpPatientManager);
        controller.loadDetails();
    }

    @FXML
    void check_prev(MouseEvent event) throws IOException, ClassNotFoundException, SQLException 
    {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("Show_prev_visit.fxml"));
    	Parent root = null;
        try {
            root = loader.load();
            
        } catch (IOException ex) {
            ex.printStackTrace(); 
        }
        b_pane.setCenter(root);
        
        ShowPrevVisitController controller = loader.getController();
        controller.setUser(user);
        controller.setB_pane(b_pane);
        controller.setPresc(this.Prescription_f.getText());
        controller.setCheckupPatientManager(checkUpPatientManager);
        controller.loadDetails();
    		
    }
    
    
    @FXML
	public void initialize() 
	{
		
	}
    
    public void setPrescription(String pres)
    {
    	this.Prescription_f.setText(pres);
    }

	

}
