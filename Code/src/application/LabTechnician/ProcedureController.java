package application.LabTechnician;

import java.io.IOException;
import java.sql.SQLException;

import application.Doctor.ViewQueueController;
import hospital.DoctorVisit;
import hospital.LabVisit;
import hospital.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import manager.PerformTestManager;

public class ProcedureController implements LabTechnicianControllerIF
{
	User user;
	PerformTestManager performTestManager;
	BorderPane b_pane;

    public User getUser() {
		return user;
	}

	public BorderPane getB_pane() {
		return b_pane;
	}

	public void setB_pane(BorderPane b_pane) {
		this.b_pane = b_pane;
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
    private TextArea Report_area;

    @FXML
    private TextField f_dateTime;

    @FXML
    private TextField f_labTest;

    @FXML
    private TextField f_patient_Age;

    @FXML
    private TextField f_patient_id;

    @FXML
    private TextField f_patient_name;

    @FXML
    private TextField f_sample_no;

    @FXML
    private Button print_btn;

    @FXML
    private Button save_btn;

    
    @FXML
    public void initialize()
    {
    	
    }

	@Override
	public void loadDetails() 
	{
		LabVisit visit = this.performTestManager.getLabVisit();
    	
    	if(visit!=null)
    	{
    		this.f_patient_name.setText(visit.getPatient().getName());
    		this.f_patient_Age.setText(Integer.toString(visit.getPatient().getAge()));
    		this.f_patient_id.setText(visit.getPatient().getCnic());
    		this.f_sample_no.setText(Integer.toString(visit.getSampleNumber()));
    		this.f_dateTime.setText(visit.getDateTime());
    		this.f_labTest.setText(visit.getTestName());
    	}
	}
	@FXML
	void print(MouseEvent e)
	{
		this.showAlert("Operation Successful", "Printed");
	}
	
	@FXML
	public void save(MouseEvent event) throws IOException, ClassNotFoundException, SQLException
	{
    	this.performTestManager.endProcedure(this.Report_area.getText());
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewQueue.fxml"));
    	Parent root = null;
        root=loader.load();
        b_pane.setCenter(root);
        
        View_QueueController controller = loader.getController();
        controller.initialize(b_pane);
        controller.setUser(user);
        controller.setPerformTestManager(performTestManager);
        controller.loadDetails();
	}

}
