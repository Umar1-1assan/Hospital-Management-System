package application.Doctor;

import java.io.IOException;
import java.util.ArrayList;

import hospital.DoctorVisit;
import hospital.LabVisit;
import hospital.Patient;
import hospital.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import manager.CheckupPatientManager;

public class ViewQueueController implements DoctorControllerIF
{
	User user;
	CheckupPatientManager checkUpPatientManager;
	private ObservableList<DoctorVisit> doctorVisitList = FXCollections.observableArrayList();

	
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
	    private Button Start_checkup_btn;

	    @FXML
	    private TableColumn<DoctorVisit, String> mIssue_t;

	    @FXML
	    private TableColumn<DoctorVisit, String> pname_t;

	    @FXML
	    private TableColumn<DoctorVisit, String> status_t;

	    @FXML
	    private TableView<DoctorVisit> table_Patient;

	    @FXML
	    private TableColumn<DoctorVisit, Integer> visitNo_t;
	    
	    private BorderPane b_pane;
    
    public void initialize(BorderPane bp)
    {
    	b_pane=bp;
    	mIssue_t.setCellValueFactory(new PropertyValueFactory<>("medicalIssue"));
    	visitNo_t.setCellValueFactory(new PropertyValueFactory<>("visitNo"));
    	pname_t.setCellValueFactory(cellData -> 
	            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getPatient().getName()));      
	    status_t.setCellValueFactory(new PropertyValueFactory<>("status"));
    }
    
    @FXML
    void Start_checkup(MouseEvent event) 
    {
    	DoctorVisit visit = this.table_Patient.getSelectionModel().getSelectedItem();
    	if(visit==null)
    	{
    		this.showAlert("Operation Unsuccessful", "Please select a visit.");
    		return;
    	}
    	
    	this.checkUpPatientManager.startCheckUp(visit.getVisitNo());
    	Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Checkup.fxml"));

        try {
            root = loader.load();
            
        } catch (IOException ex) {
            ex.printStackTrace(); 
        }
        b_pane.setCenter(root);
        
        CheckupController controller = loader.getController();
        controller.setB_pane(b_pane);
        controller.setUser(user);
        controller.setCheckupPatientManager(checkUpPatientManager);
        controller.loadDetails();
    }
    
    
   

    @Override
	public void loadDetails() 
    {
    	ArrayList<DoctorVisit> list = this.checkUpPatientManager.getDoctorQueue().getQueuedDoctorVisits();
		this.doctorVisitList.clear();
		doctorVisitList.addAll(list); 
        this.table_Patient.setItems(doctorVisitList);
	}

}
