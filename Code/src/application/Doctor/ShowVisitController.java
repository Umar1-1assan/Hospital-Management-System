package application.Doctor;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.sql.SQLException;

import hospital.DoctorVisit;
import hospital.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import manager.CheckupPatientManager;

public class ShowVisitController implements DoctorControllerIF
{
	User user;
	CheckupPatientManager checkUpPatientManager;
	BorderPane b_pane;
	private int visitNo;
	public CheckupPatientManager getCheckUpPatientManager() {
		return checkUpPatientManager;
	}

	public void setCheckUpPatientManager(CheckupPatientManager checkUpPatientManager) {
		this.checkUpPatientManager = checkUpPatientManager;
	}

	public int getVisitNo() {
		return visitNo;
	}

	public void setVisitNo(int visitNo) {
		this.visitNo = visitNo;
	}

	String presc;
	public CheckupPatientManager getCheckupPatientManager() 
	{
		return checkUpPatientManager;
	}

	public BorderPane getB_pane() {
		return b_pane;
	}

	public void setB_pane(BorderPane b_pane) {
		this.b_pane = b_pane;
	}

	public String getPresc() {
		return presc;
	}

	public void setPresc(String presc) {
		this.presc = presc;
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
    private TextArea Prescription_f;

    @FXML
    private TextField age_f;

    @FXML
    private ImageView back;

    @FXML
    private TextField cnic_f;

    @FXML
    private TextField dateTime_f;

    @FXML
    private TextField doctor_name_f;

    @FXML
    private TextField medical_Issue_f;

    @FXML
    private TextField patient_name_f;

    
    
    public void initialize()
    {
    	
    }
    

    @FXML
    void go_back(MouseEvent event) throws IOException, ClassNotFoundException, SQLException 
    {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("Show_prev_visit.fxml"));
    	Parent root=null;
        try 
        {
            root = loader.load();
            
        } catch (IOException ex) {
            ex.printStackTrace(); 
        }
        b_pane.setCenter(root);
        
        ShowPrevVisitController controller = loader.getController();
        controller.setB_pane(b_pane);
        controller.setUser(user);
        controller.setPresc(presc);
        controller.setCheckupPatientManager(checkUpPatientManager);
        controller.loadDetails();
    }

    @Override
	public void loadDetails() throws ClassNotFoundException, SQLException 
    {
		DoctorVisit visit = this.checkUpPatientManager.getDoctorVisitHistory(visitNo);
    	if(visit!=null)
    	{
    		this.patient_name_f.setText(visit.getPatient().getName());
    		this.age_f.setText(Integer.toString(visit.getPatient().getAge()));
    		this.cnic_f.setText(visit.getPatient().getCnic());
    		this.doctor_name_f.setText(visit.getDoctor().getName());
    		this.dateTime_f.setText(visit.getDateTime());
    		this.medical_Issue_f.setText(visit.getMedicalIssue());
    		String presc = visit.getPrescription();
    		presc = presc.replace("\\n", "\n");
    		this.Prescription_f.setText(presc);
    	}
		
	}

}
