package application.Doctor;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import hospital.Patient;
import hospital.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import manager.CheckupPatientManager;

public class AdmitRequestController implements DoctorControllerIF
{
	User user;
	CheckupPatientManager checkUpPatientManager;
	private ObservableList<Patient> patientList = FXCollections.observableArrayList();

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
	    private Button Gen_Admit_Req_btn;

	  @FXML
	    private TableColumn<Patient, Integer> age;

	    @FXML
	    private TableColumn<Patient, String> bloodGroup;

	    @FXML
	    private TableColumn<Patient, String> cnic;

	    @FXML
	    private TableColumn<Patient, String> gender;

	    @FXML
	    private TableColumn<Patient, String> name;

	    @FXML
	    private TextField patient_name;

	    @FXML
	    private Button search_patient;

	    @FXML
	    private TableView<Patient> table_doc;
    
    @FXML
    void Gen_Admit_Req(MouseEvent event) throws ClassNotFoundException, SQLException 
    {
    	Patient p = this.table_doc.getSelectionModel().getSelectedItem();
    	if(p==null)
    	{
    		this.showAlert("Operation Unsuccessful", "Please select a patient.");
    		return;
    	}
    	  LocalDateTime now = LocalDateTime.now();
          DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
          String dt = now.format(formatter);
    	if(this.checkUpPatientManager.generateAdmissionRequest(this.user.getUsername(), p.getCnic(), dt))
    	{
    		this.showAlert("Operation Successful", "Admission Request generated.");
    		this.loadDetails();
    		return;
    	}
    	else
    	{
    		this.showAlert("Operation Unsuccessful", "There's already an admission request for the patient.");
    		return;
    	}

    }

    @FXML
    void search_Patient(MouseEvent event) throws ClassNotFoundException, SQLException 
    {
    	String searchName = patient_name.getText().trim();
        if (searchName.isEmpty()) {
            loadDetails(); 
            return;
        }
        
        ArrayList<Patient> patients = this.checkUpPatientManager.getPatient(searchName);
        patientList.clear(); 
        patientList.addAll(patients); 
        table_doc.setItems(patientList);
    }

    @Override
	public void loadDetails() throws ClassNotFoundException, SQLException 
    {
    	ArrayList<Patient> patients = this.checkUpPatientManager.getPatients();
        patientList.clear(); 
        patientList.addAll(patients); 
        table_doc.setItems(patientList); 
	}
    
    @FXML
    public void initialize()
    {
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        age.setCellValueFactory(new PropertyValueFactory<>("age"));
        cnic.setCellValueFactory(new PropertyValueFactory<>("cnic"));
        gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        bloodGroup.setCellValueFactory(new PropertyValueFactory<>("bloodGroup"));
    }

}
