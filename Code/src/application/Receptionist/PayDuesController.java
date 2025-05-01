package application.Receptionist;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import dbhandler.BedAllocationDBHandler;
import hospital.AdmissionRequest;
import hospital.BedAllocation;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import manager.AdmitPatientManager;
import manager.DischargePatientManager;
import manager.PatientVisitManager;

public class PayDuesController implements ReceptionistControllerIF
{
	private User user;
	private PatientVisitManager patientVisitManager;
	private AdmitPatientManager admitPatientManager;
	private DischargePatientManager dischargePatientManager;
	private ObservableList<BedAllocation> allocList = FXCollections.observableArrayList();

	
	
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
	    private TableView<BedAllocation> Allocations;

	    @FXML
	    private Button Open_dues;

	    @FXML
	    private TableColumn<BedAllocation, Integer> allocNo_t;

	    @FXML
	    private TableColumn<BedAllocation, Integer> bedNo_t;

	    @FXML
	    private TextField f_patient_id;

	    @FXML
	    private TableColumn<BedAllocation, String> pname_t;

	    @FXML
	    private TableColumn<BedAllocation, Integer> roomNo_t;

	    @FXML
	    private Button search_patient;
	    
	    @FXML
	    public void initialize() throws ClassNotFoundException, SQLException 
	    {
	    	allocNo_t.setCellValueFactory(new PropertyValueFactory<>("bedAllocationNumber"));
	    	bedNo_t.setCellValueFactory(new PropertyValueFactory<>("bedNo"));
	    	roomNo_t.setCellValueFactory(new PropertyValueFactory<>("roomNo"));
	    	pname_t.setCellValueFactory(cellData -> 
	            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getPatient().getName()));
	    }
	    

    @FXML
    void Open_dues(MouseEvent event) throws IOException, ClassNotFoundException, SQLException 
    {
    	
        BedAllocation b = this.Allocations.getSelectionModel().getSelectedItem();
        
        if(b==null)
        {
        	this.showAlert("Operation Unsucessful", "Please select a bed allocation.");
        	return;
        }
        
        this.dischargePatientManager.openBedAllocation(b.getBedAllocationNumber());

        
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("ShowBedAllocation.fxml"));
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setScene(new Scene(loader.load()));
        dialogStage.setTitle("Bed Allocation");
       
        ShowBedAllocationController controller = loader.getController();
        controller.setAdmitPatientManager(admitPatientManager);
        controller.setDischargePatientManager(dischargePatientManager);
        controller.setPatientVisitManager(patientVisitManager);
        controller.initializez(Integer.toString(b.getBedAllocationNumber()));

        dialogStage.showAndWait();
    }


    @FXML
    void check_allocations(MouseEvent event) 
    {

    }

    @FXML
    void search_patient(MouseEvent event) throws IOException, ClassNotFoundException, SQLException 
    {
    	String searchName = this.f_patient_id.getText().trim();
        if (searchName.isEmpty()) 
        {
            loadDetails(); 
            return;
        }
        
        ArrayList<BedAllocation> list = this.patientVisitManager.getBedAllocations(searchName);
		this.allocList.clear();
		allocList.addAll(list); 
        this.Allocations.setItems(allocList); 
    }
    public void loadDetails() throws ClassNotFoundException, SQLException
    {
    	ArrayList<BedAllocation> list = this.patientVisitManager.getBedAllocations();
		this.allocList.clear();
		allocList.addAll(list); 
        this.Allocations.setItems(allocList); 
    }

}
