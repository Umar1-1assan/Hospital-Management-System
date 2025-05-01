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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import manager.MedicineSaleManager;

public class CompletedSaleController implements ControllerIF
{
	private User user;
	private MedicineSaleManager medicineSaleManager;
	BorderPane b_pane;
	private ObservableList<Sale> billList = FXCollections.observableArrayList();

	
	
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
	private TableView<Sale> Completed_Sales;

	@FXML
	private Button Get_Info_btn;

	@FXML
	private TableColumn<Sale,String> dateTime_t;

	@FXML
	private TableColumn<Sale,Integer> saleNum_t;

	@FXML
	private TableColumn<Sale,Float> total_t;
	
    @FXML
    public void initialize()
    {
    	saleNum_t.setCellValueFactory(new PropertyValueFactory<>("saleNumber"));
    	dateTime_t.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
    	total_t.setCellValueFactory(cellData -> 
        new javafx.beans.property.SimpleFloatProperty(cellData.getValue().getTotal()).asObject());    
    }

    @FXML
    void Get_Info(MouseEvent event) throws IOException, ClassNotFoundException, SQLException 
    {
    	Sale sale = this.Completed_Sales.getSelectionModel().getSelectedItem();
    	if(sale==null)
    	{
    		this.showAlert("Operaiton Unuccessful", "Please select a sale");
    		return;
    	}
    	
    	Sale temp = this.medicineSaleManager.getSale(sale.getSaleNumber());
    	
    	Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ShowSale" + ".fxml"));
        root = loader.load();
        ShowSaleController controller = loader.getController();
        controller.setMedicineSaleManager(medicineSaleManager);
        controller.setB_pane(b_pane);
        controller.setUser(user);
        
        controller.setSale(temp);
        controller.setPrevPage("Completed_Sale");
        controller.loadDetails();
        b_pane.setCenter(root);

    }

	@Override
	public void setB_pane(BorderPane p) 
	{
		this.b_pane=p;
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadDetails() throws ClassNotFoundException, SQLException
	{
		ArrayList<Sale> list = this.medicineSaleManager.getComepletedSales();
		this.billList.clear();
		this.billList.addAll(list);
		this.Completed_Sales.setItems(billList);
	}

}
