package application.LabTechnician;
import application.Receptionist.*;
import hospital.LabVisit;
import hospital.Patient;
import hospital.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

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
import manager.PerformTestManager;

public class LabReportsController implements LabTechnicianControllerIF 
{
	User user;
	PerformTestManager performTestManager;
	BorderPane b_pane;
	private ObservableList<LabVisit> labVisitList = FXCollections.observableArrayList();

    public BorderPane getB_pane() {
		return b_pane;
	}

	public void setB_pane(BorderPane b_pane) {
		this.b_pane = b_pane;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public PerformTestManager getPerformTestManager() {
		return performTestManager;
	}

	public void setPerformTestManager(PerformTestManager performTestManager) {
		this.performTestManager = performTestManager;
	}
    @FXML
    private TableView<LabVisit> PatientVisits;

    @FXML
    private TextField f_patient_id;

    @FXML
    private Button getReport_btn;

    @FXML
    private TableColumn<LabVisit, String> pname_t;

    @FXML
    private Button search_patient;

    @FXML
    private TableColumn<LabVisit, String> test_t;

    @FXML
    private TableColumn<LabVisit, String> visitDate_t;

    @FXML
    private TableColumn<LabVisit, Integer> visitNo_t;
    
    @FXML
    public void initialize()
    {
    	visitNo_t.setCellValueFactory(new PropertyValueFactory<>("visitNo"));
    	pname_t.setCellValueFactory(cellData -> 
             new javafx.beans.property.SimpleStringProperty(cellData.getValue().getPatient().getName()));
    	visitDate_t.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
    	test_t.setCellValueFactory(new PropertyValueFactory<>("testName"));
    }

    @FXML
    void getReport(MouseEvent event) throws IOException, ClassNotFoundException, SQLException 
    {
       	LabVisit visit = this.PatientVisits.getSelectionModel().getSelectedItem();
    	if(visit==null)
    	{
    		this.showAlert("Operation Unsuccessful", "Please select a visit.");
    		return;
    	}
    	
    	Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ShowReports.fxml"));
        root = loader.load();
        b_pane.setCenter(root);
        
        ShowReportController controller = loader.getController();
        controller.setB_pane(b_pane);
        controller.setUser(user);
        controller.setVisitNo(visit.getVisitNo());
        controller.setPerformTestManager(performTestManager);
        controller.loadDetails();
    }

    @FXML
    void search_patient(MouseEvent event) throws IOException, ClassNotFoundException, SQLException 
    {
        String searchName = this.f_patient_id.getText().trim();
        if (searchName.isEmpty()) 
        {
            this.loadDetails();; 
            return;
        }
        
        ArrayList<LabVisit> patients = this.performTestManager.getLabVisits(searchName);
        labVisitList.clear(); 
        labVisitList.addAll(patients); 
        this.PatientVisits.setItems(labVisitList);
    }

	@Override
	public void loadDetails() throws ClassNotFoundException, SQLException 
	{
		ArrayList<LabVisit> list = this.performTestManager.getLabVisits();
		this.labVisitList.clear();
		labVisitList.addAll(list); 
        this.PatientVisits.setItems(labVisitList); 
	}


}
