package application.Admin;

import java.sql.SQLException;
import java.util.ArrayList;

import hospital.LabTechnician;
import hospital.Nurse;
import hospital.Schedule;
import hospital.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import manager.AdminManager;

public class HNurseController implements CotrollerIF
{
	private AdminManager adminManager;
	private User user;
	private ObservableList<Nurse> obsList = FXCollections.observableArrayList();
	private boolean roomsFilled=false;
    @FXML
    private Button Delete_btn;

    @FXML
    private Button Edit_btn;

    @FXML
    private TableView<Nurse> List;

    @FXML
    private TextField Phone_f;

    @FXML
    private TextField Speciality_f;

    @FXML
    private CheckBox Sunday;

    @FXML
    private Button add_update_btn;

    @FXML
    private TextField addr_f;

    @FXML
    private TextField age_f;

    @FXML
    private AnchorPane ap;

    @FXML
    private TextField cnic_f;

    @FXML
    private CheckBox friday;

    @FXML
    private MenuButton genderBtn;

    @FXML
    private MenuItem gitem1;

    @FXML
    private MenuItem gitem2;

    @FXML
    private CheckBox monday;

    @FXML
    private TextField name_f;

    @FXML
    private TableColumn<Nurse,String> name_t;

    @FXML
    private TextField password_f;


    @FXML
    private MenuButton room_btn;

    @FXML
    private CheckBox saturday;

    @FXML
    private Button search_btn;

    @FXML
    private TextField search_name;

    @FXML
    private CheckBox thursday;

    @FXML
    private CheckBox tuesday;

    @FXML
    private TextField username_f;

    @FXML
    private TableColumn<Nurse,String> username_t;

    public AdminManager getAdminManager() {
		return adminManager;
	}

	public void setAdminManager(AdminManager adminManager) {
		this.adminManager = adminManager;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@FXML
    private CheckBox wednesday;

    @FXML
    void Delete(MouseEvent event) throws ClassNotFoundException, SQLException 
    {
    	Nurse d = this.List.getSelectionModel().getSelectedItem();
    	if(d==null)
    	{
    		this.showAlert("Falure", "Please select a record");
    		return;
    	}
    	
    	if(this.adminManager.removeNurse((d.getUsername())))
    	{
    		this.showAlert("Success", "Record deleted");
    		this.search(null);
    		return;
    	}
    	else
    	{
    		this.showAlert("Falure", "Error deleting");
    		return;
    	}
    }

    @FXML
    void Edit(MouseEvent event) 
    {
    	Nurse selectedDoctor = List.getSelectionModel().getSelectedItem();
        if (selectedDoctor == null) {
            this.showAlert("Failed", "Please select a record");
            return;
        }
        String username = selectedDoctor.getUsername();

        try {
        	Nurse doctor = this.adminManager.getNurse(username);
            if (doctor != null) 
            {
                name_f.setText(doctor.getName());
                addr_f.setText(doctor.getAddress());
                cnic_f.setText(doctor.getCnic());
                Phone_f.setText(doctor.getPhoneNumber());
                age_f.setText(String.valueOf(doctor.getAge()));
                username_f.setText(doctor.getUsername());
                password_f.setText(doctor.getPassword());
                Speciality_f.setText(String.valueOf(doctor.getWorkExperience()));
                

                if ("Male".equalsIgnoreCase(doctor.getGender())) {
                    genderBtn.setText("Male");
                } else if ("Female".equalsIgnoreCase(doctor.getGender())) {
                    genderBtn.setText("Female");
                } else {
                    genderBtn.setText("Other");
                }
                
                room_btn.setText(String.valueOf( doctor.getRoomAssigned()));
                

                ArrayList<String> scheduleDays = doctor.getSchedule().getDays();
                monday.setSelected(scheduleDays.contains("Monday"));
                tuesday.setSelected(scheduleDays.contains("Tuesday"));
                wednesday.setSelected(scheduleDays.contains("Wednesday"));
                thursday.setSelected(scheduleDays.contains("Thursday"));
                friday.setSelected(scheduleDays.contains("Friday"));
                saturday.setSelected(scheduleDays.contains("Saturday"));
                Sunday.setSelected(scheduleDays.contains("Sunday"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @FXML
    void add_update(MouseEvent event) throws ClassNotFoundException, SQLException 
    {
    	try {
	        if (name_f.getText().trim().isEmpty() || 
	            addr_f.getText().trim().isEmpty() || 
	            cnic_f.getText().trim().isEmpty() || 
	            Phone_f.getText().trim().isEmpty() || 
	            age_f.getText().trim().isEmpty() || 
	            genderBtn.getText().equals("Select Gender") || 
	            Speciality_f.getText().trim().isEmpty() || 
	            username_f.getText().trim().isEmpty() || 
	            password_f.getText().trim().isEmpty() ||
	            room_btn.getText().trim().equals("Select")) {
	        	
	        	this.showAlert("Failed", "Please fill all details");
	            return;
	        }

	        int age = Integer.parseInt(age_f.getText().trim());
	        if (age < 1 || age > 100) {
	        	this.showAlert("Failed", "Age must be 1-100");
	            return;
	        }
	        
	        int exp = Integer.parseInt(Speciality_f.getText().trim());
	        if (exp < 1 || exp > 100) {
	        	this.showAlert("Failed", "Experience must be 1-100");
	            return;
	        }
	        
	        int roomNo = Integer.parseInt(room_btn.getText().trim());

	        String gender = genderBtn.getText();

	        Schedule schedule = new Schedule();
	        if (monday.isSelected()) schedule.addDay("Monday");
	        if (tuesday.isSelected()) schedule.addDay("Tuesday");
	        if (wednesday.isSelected()) schedule.addDay("Wednesday");
	        if (thursday.isSelected()) schedule.addDay("Thursday");
	        if (friday.isSelected()) schedule.addDay("Friday");
	        if (saturday.isSelected()) schedule.addDay("Saturday");
	        if (Sunday.isSelected()) schedule.addDay("Sunday");

	        boolean success = adminManager.addUpdateNurse (
	            name_f.getText().trim(),
	            addr_f.getText().trim(),
	            cnic_f.getText().trim(),
	            Phone_f.getText().trim(),
	            age,
	            schedule,
	            gender,
	            exp,
	            username_f.getText().trim(),
	            password_f.getText().trim(),
	            roomNo
	        );

	        if (success) {
	        	this.showAlert("Success", "Record Added/Updated");
	        	this.clearFields();
	            this.loadDeatails(); 
	        } else {
	        	this.showAlert("Failure", "Error saving");
	        }
	    } catch (NumberFormatException e) {
	    	this.showAlert("Error", "Invalid details");
	    } 
    }

    @FXML
    void handleGenderSelect(ActionEvent event) 
    {
    	 MenuItem selectedMenuItem = (MenuItem) event.getSource();
         String selectedGender = selectedMenuItem.getText();
         genderBtn.setText(selectedGender);
    }
	@FXML
	void initialize()
	{
		name_t.setCellValueFactory(new PropertyValueFactory<>("name"));
        username_t.setCellValueFactory(new PropertyValueFactory<>("username"));	}

    @FXML
    void search(MouseEvent event) throws ClassNotFoundException, SQLException 
    {
    	String searchname = this.search_name.getText().trim();
    	if(searchname.isEmpty())
    	{
    		this.loadDeatails();
    		return;
    	}
    	ArrayList<Nurse> doctors = this.adminManager.getNurses(searchname);
        obsList.clear(); 
        obsList.addAll(doctors); 
        List.setItems(obsList); 
    }

	@Override
	public void loadDeatails() throws ClassNotFoundException, SQLException 
	{
		this.applyInputRestrictions();
		this.applyInputRestrictions();
		ArrayList<Nurse> doctors = this.adminManager.getNurses();
        obsList.clear(); 
        obsList.addAll(doctors); 
        List.setItems(obsList);
        if(!this.roomsFilled)
        {
        	this.fillRooms();
        }
	}
	
	public void fillRooms() throws ClassNotFoundException, SQLException
	{
		ArrayList<Integer> rooms = this.adminManager.getRooms();
		for(Integer i : rooms)
		{
			 MenuItem menuItem = new MenuItem(String.valueOf(i));
			 menuItem.setOnAction(event -> room_btn.setText(String.valueOf(i)));
	         room_btn.getItems().add(menuItem);
		}
		this.roomsFilled=true;
	}
	private void applyInputRestrictions() 
    {
        username_f.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.startsWith("n-")) {
                username_f.setText("n-");
                username_f.positionCaret(username_f.getText().length());
            }
        });

    	
        cnic_f.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getText().matches("\\d*")) {
                return change;
            }
            return null;
        }));
        
        Phone_f.setTextFormatter(new TextFormatter<>(change -> {
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


        name_f.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getText().matches("[a-zA-Z ]*")) {
                return change;
            }
            return null;
        }));

        Speciality_f.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().matches("\\d{0,3}")) { 
                if (!change.getControlNewText().isEmpty()) {
                    int exp = Integer.parseInt(change.getControlNewText());
                    if (exp >= 1 && exp <= 100) {
                        return change;
                    }
                } else {
                    return change; 
                }
            }
            return null;
        }));
    }
	 @FXML
	    void clearFields() {
	        name_f.clear();
	        addr_f.clear();
	        cnic_f.clear();
	        Phone_f.clear();
	        age_f.clear();
	        Speciality_f.clear();
	        username_f.clear();
	        password_f.clear();
	        search_name.clear();

	        genderBtn.setText("Male");

	        monday.setSelected(false);
	        tuesday.setSelected(false);
	        wednesday.setSelected(false);
	        thursday.setSelected(false);
	        friday.setSelected(false);
	        saturday.setSelected(false);
	        Sunday.setSelected(false);
	        this.room_btn.setText("Select");;

	    }
	 

}
