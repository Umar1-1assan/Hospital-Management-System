package application.LabTechnician;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import application.Doctor.CheckupController;
import hospital.DoctorVisit;
import hospital.LabVisit;
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
import manager.PerformTestManager;

public class View_QueueController implements LabTechnicianControllerIF 
{
	User user;
	PerformTestManager performTestManager;
	private ObservableList<LabVisit> labVisitList = FXCollections.observableArrayList();
    private BorderPane b_pane;

    public User getUser() 
    {
		return user;
	}

	public void setUser(User user) 
	{
		this.user = user;
	}

	public PerformTestManager getPerformTestManager() {
		return performTestManager;
	}

	public void setPerformTestManager(PerformTestManager performTestManager) {
		this.performTestManager = performTestManager;
	}

	  @FXML
	private Button Start_Test;

	@FXML
	private TextField patient_name;

	@FXML
	private TableColumn<LabVisit,String> pname_t;

	@FXML
	private Button search_patient;

	@FXML
	private TableColumn<LabVisit,String> status_t;

	@FXML
	private TableView<LabVisit> table_Patient;

	@FXML
	private TableColumn<LabVisit,String> test_t;

	@FXML
	private TableColumn<LabVisit,Integer> visitNo_t;
	    
	public void initialize(BorderPane bp)
	{
		b_pane=bp;
		test_t.setCellValueFactory(new PropertyValueFactory<>("testName"));
		visitNo_t.setCellValueFactory(new PropertyValueFactory<>("visitNo"));
		pname_t.setCellValueFactory(cellData -> 
		        new javafx.beans.property.SimpleStringProperty(cellData.getValue().getPatient().getName()));      
		status_t.setCellValueFactory(new PropertyValueFactory<>("status"));
	}

    @FXML
    void Start_Test(MouseEvent event) throws IOException, ClassNotFoundException, SQLException
    {
    	LabVisit visit = this.table_Patient.getSelectionModel().getSelectedItem();
    	if(visit==null)
    	{
    		this.showAlert("Operation Unsuccessful", "Please select a visit.");
    		return;
    	}
    	
    	this.performTestManager.StartProcedure(visit.getVisitNo());
    	Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Procedure.fxml"));
        root = loader.load();
        b_pane.setCenter(root);
        
        ProcedureController controller = loader.getController();
        controller.setB_pane(b_pane);
        controller.setUser(user);
        controller.setPerformTestManager(performTestManager);;
        controller.loadDetails();
    }
    
    @Override
	public void loadDetails() 
    {
    	ArrayList<LabVisit> list = this.performTestManager.getLabQueue().getQueuedLabVisits();
		this.labVisitList.clear();
		labVisitList.addAll(list); 
        this.table_Patient.setItems(labVisitList);
	}


}
