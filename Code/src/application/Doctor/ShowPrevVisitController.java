package application.Doctor;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

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
import manager.CheckupPatientManager;

public class ShowPrevVisitController implements DoctorControllerIF
{
	User user;
	private String presc;
	private BorderPane b_pane;
	
	public BorderPane getB_pane() {
		return b_pane;
	}

	public void setB_pane(BorderPane b_pane) {
		this.b_pane = b_pane;
	}

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
	public String getPresc() {
		return presc;
	}

	public void setPresc(String presc) {
		this.presc = presc;
	}

    @FXML
    private TableView<DoctorVisit> PatientVisits;

    @FXML
    private Button back_btn;

    @FXML
    private TableColumn<DoctorVisit,String> dateTime_t;

    @FXML
    private TableColumn<DoctorVisit,String> doctorName_t;

    @FXML
    private TextField f_patient_name;

    @FXML
    private Button getInfo;

    @FXML
    private TableColumn<DoctorVisit,String> medical_issue_t;

    @FXML
    private TableColumn<DoctorVisit,Integer> visitNo_t;
    
    @FXML
    public void initialize()
    {
    	dateTime_t.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
    	visitNo_t.setCellValueFactory(new PropertyValueFactory<>("visitNo"));
    	doctorName_t.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDoctor().getName()));
    	medical_issue_t.setCellValueFactory(new PropertyValueFactory<>("medicalIssue"));
    }

    @FXML
    void getInfo(MouseEvent event) throws IOException, ClassNotFoundException, SQLException
    {
    	DoctorVisit v = this.PatientVisits.getSelectionModel().getSelectedItem();
    	if(v==null)
    	{
    		this.showAlert("Operation Unsuccessful", "Please select a visit.");
    		return;
    	}
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("Show_visit.fxml"));
    	Parent root=null;
        try 
        {
            root = loader.load();
            
        } catch (IOException ex) {
            ex.printStackTrace(); 
        }
        b_pane.setCenter(root);
        
        ShowVisitController controller = loader.getController();
        controller.setB_pane(b_pane);
        controller.setUser(user);
        controller.setB_pane(b_pane);
        controller.setPresc(presc);
        controller.setVisitNo(v.getVisitNo());
        controller.setCheckupPatientManager(checkUpPatientManager);
        controller.loadDetails();
    }
    


	@Override
	public void loadDetails() throws ClassNotFoundException, SQLException 
	{
		this.f_patient_name.setText( this.checkUpPatientManager.getDoctorVisit().getPatient().getName() );
		ArrayList<DoctorVisit> list = this.checkUpPatientManager.getPatientVisitHistory();
		this.doctorVisitList.clear();
		doctorVisitList.addAll(list); 
        this.PatientVisits.setItems(doctorVisitList);
		
	}
	
	@FXML
	public void goBack()
	{
    	Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Checkup.fxml"));

        try {
            root = loader.load();
            
        } catch (IOException ex) {
            ex.printStackTrace(); 
        }
        b_pane.setCenter(root);
        
        CheckupController controller = loader.getController();
        controller.initialize();
        controller.setB_pane(b_pane);
        controller.setUser(user);
        controller.setPrescription(presc);
        controller.setCheckupPatientManager(checkUpPatientManager);
        controller.loadDetails();
	}

}
