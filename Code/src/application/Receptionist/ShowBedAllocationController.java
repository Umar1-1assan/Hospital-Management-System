package application.Receptionist;

import java.sql.SQLException;
import java.util.ArrayList;

import hospital.BedAllocation;
import hospital.Due;
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

public class ShowBedAllocationController implements ReceptionistControllerIF
{
	private User user;
	private PatientVisitManager patientVisitManager;
	private AdmitPatientManager admitPatientManager;
	private DischargePatientManager dischargePatientManager;
	private ObservableList<Due> dueList = FXCollections.observableArrayList();
	
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
	    private TableColumn<Due, String> dateTime_t;

	    @FXML
	    private TableColumn<Due, String> desc_t;

	    @FXML
	    private TableView<Due> dues;

	    @FXML
	    private TextField f_Allocation_id;

	    @FXML
	    private Button mark_paid;

	    @FXML
	    private AnchorPane paid;

	    @FXML
	    private TableColumn<Due, Float> price_t;

	    @FXML
	    private Button save;

	    @FXML
	    private TableColumn<Due, String> status_t;

    public void initializez(String visitID) throws ClassNotFoundException, SQLException
    {
    	f_Allocation_id.setText(visitID);  
    	price_t.setCellValueFactory(new PropertyValueFactory<>("price"));
    	status_t.setCellValueFactory(new PropertyValueFactory<>("status"));
    	desc_t.setCellValueFactory(new PropertyValueFactory<>("description"));
    	dateTime_t.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
    	
    	loadDetails();
    }
    
    
    @FXML
    void mark_paid(MouseEvent event) throws ClassNotFoundException, SQLException 
    {
    	Due d = this.dues.getSelectionModel().getSelectedItem();
    	
    	if(d==null)
    	{
    		this.showAlert("Operation Unssuccesful", "Please select a due.");
    		return;
    	}
    	
    	this.dischargePatientManager.payDue(d.getDueNumber());
    	this.loadDetails();
    }

    @FXML
    void save_changes(MouseEvent event) throws ClassNotFoundException, SQLException 
    {
    		this.dischargePatientManager.saveBedAllocation();
    		Stage stage = (Stage) save.getScene().getWindow();
    		stage.close();
    		
    }
    public void loadDetails() throws ClassNotFoundException, SQLException
    {
    	this.dueList.clear();
    	this.dueList.addAll(this.dischargePatientManager.getBedAllocation().getDues());
    	this.dues.setItems(dueList);
    }

}
