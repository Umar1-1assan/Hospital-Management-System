package application.Receptionist;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import hospital.BedAllocation;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DischargeRecptController 
{
	private BedAllocation bedAlloc;

    @FXML
    private TextField bed_f;

    @FXML
    private TextField cnic_f;

    @FXML
    private TextField dateTime_f;

    @FXML
    private TextField pName_f;

    @FXML
    private AnchorPane paid;

    @FXML
    private Button print_btn;

    @FXML
    void print(MouseEvent event) 
    {
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("Operation Successful");
    	alert.setHeaderText("");
    	alert.setContentText("Printed");
    	alert.showAndWait();
    	Stage s =(Stage)this.print_btn.getScene().getWindow();
    	s.close();
    }

	public BedAllocation getBedAlloc() 
	{
		return bedAlloc;
	}

	public void setBedAlloc(BedAllocation bedAlloc) 
	{
		this.bedAlloc = bedAlloc;
	}
	
	public void loadDetails()
	{
		this.pName_f.setText(this.bedAlloc.getPatient().getName());
		this.cnic_f.setText(this.bedAlloc.getPatientCnic());
		String bed = "Room-" + bedAlloc.getRoomNo() +" Bed- "+bedAlloc.getBedNo();
		this.bed_f.setText(bed);
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-yyyy h:mm a");
        String formattedDateTime = now.format(formatter);
        this.dateTime_f.setText(formattedDateTime);
	}
    
}
