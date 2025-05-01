package application.Doctor;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import hospital.BedAllocation;
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

public class DischargeRequestController implements DoctorControllerIF
{
	User user;
	CheckupPatientManager checkUpPatientManager;
	private ObservableList<BedAllocation> allocList = FXCollections.observableArrayList();

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
    private AnchorPane confirm_select;
    
	 @FXML
	private TableView<BedAllocation> Allocations;

	@FXML
	private Button Gen_Discharge_Req_btn;

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
    private Button search_patient_btn;
	    

    
    @FXML
    void Gen_Discharge_Req(MouseEvent event) throws ClassNotFoundException, SQLException 
    {
    	BedAllocation b = this.Allocations.getSelectionModel().getSelectedItem();
    	if(b==null)
    	{
    		this.showAlert("Operation Unsuccessful", "Please select a record.");
    		return;
    	}
    	LocalDateTime now = LocalDateTime.now();
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    	String dt = now.format(formatter);
    	if(this.checkUpPatientManager.generateDischargeRequest(b.getBedAllocationNumber(), b.getPatientCnic(), dt))
    	{
    		this.showAlert("Operation Successful", "Discharge request generated.");
    		return;
    	}
    	else
    	{
    		this.showAlert("Operation Unsuccessful", "There is already a discharge request for this patient.");
    		return;
    	}
    }

    @FXML
    void search_patient(MouseEvent event) throws ClassNotFoundException, SQLException 
    {
    	String searchName = this.f_patient_id.getText().trim();
        if (searchName.isEmpty()) 
        {
            loadDetails(); 
            return;
        }
        
        ArrayList<BedAllocation> list = this.checkUpPatientManager.getBedAllocations(searchName);
		this.allocList.clear();
		allocList.addAll(list); 
        this.Allocations.setItems(allocList); 
    }

    @Override
    public void loadDetails() throws ClassNotFoundException, SQLException
    {
    	ArrayList<BedAllocation> list = this.checkUpPatientManager.getBedAllocations();
		this.allocList.clear();
		allocList.addAll(list); 
        this.Allocations.setItems(allocList); 
    }
    
    @FXML
    public void initialize() throws ClassNotFoundException, SQLException 
    {
    	allocNo_t.setCellValueFactory(new PropertyValueFactory<>("bedAllocationNumber"));
    	bedNo_t.setCellValueFactory(new PropertyValueFactory<>("bedNo"));
    	roomNo_t.setCellValueFactory(new PropertyValueFactory<>("roomNo"));
    	pname_t.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getPatient().getName()));
    }

}
