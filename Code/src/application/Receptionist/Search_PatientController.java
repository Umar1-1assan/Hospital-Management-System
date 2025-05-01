package application.Receptionist;

import java.sql.SQLException;
import java.util.ArrayList;

import hospital.Patient;
import hospital.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import manager.AdmitPatientManager;
import manager.DischargePatientManager;
import manager.PatientVisitManager;

public class Search_PatientController implements ReceptionistControllerIF
{
	private User user;
	private PatientVisitManager patientVisitManager;
	private AdmitPatientManager admitPatientManager;
	private DischargePatientManager dischargePatientManager;
	private ObservableList<Patient> patientList = FXCollections.observableArrayList();
	
	
    public User getUser() {
		return user;
	}

	public void setUser(User user) {
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

	@FXML
    private TableColumn<Patient, Integer> age;

    @FXML
    private TableColumn<Patient, String> bloodGroup;

    @FXML
    private TableColumn<Patient, String> cnic;

    @FXML
    private AnchorPane confirm_select;

    @FXML
    private Button confirm_selection;

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

    private PatientSelectedCallback callback;

    @FXML
    public void initialize() throws ClassNotFoundException, SQLException 
    {
        confirm_selection.setOnAction(event -> selectPatient());
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        age.setCellValueFactory(new PropertyValueFactory<>("age"));
        cnic.setCellValueFactory(new PropertyValueFactory<>("cnic"));
        gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        bloodGroup.setCellValueFactory(new PropertyValueFactory<>("bloodGroup"));
    }
    public void loadAllPatients() throws ClassNotFoundException, SQLException 
    {
        ArrayList<Patient> patients = this.patientVisitManager.getPatients();
        patientList.clear(); 
        patientList.addAll(patients); 
        table_doc.setItems(patientList); 
    }

    public void setOnPatientSelectedCallback(PatientSelectedCallback callback) 
    {
        this.callback = callback;
    }

    private void selectPatient() 
    {
        Patient patient = this.table_doc.getSelectionModel().getSelectedItem();
        if(patient == null)
        {
        	//*** Add dialog box
        	return;
        }
       
        if (callback != null) 
        {
            callback.onPatientSelected(patient.getCnic());
        }

        Stage stage = (Stage) confirm_selection.getScene().getWindow();
        stage.close();
    }

    public interface PatientSelectedCallback 
    {
        void onPatientSelected(String PatientId);
    }
    @FXML
    void search_Patient(MouseEvent event) throws ClassNotFoundException, SQLException 
    {
        String searchName = patient_name.getText().trim();
        if (searchName.isEmpty()) {
            loadAllPatients(); 
            return;
        }
        
        ArrayList<Patient> patients = this.patientVisitManager.getPatient(searchName);
        patientList.clear(); 
        patientList.addAll(patients); 
        table_doc.setItems(patientList);
    }
    public void loadDetails() throws ClassNotFoundException, SQLException
    {
    	
    }


}
