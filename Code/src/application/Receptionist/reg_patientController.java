
package application.Receptionist;

import java.sql.SQLException;

import dbhandler.PatientDBHandler;
import hospital.Patient;
import hospital.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.MouseEvent;
import manager.AdmitPatientManager;
import manager.DischargePatientManager;
import manager.PatientVisitManager;

public class reg_patientController implements ReceptionistControllerIF
{
	private User user;
	private PatientVisitManager patientVisitManager;
	private AdmitPatientManager admitPatientManager;
	private DischargePatientManager dischargePatientManager;
	
	
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
    private TextField age_f;

	    @FXML
	    private MenuItem bitem1;

	    @FXML
	    private MenuItem bitem2;

	    @FXML
	    private MenuItem bitem3;

	    @FXML
	    private MenuItem bitem4;

	    @FXML
	    private MenuButton bldGrpBtn;

	    @FXML
	    private Button clear_btn;

	    @FXML
	    private TextField f_address;

	    @FXML
	    private TextField f_cnic;

	    @FXML
	    private TextField f_name;

	    @FXML
	    private TextField f_phoneno;

	    @FXML
	    private MenuButton genderBtn;

	    @FXML
	    private MenuItem gitem1;

	    @FXML
	    private MenuItem gitem2;

	    @FXML
	    private Button reg_btn;
	    
	    @FXML
	    public void initialize()
	    {
	    }

	    @FXML
	    void Clear_fields(MouseEvent event) 
	    {
	    	f_name.clear();
	        f_address.clear();
	        f_cnic.clear();
	        f_phoneno.clear();
	    	age_f.clear();
	    }

	    @FXML
	    void register_patient_fnc(MouseEvent event) throws ClassNotFoundException, SQLException 
	    {
	    	// Retrieve values from input fields
	        String name = f_name.getText().trim();
	        String address = f_address.getText().trim();
	        String cnic = f_cnic.getText().trim();
	        String phoneNumber = f_phoneno.getText().trim();
	        String gender = genderBtn.getText().trim();
	        String bloodGroup = bldGrpBtn.getText().trim();
	        String age = bldGrpBtn.getText().trim();
	        int numAge;

	        // Validate input fields
	        if (name.isEmpty() || address.isEmpty() || cnic.isEmpty() || phoneNumber.isEmpty() || age.isEmpty()) 
	        {
	        	this.showAlert("Operation Unsuccessful", "Please fill in all the details.");
	            return;
	        }
	        
	        String regex = "^[a-zA-Z ]+$";
	        if(!name.matches(regex))
	        {
	        	this.showAlert("Failed", "Please enter a valid name");
	        	return;
	        }
	        
	        if(!cnic.matches("\\d+"))
	        {
	        	this.showAlert("Failed", "Please enter a valid CNIC");
	        	return;
	        }
	        
	        if(!phoneNumber.matches("\\d+"))
	        {
	        	this.showAlert("Failed", "Please enter a valid phone number");
	        	return;
	        }
	        
	        try
	        {
	        	numAge = Integer.parseInt(age);
	        } catch(NumberFormatException e)
	        {
	        	this.showAlert("Error", "Invalid age");
	        	return;
	        }

	        Patient patient = new Patient(name, address, cnic, phoneNumber, numAge, gender, bloodGroup);
	        boolean success = PatientDBHandler.getpatientDBHandler().savePatient(patient);
	        if (success) {
	            System.out.println("Patient registered successfully.");
	            Clear_fields(null); // Clear fields after successful registration
	        } else {
	            System.out.println("Failed to register patient. Please try again.");
	        }
	    }
	    
	    @FXML
	    void handleGenderSelect(ActionEvent event) {
	        MenuItem selectedMenuItem = (MenuItem) event.getSource();

	        genderBtn.setText(selectedMenuItem.getText());
	    }

	    @FXML
	    void handleBldGrpChange(ActionEvent event) {
	        MenuItem selectedMenuItem = (MenuItem) event.getSource();

	        bldGrpBtn.setText(selectedMenuItem.getText());
	    }
	    public void loadDetails() throws ClassNotFoundException, SQLException
	    {
	    	this.addRestrictions();
	    }
	    
	    public void addRestrictions()
	    {
	    	this.f_name.setTextFormatter(new TextFormatter<>(change -> {
	            if (change.getText().matches("[a-zA-Z ]*")) {
	                return change;
	            }
	            return null;
	        }));
	    	
	    	this.f_cnic.setTextFormatter(new TextFormatter<>(change -> {
	            if (change.getText().matches("\\d*")) {
	                return change;
	            }
	            return null;
	        }));
	    	
	    	this.f_phoneno.setTextFormatter(new TextFormatter<>(change -> {
	            if (change.getText().matches("\\d*")) {
	                return change;
	            }
	            return null;
	        }));
	    	 age_f.setTextFormatter(new TextFormatter<>(change -> {
	             if (change.getControlNewText().matches("\\d{0,3}")) { 
	                 if (!change.getControlNewText().isEmpty()) {
	                     int age = Integer.parseInt(change.getControlNewText());
	                     if (age >= 1 && age <= 100) {
	                         return change;
	                     }
	                 } else {
	                     return change; 
	                 }
	             }
	             return null;
	         }));

	    }
	    
	    

}
