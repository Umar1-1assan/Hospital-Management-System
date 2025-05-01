package application.Receptionist;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import hospital.BedAllocation;
import hospital.DischargeRequest;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import manager.AdmitPatientManager;
import manager.DischargePatientManager;
import manager.PatientVisitManager;

public class DischargePatientController implements ReceptionistControllerIF
{
	private User user;
	private PatientVisitManager patientVisitManager;
	private AdmitPatientManager admitPatientManager;
	private DischargePatientManager dischargePatientManager;
	private ObservableList<DischargeRequest> reqList  = FXCollections.observableArrayList();

	
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
    private Button Discharge;

    @FXML
    private TableColumn<DischargeRequest, String> cnic_t;

    @FXML
    private TableColumn<DischargeRequest, String> dateTime_t;

    @FXML
    private TableView<DischargeRequest> discharge_reqs_table;

    @FXML
    private TextField f_patient_id;

    @FXML
    private TableColumn<DischargeRequest, String> patName_t;

    @FXML
    private TableColumn<DischargeRequest, Integer> reqNo_t;
    
    @FXML
    private TableColumn<DischargeRequest, Integer> bedAllocNo_t;

    @FXML
    private Button search_patient;
    
    @FXML
    public void initialize() throws ClassNotFoundException, SQLException 
    {
    	dateTime_t.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
    	reqNo_t.setCellValueFactory(new PropertyValueFactory<>("requestNumber"));    
    	patName_t.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getBedAllocation().getPatient().getName()));
    	cnic_t.setCellValueFactory(cellData -> 
        new javafx.beans.property.SimpleStringProperty(cellData.getValue().getBedAllocation().getPatient().getCnic()));  
    	bedAllocNo_t.setCellValueFactory(cellData -> 
        new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getBedAllocation().getBedAllocationNumber()).asObject()); 
    }
    
    
    @FXML
    void Discharge_fnc(MouseEvent event) throws ClassNotFoundException, SQLException, IOException 
    {
    	DischargeRequest req = this.discharge_reqs_table.getSelectionModel().getSelectedItem();
    	if(req==null)
    	{
    		this.showAlert("Operation Unsuccessfule", "Please select a request.");
    		return;
    	}
    	
    	BedAllocation b = this.dischargePatientManager.dischargePatient(req.getRequestNumber(), req.getBedAllocation().getBedAllocationNumber());
    	
    	if(b==null)
    	{
    		this.showAlert("Operation Unuccessful", "All dues must be paid first.");
    		return;
    	}
    	else
    	{
    		this.displayDisReceipt(b);
    		this.loadDetails();
    	}

    }
    
    public void displayDisReceipt(BedAllocation b) throws IOException
    {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("DischargeReceipt.fxml"));
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setScene(new Scene(loader.load()));
        dialogStage.setTitle("Admission Receipt");

        DischargeRecptController controller = loader.getController();
        controller.setBedAlloc(b);
        controller.loadDetails();
        dialogStage.showAndWait();
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
        
        ArrayList<DischargeRequest> list = this.dischargePatientManager.getDischargeRequests(searchName); 
        this.reqList.clear();
		reqList.addAll(list); 
        this.discharge_reqs_table.setItems(reqList);       
    }
    public void loadDetails() throws ClassNotFoundException, SQLException
    {
    	ArrayList<DischargeRequest> list = this.dischargePatientManager.getDischargeRequests();
		this.reqList.clear();
		reqList.addAll(list); 
        this.discharge_reqs_table.setItems(reqList);
    }

}
