package application.Receptionist;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import hospital.DoctorVisit;
import hospital.LabVisit;
import hospital.User;
import manager.AdmitPatientManager;
import manager.DischargePatientManager;
import manager.PatientVisitManager;

public class LabVisitController implements ReceptionistControllerIF
{
	private User user;
	private PatientVisitManager patientVisitManager;
	private AdmitPatientManager admitPatientManager;
	private DischargePatientManager dischargePatientManager;
	private ObservableList<LabVisit> labVisitList = FXCollections.observableArrayList();

	
	
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
    private TableView<LabVisit> Queue;

    @FXML
    private Button add_labTech;

    @FXML
    private Button confirm_visit;

    @FXML
    private TextField f_labtech_id;

    @FXML
    private TextField f_patient_id;

    @FXML
    private MenuItem item1;

    @FXML
    private MenuItem item2;

    @FXML
    private MenuItem item3;

    @FXML
    private MenuItem item4;

    @FXML
    private TableColumn<LabVisit, String> pname_q;

    @FXML
    private Button search_patient;

    @FXML
    private TableColumn<LabVisit, String> status_q;

    @FXML
    private MenuButton testBtn;

    @FXML
    private TableColumn<LabVisit, Integer> visitNo_q;
    
    @FXML
    public void initialize() 
    {
        visitNo_q.setCellValueFactory(new PropertyValueFactory<>("visitNo"));
        pname_q.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getPatient().getName()));
        status_q.setCellValueFactory(new PropertyValueFactory<>("status"));
    }
    
    public void loadDetails()
	{
		ArrayList<LabVisit> list = this.patientVisitManager.getLabQueue().getQueuedLabVisits();
		this.labVisitList.clear();
		labVisitList.addAll(list); 
        this.Queue.setItems(labVisitList); 
        this.addRetrictions();
	}


    @FXML
    void confirm_visit(MouseEvent event) throws ClassNotFoundException, SQLException 
    {
    	if(this.f_labtech_id.getText().isEmpty() || this.f_patient_id.getText().isEmpty())
    	{
    		this.showAlert("Operation Unsuccessful", "Please fill in all the details.");
    		return;
    	}
    	String labTechUsername = this.f_labtech_id.getText().trim();
    	String patientNic = this.f_patient_id.getText().trim();
    	String test = this.testBtn.getText();
    	
    	LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-yyyy h:mm a");
        String dateTime = now.format(formatter);
    	if(this.patientVisitManager.createLabVisit(patientNic, labTechUsername, test, dateTime))
    	{
    		this.loadDetails();
    	}
    	else
    	{
    		this.showAlert("Operation Unsuccessful", "Invalid details.");
    	}
    }
    
    private void setLabTechId(String LabTechId) {
        f_labtech_id.setText(LabTechId);
    }
    
    @FXML
    void add_labTech(MouseEvent event) throws IOException, ClassNotFoundException, SQLException 
    {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("SearchLabTech.fxml"));
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setScene(new Scene(loader.load()));
        dialogStage.setTitle("Search Lab Technition");

        SearchLabTechController controller = loader.getController();
        controller.setAdmitPatientManager(admitPatientManager);
        controller.setDischargePatientManager(dischargePatientManager);
        controller.setPatientVisitManager(patientVisitManager);
        controller.setUser(user);
        controller.setOnLabTechSelectedCallback(this::setLabTechId);
        controller.loadAllLabTechs();

        dialogStage.showAndWait();
    }

    private void setPatientId(String patientId) {
        f_patient_id.setText(patientId);
    }
    
    @FXML
    void search_patient(MouseEvent event) throws IOException, ClassNotFoundException, SQLException 
    {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("SearchPatient.fxml"));
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setScene(new Scene(loader.load()));
        dialogStage.setTitle("Search Patient");

        Search_PatientController controller = loader.getController();
        controller.setAdmitPatientManager(admitPatientManager);
        controller.setDischargePatientManager(dischargePatientManager);
        controller.setPatientVisitManager(patientVisitManager);
        controller.setUser(user);
        controller.setOnPatientSelectedCallback(this::setPatientId);
        controller.loadAllPatients();

        dialogStage.showAndWait();
    }
    
    @FXML
    void handleOptionSelected(javafx.event.ActionEvent event) 
    {
        MenuItem selectedItem = (MenuItem) event.getSource();
        String selectedOption = selectedItem.getText();
        testBtn.setText(selectedOption);

    }
    
    void addRetrictions()
    {
    	this.f_patient_id.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getText().matches("\\d*")) {
                return change;
            }
            return null;
        }));
    }

}
