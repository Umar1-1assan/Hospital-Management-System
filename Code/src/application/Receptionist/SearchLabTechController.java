package application.Receptionist;

import java.sql.SQLException;
import java.util.ArrayList;

import application.Receptionist.SearchLabTechController.LabTechSelectedCallback;
import hospital.LabTechnician;
import hospital.Patient;
import hospital.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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

public class SearchLabTechController implements ReceptionistControllerIF
{
	private User user;
	private PatientVisitManager patientVisitManager;
	private AdmitPatientManager admitPatientManager;
	private DischargePatientManager dischargePatientManager;
	private ObservableList<LabTechnician> labTechList = FXCollections.observableArrayList();
	
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
	
	public void loadAllLabTechs() throws ClassNotFoundException, SQLException
	{
		ArrayList<LabTechnician> labTechs = this.patientVisitManager.getLabTechnicians();
        labTechList.clear(); 
        labTechList.addAll(labTechs); 
        table_doc.setItems(labTechList); 
	}

    @FXML
    private TextField LabTech_name;

    @FXML
    private AnchorPane confirm_select;

    @FXML
    private Button confirm_selection;

    @FXML
    private TableColumn<LabTechnician, Integer> experience;

    @FXML
    private TableColumn<LabTechnician, String> name;

    @FXML
    private Button search_LabTech;

    @FXML
    private TableView<LabTechnician> table_doc;

    @FXML
    private TableColumn<LabTechnician, String> uesrname;

    private LabTechSelectedCallback callback;
    
    
    public void initialize() 
    {
        confirm_selection.setOnAction(event -> selectLabTech());
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        uesrname.setCellValueFactory(new PropertyValueFactory<>("username"));
        experience.setCellValueFactory(new PropertyValueFactory<>("workExperience"));
        
    }

    public void setOnLabTechSelectedCallback(LabTechSelectedCallback callback) {
        this.callback = callback;
    }

    private void selectLabTech() {
        LabTechnician labTech = this.table_doc.getSelectionModel().getSelectedItem();
        
        if(labTech == null)
        {
        	//Add dialog box
        	return;
        }

        if (callback != null) {
            callback.onLabTechSelected(labTech.getUsername());
        }

        Stage stage = (Stage) confirm_selection.getScene().getWindow();
        stage.close();
    }

    public interface LabTechSelectedCallback {
        void onLabTechSelected(String PatientId);
    }
    
    
    @FXML
    void search_LabTech(MouseEvent event) throws ClassNotFoundException, SQLException 
    {
    	 String searchName = this.LabTech_name.getText().trim();
         if (searchName.isEmpty()) {
             loadAllLabTechs(); 
             return;
         }
         
         ArrayList<LabTechnician> labTechs = this.patientVisitManager.getLabTechnician(searchName);
         labTechList.clear(); 
         labTechList.addAll(labTechs); 
         table_doc.setItems(labTechList);
    }
    public void loadDetails() throws ClassNotFoundException, SQLException
    {
    	
    }

}
