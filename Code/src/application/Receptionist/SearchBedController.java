package application.Receptionist;

import java.sql.SQLException;
import java.util.ArrayList;

import application.Receptionist.SearchBedController.BedSelectedCallback;
import application.Receptionist.Search_DoctorController.DoctorSelectedCallback;
import dbhandler.RoomDBHandler;
import hospital.Bed;
import hospital.Patient;
import hospital.Room;
import hospital.RoomBed;
import hospital.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import manager.AdmitPatientManager;
import manager.DischargePatientManager;
import manager.PatientVisitManager;

public class SearchBedController implements ReceptionistControllerIF
{
	private User user;
	private PatientVisitManager patientVisitManager;
	private AdmitPatientManager admitPatientManager;
	private DischargePatientManager dischargePatientManager;
	private ObservableList<RoomBed> bedList = FXCollections.observableArrayList();

	
	
    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public PatientVisitManager getPatientVisitManager() {
		return patientVisitManager;
	}

	public void setPatientVisitManager(PatientVisitManager patientVisitManager) {
		this.patientVisitManager = patientVisitManager;
	}

	public AdmitPatientManager getAdmitPatientManager() {
		return admitPatientManager;
	}

	public void setAdmitPatientManager(AdmitPatientManager admitPatientManager) {
		this.admitPatientManager = admitPatientManager;
	}

	public DischargePatientManager getDischargePatientManager() {
		return dischargePatientManager;
	}

	public void setDischargePatientManager(DischargePatientManager dischargePatientManager) {
		this.dischargePatientManager = dischargePatientManager;
	}

    @FXML
    private TableColumn<RoomBed, Integer> bedNo_t;

    @FXML
    private AnchorPane confirm_select;

    @FXML
    private Button confirm_selection;

    @FXML
    private TableColumn<RoomBed, Integer> roomNo_t;

    @FXML
    private TableView<RoomBed> room_table;

    @FXML
    private TableColumn<RoomBed, String> status_t; 

    @FXML
    private TableColumn<RoomBed, Float> price_t;

    private BedSelectedCallback callback;
    
    public void initialize() {
        confirm_selection.setOnAction(event -> selectBed());
        roomNo_t.setCellValueFactory(new PropertyValueFactory<>("roomNo"));
        bedNo_t.setCellValueFactory(new PropertyValueFactory<>("bedNo"));
        status_t.setCellValueFactory(new PropertyValueFactory<>("status"));
        price_t.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    public void loadDetails() throws ClassNotFoundException, SQLException
    {
    	ArrayList<Room> rooms = RoomDBHandler.getRoomDBHandler().getAvailableBeds();
    	for (Room room : rooms) {
            for (Bed bed : room.getBeds()) 
            {
            	String s = "Occupied";
            	if(bed.isAvailable())
            		s="Available";
                bedList.add(new RoomBed(room.getRoomNumber(), bed.getBedNumber(), s, bed.getPricePerDay()));
            }
        }
    	
    	room_table.setItems(bedList);
    }
    
    public void setOnBedSelectedCallback(BedSelectedCallback callback) {
        this.callback = callback;
    }
    

    private void selectBed() 
    {
    	RoomBed rb = this.room_table.getSelectionModel().getSelectedItem(); 
    	
    	if(rb==null)
    	{
    		//**Show error
    		return;
    	}
        if (callback != null) {
            callback.onBedSelected(Integer.toString(rb.getBedNo()),Integer.toString(rb.getRoomNo()));
        }

        // Close the dialog
        Stage stage = (Stage) confirm_selection.getScene().getWindow();
        stage.close();
    }

    // Interface for callback
    public interface BedSelectedCallback {
        void onBedSelected(String BedId, String RoomId);
    }
}
