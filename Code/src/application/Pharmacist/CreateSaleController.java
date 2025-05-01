package application.Pharmacist;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import hospital.BedAllocation;
import hospital.Due;
import hospital.Medicine;
import hospital.MedicineSale;
import hospital.Sale;
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
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import manager.MedicineSaleManager;

public class CreateSaleController implements ControllerIF
{
	private User user;
	private MedicineSaleManager medicineSaleManager;
	private ObservableList<Medicine> medList = FXCollections.observableArrayList();
	private ObservableList<MedicineSale> billList = FXCollections.observableArrayList();
	BorderPane b_pane;
	
	
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
	    private TableView<Medicine> Available_tb;

	    @FXML
	    private TableView<MedicineSale> Bill_tb;

	    @FXML
	    private TextField Med_name;

	    @FXML
	    private Button add_med_btn;

	    @FXML
	    private TableColumn<MedicineSale,String> bill_id_t;

	    @FXML
	    private TableColumn<MedicineSale,String> bill_name_t;

	    @FXML
	    private TableColumn<MedicineSale,Float> bill_price_t;

	    @FXML
	    private TableColumn<MedicineSale,Integer> bill_quant_t;

	    @FXML
	    private Button confirm_sale_btn;

	    @FXML
	    private TableColumn<Medicine,String> med_id_t;

	    @FXML
	    private TableColumn<Medicine,Float> med_price_t;

	    @FXML
	    private TableColumn<Medicine,Integer> med_quant;

	    @FXML
	    private TableColumn<Medicine,String> name_id_t;

	    @FXML
	    private Button removeMed_btn;

	    @FXML
	    private Button search_med_btn;

	    @FXML
	    private TextField total_amount;
	    
	    @FXML
	    private TextField quant_f;
	    
	@FXML
	public void initialize()
	{
		med_id_t.setCellValueFactory(new PropertyValueFactory<>("id"));
		name_id_t.setCellValueFactory(new PropertyValueFactory<>("name"));
		med_price_t.setCellValueFactory(new PropertyValueFactory<>("price"));
		med_quant.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		
		bill_id_t.setCellValueFactory(cellData -> 
        new javafx.beans.property.SimpleStringProperty(cellData.getValue().getMedicine().getId()));
		bill_name_t.setCellValueFactory(cellData -> 
        new javafx.beans.property.SimpleStringProperty(cellData.getValue().getMedicine().getName()));
		bill_quant_t.setCellValueFactory(cellData -> 
        new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());
		bill_price_t.setCellValueFactory(cellData -> 
        new javafx.beans.property.SimpleFloatProperty(cellData.getValue().getSubTotal()).asObject());		
	}

    @FXML
    void add_med(MouseEvent event) throws ClassNotFoundException, SQLException 
    {
    	Medicine medSale = this.Available_tb.getSelectionModel().getSelectedItem();
    	if(medSale==null)
    	{
    		this.showAlert("Operation unsuccessful", "Please select a medicine to add");
    		return;
    	}
    	
    	//Quantity  checks
    	String quant = this.quant_f.getText();
    	int quantity;
    	if(quant.isEmpty())
    	{
    		this.showAlert("Operation unsuccessful", "Please enter quantity");
    		return;
    	}
    	
    	try {
            quantity = Integer.parseInt(quant);
        } catch (NumberFormatException e) 
    	{
           this.showAlert("Operation Unsuccessful", "Please enter a valid quantity");
           return;
        }
    	
    	if(quantity<=0)
    	{
    		 this.showAlert("Operation Unsuccessful", "Please enter a valid quantity");
    		 return;
    	}
    	
    	if(!this.medicineSaleManager.addMedicine(medSale.getId(), quantity))
    	{
    		this.showAlert("Operation Unsuccessful", "Not enough quantity available");
    	}
    	else
    	{
    		this.quant_f.setText("");
    		this.search_medicine();
    		this.updateBill();
    	}
    	
    }

    @FXML
    void confirm_sale(MouseEvent event) throws ClassNotFoundException, SQLException, IOException 
    {
    	if(this.medicineSaleManager.getSale().getMedicineSales().size()==0)
    	{
    		this.showAlert("Operation Unsuccessful", "Bill can't be empty");
    		return;
    	}
    	
    	Sale temp = this.getMedicineSaleManager().getSale();
    	if(!this.medicineSaleManager.endSale())
    	{
    		this.showAlert("Error", "Error saving sale");
    		return;
    	}
    	
    	Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ShowSale" + ".fxml"));
        root = loader.load();
        ShowSaleController controller = loader.getController();
        controller.setMedicineSaleManager(medicineSaleManager);
        controller.setB_pane(b_pane);
        controller.setUser(user);
        
        controller.setSale(temp);
        controller.setPrevPage("Create_Sale");
        controller.loadDetails();
        b_pane.setCenter(root);

    }

    @FXML
    void search_med(MouseEvent event) throws ClassNotFoundException, SQLException 
    {
    	this.search_medicine();
    }
    
    void search_medicine() throws ClassNotFoundException, SQLException
    {
    	String searchName = this.Med_name.getText().trim();
        if (searchName.isEmpty()) 
        {
            load(); 
            return;
        }
        ArrayList<Medicine> list = this.medicineSaleManager.getMedicines(searchName);
		this.medList.clear();
		medList.addAll(list); 
        this.Available_tb.setItems(medList); 
    }
    
    @FXML
    void remove_med(MouseEvent event) throws ClassNotFoundException, SQLException 
    {
    	MedicineSale medSale = this.Bill_tb.getSelectionModel().getSelectedItem();
    	if(medSale==null)
    	{
    		this.showAlert("Operation unsuccessful", "Please select a medicine from bill to remove");
    		return;
    	}
    	this.medicineSaleManager.removeMedicine(medSale.getMedicine().getId());
    	this.search_medicine();
    	this.updateBill();
    }
    
    public void updateBill()
    {
    	ArrayList<MedicineSale> saleList = this.medicineSaleManager.getSale().getMedicineSales();
    	this.billList.clear();
    	this.billList.addAll(saleList);
    	this.Bill_tb.setItems(billList);
    	this.total_amount.setText(Float.toString( this.medicineSaleManager.getSale().getTotal()));
    }
    
	@Override
	public void loadDetails() throws ClassNotFoundException, SQLException 
	{
		LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-yyyy h:mm a");
        String dt = now.format(formatter); 
		this.medicineSaleManager.createNewSale(dt);
		this.addRetrictions();
		this.load();
	}
	
	public void load() throws ClassNotFoundException, SQLException
	{
		ArrayList<Medicine> list = this.medicineSaleManager.getMedicines();
		this.medList.clear();
		medList.addAll(list); 
        this.Available_tb.setItems(medList); 	
	}

	@Override
	public void setB_pane(BorderPane p) {
		// TODO Auto-generated method stub
		this.b_pane=p;
	}
	
	void addRetrictions()
	{
		 this.quant_f.setTextFormatter(new TextFormatter<>(change -> {
	            if (change.getText().matches("\\d*")) {
	                return change;
	            }
	            return null;
	        }));
	}
	

}
