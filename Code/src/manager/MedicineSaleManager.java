package manager;
//import hospital.User;
//import hospital.LabTechnicianQueue;
//import hospital.LabQueue;
//import hospital.Patient;
import hospital.*;
import dbhandler.*;

import java.sql.SQLException;
import java.util.ArrayList;
//import dbhandler.PatientDBHandler;

public class MedicineSaleManager
{
	private Sale sale;
	
	public MedicineSaleManager()
	{
		sale=null;
	}
	public void createNewSale(String datetime) throws ClassNotFoundException, SQLException
	{
		sale = new Sale();
		int sNo = SaleDBHandler.getSaleDBHandler().getMaxSaleNumber();
		sale.setSaleNumber(sNo+1);
		sale.setDateTime(datetime);
	}
	public ArrayList<Medicine>getMedicines() throws ClassNotFoundException, SQLException
	{
		return MedicineDBHandler.getMedicineDBHandler().getMedicines();
	}
	public ArrayList<Medicine> getMedicines(String name) throws ClassNotFoundException, SQLException
	{
		return MedicineDBHandler.getMedicineDBHandler().getMedicineViaName(name);
	}
	
	public boolean addMedicine(String medId, int qty) throws ClassNotFoundException, SQLException
	{
		Medicine m = MedicineDBHandler.getMedicineDBHandler().getMedicine(medId);
		if(this.sale.enterMedicine(m, qty))
		{
			MedicineDBHandler.getMedicineDBHandler().saveMedicine(m);
			return true;
		}
		else return false;
	}
	
	public boolean removeMedicine(String medId) throws ClassNotFoundException, SQLException
	{
		Medicine m = MedicineDBHandler.getMedicineDBHandler().getMedicine(medId);
		if(this.sale.removeMedicine(m))
		{
			MedicineDBHandler.getMedicineDBHandler().saveMedicine(m);
			return true;
		}
		else return false;
	}
	
	public boolean addUpdateMedicineInStock(Medicine m) throws ClassNotFoundException, SQLException
	{
		return MedicineDBHandler.getMedicineDBHandler().saveMedicine(m);
	}
	
	public ArrayList<MedicineSale> getAddedMedicine()
	{
		return this.sale.getMedicineSales();
	}
	
	public float getTotal()
	{
		return this.sale.getTotal();
	}
	
	public boolean endSale() throws ClassNotFoundException, SQLException
	{
		sale.setComplete(true);
		SaleDBHandler.getSaleDBHandler().saveSale(sale);
		this.sale=null;
		return true;
	}
	
	public boolean cancelSale() throws ClassNotFoundException, SQLException
	{
		if(this.sale==null)
			return true;
		
		for(MedicineSale s: this.sale.getMedicineSales())
    	{
			int Quant = s.getMedicine().getQuantity();
			s.getMedicine().setQuantity( Quant + s.getQuantity());
    		MedicineDBHandler.getMedicineDBHandler().saveMedicine(s.getMedicine());
    	}
		this.sale=null;
    	return true;
	}
	
	public ArrayList<Sale> getComepletedSales() throws ClassNotFoundException, SQLException
	{
		return SaleDBHandler.getSaleDBHandler().getSales();
	}
	
	public Sale getSale(int id) throws ClassNotFoundException, SQLException
	{
		return SaleDBHandler.getSaleDBHandler().getSale(id);
	}
	
	public Sale getSale() {
		return sale;
	}
	public void setSale(Sale sale) {
		this.sale = sale;
	}
	public Pharmacist showProfile(String username) throws ClassNotFoundException, SQLException
	{
		return PharmacistDBHandler.getPharmacistDBHandler().getPharmacist(username);
	}
}