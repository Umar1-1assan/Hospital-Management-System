package application.Receptionist;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import hospital.DoctorVisit;
import hospital.Patient;
import hospital.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import manager.AdmitPatientManager;
import manager.DischargePatientManager;
import manager.PatientVisitManager;

public class doctorVisitController implements ReceptionistControllerIF
{ 
	private User user;
	private PatientVisitManager patientVisitManager;
	private AdmitPatientManager admitPatientManager;
	private DischargePatientManager dischargePatientManager;
	private ObservableList<DoctorVisit> doctorVisitList = FXCollections.observableArrayList();
	
	
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
	
	public void loadDetails()
	{
		ArrayList<DoctorVisit> list = this.patientVisitManager.getDoctorQueue().getQueuedDoctorVisits();
		this.doctorVisitList.clear();
		doctorVisitList.addAll(list); 
        this.Queue.setItems(doctorVisitList); 
        this.addRetrictions();
	}

	  @FXML
	    private TableView<DoctorVisit> Queue;

	    @FXML
	    private Button confirm_visitBTN;
	    @FXML
	    private TextField f_doc_id;

	    @FXML
	    private TextField f_patient_id;

	    @FXML
	    private TextField medical_issues;
	    @FXML
	    private TableColumn<DoctorVisit, String> Status;

	    @FXML
	    private TableColumn<DoctorVisit, String> pname_q;

	    @FXML
	    private Button search_doctor;

	    @FXML
	    private Button search_patient;

	    @FXML
	    private TableColumn<DoctorVisit,Integer> visitNo_q;

	    @FXML
	    public void initialize() 
	    {
	        visitNo_q.setCellValueFactory(new PropertyValueFactory<>("visitNo"));
	        pname_q.setCellValueFactory(cellData -> 
	            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getPatient().getName()));
	        Status.setCellValueFactory(new PropertyValueFactory<>("status"));
	    }
    @FXML
    void confirm_visit(MouseEvent event) throws ClassNotFoundException, SQLException 
    {
    	if(this.f_doc_id.getText().isEmpty() || this.f_patient_id.getText().isEmpty() || this.medical_issues.getText().isEmpty())
    	{
    		this.showAlert("Operation Unsuccessful", "Please fill in all the details.");
    		return;
    	}
    	String docUsername = this.f_doc_id.getText().trim();
    	String patientNic = this.f_patient_id.getText().trim();
    	String issue = this.medical_issues.getText().trim();
    	
    	LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-yyyy h:mm a");
        String dateTime = now.format(formatter);
        if(!issue.matches("^[a-zA-Z ]+$"))
        {
        	this.showAlert("Failed", "Please enter a valid issue");
        	return;
        }
    	if(this.patientVisitManager.createDoctorVisit(patientNic, docUsername, issue, dateTime))
    	{
    		this.loadDetails();
    	}
    	else
    	{
    		this.showAlert("Operation Unsuccessful", "Invalid details");
    	}
    }

    @FXML
    void search_doctor(MouseEvent event) throws IOException, ClassNotFoundException, SQLException 
    {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("SearchDoctor.fxml"));
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setScene(new Scene(loader.load()));
        dialogStage.setTitle("Search Doctor");

        Search_DoctorController controller = loader.getController();
        controller.setAdmitPatientManager(admitPatientManager);
        controller.setDischargePatientManager(dischargePatientManager);
        controller.setPatientVisitManager(patientVisitManager);
        controller.setUser(user);
        controller.loadAllDoctors();
        controller.setOnDoctorSelectedCallback(this::setDoctorId);

        dialogStage.showAndWait();
    }

    private void setDoctorId(String doctorId) {
        f_doc_id.setText(doctorId);
    }
    private void setPatientId(String patientId) {
        f_patient_id.setText(patientId);
    }
    @FXML
    void search_patient(MouseEvent event) throws IOException, ClassNotFoundException, SQLException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("SearchPatient.fxml"));
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setScene(new Scene(loader.load()));
        dialogStage.setTitle("Search Patient");

        // Get the controller of the dialog and set up a callback for when a doctor is selected
        Search_PatientController controller = loader.getController();
        controller.setAdmitPatientManager(admitPatientManager);
        controller.setDischargePatientManager(dischargePatientManager);
        controller.setPatientVisitManager(patientVisitManager);
        controller.setUser(user);
        controller.loadAllPatients();
        controller.setOnPatientSelectedCallback(this::setPatientId);

        dialogStage.showAndWait();
    }
    
    public void addRetrictions()
    {
    	this.f_patient_id.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getText().matches("\\d*")) {
                return change;
            }
            return null;
        }));
    	
    	this.medical_issues.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getText().matches("[a-zA-Z ]*")) {
                return change;
            }
            return null;
        }));
    }

}
