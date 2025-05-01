package application.Receptionist;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import hospital.AdmissionRequest;
import hospital.BedAllocation;
import hospital.LabVisit;
import hospital.Patient;
import hospital.Doctor;
import hospital.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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

public class AdmitPatientController implements ReceptionistControllerIF
{
	private User user;
	private PatientVisitManager patientVisitManager;
	private AdmitPatientManager admitPatientManager;
	private DischargePatientManager dischargePatientManager;
	private ObservableList<AdmissionRequest> admReqList = FXCollections.observableArrayList();

	
	
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
    private AnchorPane confirm_select;

	   @FXML
	    private Button Add_Bed;

	    @FXML
	    private Button admit_patient;

	    @FXML
	    private TableColumn<AdmissionRequest, String> dateTime_t;

	    @FXML
	    private TableColumn<AdmissionRequest, String> docName_t;

	    @FXML
	    private TextField f_BedNo;

	    @FXML
	    private TextField f_RoomNo;

	    @FXML
	    private TextField f_patient_id;

	    @FXML
	    private TableColumn<AdmissionRequest, String> nic_t;

	    @FXML
	    private TableColumn<AdmissionRequest, String> patName_t;

	    @FXML
	    private TableColumn<AdmissionRequest, Integer> reqNo_t;

	    @FXML
	    private Button search_patient;
	    
	    @FXML
	    private TableView<AdmissionRequest> reqTable;
	    
	    public void initialize() 
	    {
	        this.reqNo_t.setCellValueFactory(new PropertyValueFactory<>("requestNumber"));

	        patName_t.setCellValueFactory(cellData -> 
	            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getPatient().getName()));
	        patName_t.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getPatient().getName()));
	        
	        nic_t.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getPatient().getCnic()));
	        
	        this.docName_t.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDoctor().getName()));
        
	        dateTime_t.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
	    }


    @FXML
    void Add_Bed(MouseEvent event) throws IOException, ClassNotFoundException, SQLException 
    {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("SearchBed.fxml"));
    	Stage dialogStage = new Stage();
    	dialogStage.setScene(new Scene(loader.load()));
    	dialogStage.setTitle("Search Bed");

    	SearchBedController controller = loader.getController();
    	controller.setOnBedSelectedCallback(this::setBedId);
    	controller.setAdmitPatientManager(admitPatientManager);
    	controller.setPatientVisitManager(patientVisitManager);
    	controller.setDischargePatientManager(dischargePatientManager);
    	controller.loadDetails();

    	dialogStage.showAndWait();
    }
    
    private void setBedId(String BedId, String RoomId) {
        f_BedNo.setText(BedId);
        f_RoomNo.setText(RoomId);
        
    }

    @FXML
    void Admit_Patient(MouseEvent event) throws ClassNotFoundException, SQLException, IOException 
    {
    	AdmissionRequest req = this.reqTable.getSelectionModel().getSelectedItem();
    	
    	if(req==null)
    	{
    		this.showAlert("Operation Unsuccessful", "Please select an admission request.");
    		return;
    	}
    	int roomNo=0;
    	int bedNo=0;
    	
    	if(this.f_RoomNo.getText().isEmpty() || this.f_BedNo.getText().isEmpty())
    	{
    		this.showAlert("Operation Unsuccessful", "Please fill in room,bed number");
    		return;
    	}
    	else
    	{
    		try 
    		{
                roomNo = Integer.parseInt(this.f_RoomNo.getText().trim());
                bedNo = Integer.parseInt(this.f_BedNo.getText().trim());
                /***/
            } 
    		catch (NumberFormatException e) 
    		{
    			this.showAlert("Operation Unsuccessful", "Invalid room,bed number");
                return;
            }
    	}
    	
    	LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-yyyy h:mm a");
        String dt = now.format(formatter);
    	
    	BedAllocation b = this.admitPatientManager.admitPatient(req.getRequestNumber(), roomNo, bedNo, dt);
    	if(b==null)
    	{
    		this.showAlert("Operation Unsuccessful", "Error Admitting patient.");
    	}
    	else
    	{
    		this.displayAdmReceipt(b);
    		this.loadDetails();
    	}
    }
    
    public void displayAdmReceipt(BedAllocation b) throws IOException
    {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("AdmissionReceipt.fxml"));
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setScene(new Scene(loader.load()));
        dialogStage.setTitle("Admission Receipt");

        AdmissionRecptController controller = loader.getController();
        controller.setBedAlloc(b);
        controller.loadDetails();
        dialogStage.showAndWait();
    }

    private void setPatientId(String patientId) 
    {
        f_patient_id.setText(patientId);
    }
    @FXML
    void search_patient(MouseEvent event) throws IOException, ClassNotFoundException, SQLException 
    {
    	  String searchName = this.f_patient_id.getText().trim();
          if (searchName.isEmpty()) 
          {
              this.loadDetails();
              return;
          }
          
          ArrayList<AdmissionRequest> reqs = this.admitPatientManager.getAdmissionRequest(searchName);
          this.admReqList.clear(); 
          admReqList.addAll(reqs); 
          this.reqTable.setItems(admReqList);

    }
    public void loadDetails() throws ClassNotFoundException, SQLException
    {
    	ArrayList<AdmissionRequest> list = this.admitPatientManager.getAdmissionRequests();
		this.admReqList.clear();
		admReqList.addAll(list); 
        this.reqTable.setItems(admReqList); 
    }

}
