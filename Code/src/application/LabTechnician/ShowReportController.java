package application.LabTechnician;


import java.io.IOException;
import java.sql.SQLException;

import hospital.LabVisit;
import hospital.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import manager.PerformTestManager;

public class ShowReportController implements LabTechnicianControllerIF 
{
	User user;
	PerformTestManager performTestManager;
	BorderPane b_pane;
	int visitNo;
	
	
    public int getVisitNo() {
		return visitNo;
	}

	public void setVisitNo(int visitNo) {
		this.visitNo = visitNo;
	}

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
	    private Button back_btn;

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


    void initialize(String PID)
    {
    	f_patient_id.setText(PID);
    	
    	// fill others by ID
    }
    @FXML
    void print(MouseEvent event) 
    {
    	this.showAlert("Operation Successful", "Printed");
    }

	@Override
	public void loadDetails() throws ClassNotFoundException, SQLException 
	{
		LabVisit visit = this.performTestManager.getLabVisit(visitNo);
		
		if(visit!=null)
    	{
    		this.f_patient_name.setText(visit.getPatient().getName());
    		this.f_patient_Age.setText(Integer.toString(visit.getPatient().getAge()));
    		this.f_patient_id.setText(visit.getPatient().getCnic());
    		this.f_sample_no.setText(Integer.toString(visit.getSampleNumber()));
    		this.f_dateTime.setText(visit.getDateTime());
    		this.f_labTest.setText(visit.getTestName());
    		String rep = visit.getReport();
    		rep = rep.replace("\\n", "\n");
    		this.Report_area.setText(rep);
    	}
		
	}
	@FXML
	public void back(MouseEvent e) throws ClassNotFoundException, SQLException, IOException
	{
		Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("lab_reports.fxml"));
        root = loader.load();
        b_pane.setCenter(root);
        
        LabReportsController controller = loader.getController();
        controller.setB_pane(b_pane);
        controller.setUser(user);
        controller.setPerformTestManager(performTestManager);
        controller.loadDetails();
	}


}
