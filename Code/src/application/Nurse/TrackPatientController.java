package application.Nurse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import application.Doctor.CheckupController;
import application.LabTechnician.ShowReportController;
import hospital.BedAllocation;
import hospital.DoctorVisit;
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
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import manager.NurseManager;

public class TrackPatientController implements ControllerIF
{
	private User user;
	private NurseManager nurseManager;
	BorderPane b_pane;
	private ObservableList<BedAllocation> allocList = FXCollections.observableArrayList();

	

    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public NurseManager getNurseManager() {
		return nurseManager;
	}

	public void setNurseManager(NurseManager nurseManager) {
		this.nurseManager = nurseManager;
	}

	@FXML
	private TableView<BedAllocation> Allocations;

	@FXML
	private Button Open_U;

	@FXML
	private TableColumn<BedAllocation, Integer> allocNo_t;

	@FXML
	private TableColumn<BedAllocation, String> bedNo_t;

	@FXML
	private TextField f_patient_id;

	@FXML
	private TableColumn<BedAllocation, String> pname_t;

	@FXML
	private TableColumn<BedAllocation, Integer> roomNo_t;
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
    void Open_U(MouseEvent event) throws IOException, ClassNotFoundException, SQLException 
    {
    	BedAllocation alloc = this.Allocations.getSelectionModel().getSelectedItem();
    	if(alloc==null)
    	{
    		this.showAlert("Operation Unsuccessful", "Please select a record.");
    		return;
    	}
    	
    	this.nurseManager.openBedAllocation(alloc.getBedAllocationNumber());
    	
    	Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdatePatient.fxml"));
        root = loader.load();
        b_pane.setCenter(root);
        
        ControllerIF controller = loader.getController();
        controller.setB_pane(b_pane);
        controller.setUser(user);
        controller.setNurseManager(nurseManager);
        controller.loadDetails();
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
        
        ArrayList<BedAllocation> list = this.nurseManager.getBedAllocations(this.user.getUsername(), searchName);
		this.allocList.clear();
		allocList.addAll(list); 
        this.Allocations.setItems(allocList); 
    }

    
	@Override
	public void loadDetails() throws ClassNotFoundException, SQLException 
	{
    	ArrayList<BedAllocation> list = this.nurseManager.getBedAllocations(this.user.getUsername());
		this.allocList.clear();
		allocList.addAll(list); 
        this.Allocations.setItems(allocList);
		
	}

	@Override
	public void setB_pane(BorderPane p) 
	{
		this.b_pane=p;
	}

}
