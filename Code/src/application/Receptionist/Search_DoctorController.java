package application.Receptionist;

import java.sql.SQLException;
import java.util.ArrayList;

import hospital.Doctor;
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

public class Search_DoctorController implements ReceptionistControllerIF
{
	private User user;
	private PatientVisitManager patientVisitManager;
	private AdmitPatientManager admitPatientManager;
	private DischargePatientManager dischargePatientManager;
	private ObservableList<Doctor> doctorList = FXCollections.observableArrayList();
	
	
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
	
	public void loadAllDoctors() throws ClassNotFoundException, SQLException
	{
        ArrayList<Doctor> doctors = this.patientVisitManager.getDoctors();
        doctorList.clear(); 
        doctorList.addAll(doctors); 
        table_doc.setItems(doctorList); 
	}

	 @FXML
	    private AnchorPane confirm_select;

	    @FXML
	    private Button confirm_selection;

	    @FXML
	    private TextField f_doc_id;

	    @FXML
	    private TableColumn<Doctor, String> name;

	    @FXML
	    private Button search_doctor;

	    @FXML
	    private TableColumn<Doctor, String> speciality;

	    @FXML
	    private TableView<Doctor> table_doc;

	    @FXML
	    private TableColumn<Doctor, String> username;
    private DoctorSelectedCallback callback;

    @FXML
    public void initialize() 
    {
        confirm_selection.setOnAction(event -> selectDoctor());
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        speciality.setCellValueFactory(new PropertyValueFactory<>("speciality"));
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
    }

    public void setOnDoctorSelectedCallback(DoctorSelectedCallback callback) {
        this.callback = callback;
    }

    private void selectDoctor() 
    {
    	Doctor doctor = this.table_doc.getSelectionModel().getSelectedItem();

        if (callback != null) {
            callback.onDoctorSelected(doctor.getUsername());
        }

        Stage stage = (Stage) confirm_selection.getScene().getWindow();
        stage.close();
    }

    public interface DoctorSelectedCallback {
        void onDoctorSelected(String doctorId);
    }
    @FXML
    void search_doctor(MouseEvent event) throws ClassNotFoundException, SQLException 
    {
    	 String searchName = this.f_doc_id.getText().trim();
         if (searchName.isEmpty()) {
             loadAllDoctors();
             return;
         }

         ArrayList<Doctor> doctors = this.patientVisitManager.getDoctor(searchName);
         doctorList.clear(); // Clear existing data
         doctorList.addAll(doctors); // Add filtered data
         table_doc.setItems(doctorList); // Bind the list to the TableView

    }
    public void loadDetails() throws ClassNotFoundException, SQLException
    {
    	
    }



}
