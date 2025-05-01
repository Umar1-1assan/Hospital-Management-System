package application.Pharmacist;

import java.io.IOException;
import java.sql.SQLException;

import application.Doctor.DoctorControllerIF;
import application.Doctor.ViewQueueController;
import hospital.Doctor;
import hospital.Pharmacist;
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
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import manager.MedicineSaleManager;

public class Pharmacist_menuController implements ControllerIF
{
	private User user;
	private MedicineSaleManager medicineSaleManager;
	
	
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

    public MedicineSaleManager getMedicineSaleManager() {
		return medicineSaleManager;
	}

	public void setMedicineSaleManager(MedicineSaleManager medicineSaleManager) {
		this.medicineSaleManager = medicineSaleManager;
	}

	   @FXML
	    private Button Add_Stock;

	    @FXML
	    private Button Completed_Sale;

	    @FXML
	    private Button Create_Sale;

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
	    private VBox vb;
    

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
    void Add_Stock(MouseEvent event) throws IOException, ClassNotFoundException, SQLException {
    	loadPage("Add_Stock");
    }

    @FXML
    void Completed_Sale(MouseEvent event) throws IOException, ClassNotFoundException, SQLException {
    	loadPage("Completed_Sale");
    }

    @FXML
    void Create_Sale(MouseEvent event) throws IOException, ClassNotFoundException, SQLException {
    	loadPage("Create_Sale");
    }

    @FXML
    void Pharmacist_menu(MouseEvent event) {
    	bp.setCenter (ap);
    }
    
    private void loadPage (String page) throws IOException, ClassNotFoundException, SQLException
	{
    	Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource(page + ".fxml"));
        root = loader.load();
        this.medicineSaleManager.cancelSale();
        ControllerIF controller = loader.getController();
        controller.setMedicineSaleManager(medicineSaleManager);
        controller.setB_pane(bp);
        controller.setUser(user);
        controller.loadDetails();
        bp.setCenter(root);
	}

	@Override
	public void loadDetails() throws ClassNotFoundException, SQLException 
	{
    	Pharmacist d = this.medicineSaleManager.showProfile(this.user.getUsername());
    	//r.print();
    	
    	if(d!=null)
    	{
    		this.ussername_f .setText(d.getUsername());
    		this.name_f.setText(d.getName());
    		this.age_f .setText(Integer.toString(d.getAge()));
    		this.Speciality_f .setText(Integer.toString(d.getWorkExperience()) + " years");
    		this.Phone.setText(d.getPhoneNumber());
    		this.gender_f .setText(d.getGender());
    		this.addr_f .setText(d.getAddress());
    		this.cnic_f.setText(d.getCnic());
    	}
		
	}

	@Override
	public void setB_pane(BorderPane p) 
	{
		this.bp=p;
		
	}

}
