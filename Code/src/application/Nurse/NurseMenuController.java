package application.Nurse;

import java.io.IOException;
import java.sql.SQLException;
import hospital.Doctor;
import hospital.Nurse;
import hospital.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import manager.NurseManager;

public class NurseMenuController implements ControllerIF
{
	
	private User user;
	private NurseManager nurseManager;
	
	

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
	    private Button Home;

	    @FXML
	    private Rectangle Image;

	    @FXML
	    private Button LogOut;

	    @FXML
	    private TextField Phone;

	    @FXML
	    private TextField Speciality_f;

	    @FXML
	    private Button TrackPatient;

	    @FXML
	    private TextField addr_f;

	    @FXML
	    private TextField age_f;

	    @FXML
	    private AnchorPane ap;

	    @FXML
	    private BorderPane bp;

	    @FXML
	    private TextField cnic_f;

	    @FXML
	    private TextField gender_f;

	    @FXML
	    private TextField name_f;

	    @FXML
	    private TextField ussername_f;
    

    @FXML
    void LogOut(MouseEvent event) throws IOException 
    {
    	FXMLLoader loader;
    	Parent parent;
    	loader = new FXMLLoader(getClass().getResource("/application/Login.fxml"));
        parent = loader.load();
        Stage stage = (Stage) this.LogOut.getScene().getWindow();
        stage.setScene(new Scene(parent));
    }


    @FXML
    void TrackPatient(MouseEvent event) throws IOException, ClassNotFoundException, SQLException 
    {
    	loadPage("TrackPatient");
    }

    @FXML
    void Nurse_Menu(MouseEvent event) 
    {
    	bp.setCenter(ap);
    }
    
    private void loadPage (String page) throws IOException, ClassNotFoundException, SQLException
	{
    	Parent root = null;
		FXMLLoader loader = null;
		loader = new FXMLLoader(getClass().getResource (page+".fxml"));
		root = loader.load();
		ControllerIF controller = loader.getController();
		controller.setNurseManager(nurseManager);
		controller.setUser(user);
		controller.setB_pane(bp);
		controller.loadDetails();
		bp.setCenter (root);
	}

	@Override
	public void loadDetails() throws ClassNotFoundException, SQLException 
	{
    	Nurse d = this.nurseManager.showProfile(this.user.getUsername());
    	//r.print();
    	
    	if(d!=null)
    	{
    		this.ussername_f .setText(d.getUsername());
    		this.name_f.setText(d.getName());
    		this.age_f .setText(Integer.toString(d.getAge()));
    		this.Speciality_f .setText((Integer.toString(d.getWorkExperience())));
    		this.Phone.setText(d.getPhoneNumber());
    		this.gender_f .setText(d.getGender());
    		this.addr_f .setText(d.getAddress());
    		this.cnic_f.setText(d.getCnic());
    	}
	}

	@Override
	public void setB_pane(BorderPane p) 
	{
		this.bp = p;
		
	}

}
