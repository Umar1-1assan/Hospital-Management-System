package application.Nurse;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import hospital.Due;
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
import javafx.stage.Stage;
import manager.NurseManager;

public class UpdatePatientController implements ControllerIF
{
	private User user;
	private NurseManager nurseManager;
	BorderPane b_pane;
	private ObservableList<Due> dueList = FXCollections.observableArrayList();


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
    private TextField AllocationNo;

    @FXML
    private TextField BedNo;

    @FXML
    private TextField Descp;

    @FXML
    private TextField Price;

    @FXML
    private TextField RoomNo;

    @FXML
    private Button Save_data;

    @FXML
    private TableView<Due> Treatment;
    
    @FXML
    private Button add_data;

    @FXML
    private TableColumn<Due,String> dateTime_t;

    @FXML
    private TableColumn<Due,String> desc_t;

    @FXML
    private TableColumn<Due,Integer> dueNo_t;

    @FXML
    private TextField f_patient_name;

    @FXML
    private TableColumn<Due,Float> price_t;
    
    @FXML
    public void initialize()
    {
    	price_t.setCellValueFactory(new PropertyValueFactory<>("price"));
    	desc_t.setCellValueFactory(new PropertyValueFactory<>("description"));
    	dateTime_t.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
    }

    @FXML
    void Save_data(MouseEvent event) throws ClassNotFoundException, SQLException, IOException 
    {
    	this.nurseManager.saveBedAllocation();
    	Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TrackPatient.fxml"));
        root = loader.load();
        b_pane.setCenter(root);
        
        ControllerIF controller = loader.getController();
        controller.setB_pane(b_pane);
        controller.setUser(user);
        controller.setNurseManager(nurseManager);
        controller.loadDetails();
    }
    

    @FXML
    void add_data(MouseEvent event) 
    {
    	String desc = this.Descp.getText();
    	Float price;
    	if(desc.isEmpty())
    	{
    		this.showAlert("Operation Unuccessful", "Please fill in description field");
    		return;
    	}
    	try
    	{
    		price = Float.parseFloat(this.Price.getText());
    	} catch(NumberFormatException e)
    	{
    		this.showAlert("Operation Unuccessful", "Please enter a valid price");
    		return;
    	}
    	
    	LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-yyyy h:mm a");
        String dt = now.format(formatter);
    	
    	this.nurseManager.addDue(desc, price, dt);
    	this.clear_fields();
    	this.loadDetails();
    }
    
    public void clear_fields()
    {
    	this.Price.setText("");
    	this.Descp.setText("");
    }

	@Override
	public void loadDetails() 
	{
		this.f_patient_name.setText(this.nurseManager.getBedAllocation().getPatient().getName());
		this.AllocationNo.setText(Integer.toString(this.nurseManager.getBedAllocation().getBedAllocationNumber()));
		this.BedNo.setText(Integer.toString( this.nurseManager.getBedAllocation().getBedNo()));
		this.RoomNo.setText(Integer.toString( this.nurseManager.getBedAllocation().getRoomNo()));
		this.dueList.clear();
    	this.dueList.addAll(this.nurseManager.getBedAllocation().getDues());
    	this.Treatment.setItems(dueList);
		
	}

	@Override
	public void setB_pane(BorderPane p) 
	{
		this.b_pane=p;
	}

}
