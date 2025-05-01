package application.Pharmacist;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import manager.MedicineSaleManager;

public class ShowSaleController implements ControllerIF
{
	private User user;
	private MedicineSaleManager medicineSaleManager;
	BorderPane b_pane;
	Sale sale;
	String prevPage;
	private ObservableList<MedicineSale> billList = FXCollections.observableArrayList();
	
	
	public Sale getSale() {
		return sale;
	}

	public void setSale(Sale sale) {
		this.sale = sale;
	}

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
	    private TableView<MedicineSale> Bill_tb;

	    @FXML
	    private TextField Sale_Date_time;

	    @FXML
	    private TextField Sale_id;

	    @FXML
	    private Button back_btn;

	    @FXML
	    private TableColumn<MedicineSale,String> bill_id_t;

	    @FXML
	    private TableColumn<MedicineSale,String> bill_name_t;

	    @FXML
	    private TableColumn<MedicineSale,Float> bill_price_t;

	    @FXML
	    private TableColumn<MedicineSale,Integer> bill_quant_t;

	    @FXML
	    private Button print_btn;

	    @FXML
	    private TextField total_amount;

	    @FXML
	    void back(MouseEvent event) throws IOException, ClassNotFoundException, SQLException 
	    {
	    	Parent root = null;
	        FXMLLoader loader = new FXMLLoader(getClass().getResource(this.prevPage + ".fxml"));
	        root = loader.load();
	        ControllerIF controller = loader.getController();
	        controller.setMedicineSaleManager(medicineSaleManager);
	        controller.setB_pane(b_pane);
	        controller.setUser(user);
	        controller.loadDetails();
	        b_pane.setCenter(root);
	    }

	    @FXML
	    void print(MouseEvent event) 
	    {
	    	this.showAlert("Operation Successful", "Printed");
	    }
    
    public void initialize ()
    {
    	bill_id_t.setCellValueFactory(cellData -> 
        new javafx.beans.property.SimpleStringProperty(cellData.getValue().getMedicine().getId()));
		bill_name_t.setCellValueFactory(cellData -> 
        new javafx.beans.property.SimpleStringProperty(cellData.getValue().getMedicine().getName()));
		bill_quant_t.setCellValueFactory(cellData -> 
        new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());
		bill_price_t.setCellValueFactory(cellData -> 
        new javafx.beans.property.SimpleFloatProperty(cellData.getValue().getSubTotal()).asObject());		
    }

	public String getPrevPage() 
	{
		return prevPage;
	}

	public void setPrevPage(String prevPage) {
		this.prevPage = prevPage;
	}

	@Override
	public void loadDetails() 
	{
		this.billList.clear();
		billList.addAll(this.sale.getMedicineSales()); 
        this.Bill_tb.setItems(billList); 
        this.Sale_id.setText(Integer.toString( this.sale.getSaleNumber()));
        this.Sale_Date_time.setText(this.sale.getDateTime());
        this.total_amount.setText(Float.toString( this.sale.getTotal()));
	}

	@Override
	public void setB_pane(BorderPane p) 
	{
		// TODO Auto-generated method stub
		this.b_pane=p;
	}

}
