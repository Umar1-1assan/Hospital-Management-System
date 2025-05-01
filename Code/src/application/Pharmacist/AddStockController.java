package application.Pharmacist;

import java.sql.SQLException;
import java.util.ArrayList;

import hospital.Medicine;
import hospital.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import manager.MedicineSaleManager;

public class AddStockController implements ControllerIF 
{
	private User user;
	private MedicineSaleManager medicineSaleManager;
	BorderPane b_pane;
	private ObservableList<Medicine> medList = FXCollections.observableArrayList();

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
    private Button Add_btn;

    @FXML
    private TableView<Medicine> Available_tb;

    @FXML
    private TextField Med_id;

    @FXML
    private TextField Med_name;

    @FXML
    private TextField Med_name_search;


    @FXML
    private TextField Med_price;

    @FXML
    private TextField Med_quant;

    @FXML
    private Button edit_btn;

    @FXML
    private TableColumn<Medicine,String> med_id_t;

    @FXML
    private TableColumn<Medicine,Float> med_price_t;

    @FXML
    private TableColumn<Medicine,Integer> med_quant;

    @FXML
    private TableColumn<Medicine,String> name_id_t;

    @FXML
    private Button search_med_btn;
    
    @FXML
    public void initialize()
    {
    	med_id_t.setCellValueFactory(new PropertyValueFactory<>("id"));
		name_id_t.setCellValueFactory(new PropertyValueFactory<>("name"));
		med_price_t.setCellValueFactory(new PropertyValueFactory<>("price"));
		med_quant.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    }

    @FXML
    void Add_Med(MouseEvent event) throws ClassNotFoundException, SQLException 
    {
    	String quant = this.Med_quant.getText().trim();
    	String medID = this.Med_id.getText().trim();
    	String medName = this.Med_name.getText().trim();
    	String pr = this.Med_price.getText().trim();
    	int quantity;
    	float price;
    	
    	if(quant.isEmpty() || medID.isEmpty() || medName.isEmpty() || pr.isEmpty())
    	{
    		this.showAlert("Failed", "Please fill in all the details");
    		return;
    	}
    	
    	try
    	{
    		quantity = Integer.parseInt(quant);
    		price = Float.parseFloat(pr);
    	}catch(NumberFormatException e)
    	{
    		this.showAlert("Failed", "Please fill in valid the details");
    		return;
    	}
    	
    	if(quantity<=0 || price<=0)
    	{
    		this.showAlert("Failed", "Please fill in valid the details");
    		return;
    	}
    	Medicine m = new Medicine();
    	m.setId(medID);
    	m.setName(medName);
    	m.setQuantity(quantity);
    	m.setPrice(price);
    	
    	if(this.medicineSaleManager.addUpdateMedicineInStock(m))
    	{
    		this.showAlert("Success", "Added/Updated Medicine Info");
    		this.clearFields();
    		this.search_med(null);
    		return;
    	}
    	else
    	{
    		this.showAlert("Failed", "Error updating");
    		return;
    	}
    }
    
    public void clearFields()
    {
    	this.Med_id.setText("");
    	this.Med_name.setText("");
    	this.Med_price.setText("");
    	this.Med_quant.setText("");
    }

    @FXML
    void edit(MouseEvent event) 
    {
    	Medicine med = this.Available_tb.getSelectionModel().getSelectedItem();
    	if(med==null)
    	{
    		this.showAlert("Operation Unsuccesssful","Please select a medicine to edit");
    		return;
    	}
    	
    	this.Med_id.setText(med.getId());
    	this.Med_name.setText(med.getName());
    	this.Med_price.setText(Float.toString(med.getPrice()));
    	this.Med_quant.setText(Integer.toString(med.getQuantity()));
    }

    @FXML
    void search_med(MouseEvent event) throws ClassNotFoundException, SQLException 
    {
    	String searchName = this.Med_name_search.getText().trim();
        if (searchName.isEmpty()) 
        {
            loadDetails(); 
            return;
        }
        ArrayList<Medicine> list = this.medicineSaleManager.getMedicines(searchName);
		this.medList.clear();
		medList.addAll(list); 
        this.Available_tb.setItems(medList); 
    }

	@Override
	public void loadDetails() throws ClassNotFoundException, SQLException 
	{
		ArrayList<Medicine> list = this.medicineSaleManager.getMedicines();
		this.medList.clear();
		medList.addAll(list); 
        this.Available_tb.setItems(medList);
        this.addRestrictions();
	}

	@Override
	public void setB_pane(BorderPane p) 
	{
		// TODO Auto-generated method stub
		this.b_pane=p;
	}
	
	void addRestrictions()
	{
		 this.Med_quant.setTextFormatter(new TextFormatter<>(change -> {
	            if (change.getText().matches("\\d*")) {
	                return change;
	            }
	            return null;
	        }));
		 this.Med_price .setTextFormatter(new TextFormatter<>(change -> {
			    if (change.getControlNewText().matches("\\d*(\\.\\d*)?")) {
			        return change;
			    }
			    return null;
			}));
		 this.Med_name.setTextFormatter(new TextFormatter<>(change -> {
			 if (change.getText().matches("[a-zA-Z ]*")) {
	                return change;
	            }
	            return null;
	        }));
	}

}


