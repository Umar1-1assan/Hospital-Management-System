package hospital;
import java.util.ArrayList;

public class Sale 
{
	private int saleNumber;
    private boolean complete;
    private String dateTime;
    private ArrayList<MedicineSale> medicineSales;
    private float total;



	public Sale() 
    {
        this.complete = false;
        this.dateTime = "";
        this.medicineSales = new ArrayList<>();
    }

    public Sale(int number, boolean complete, String dateTime, ArrayList<MedicineSale> medicineSales) 
    {
    	this.saleNumber=number;
        this.complete = complete;
        this.dateTime = dateTime;
        this.medicineSales = medicineSales;
    }
    

    public boolean enterMedicine(Medicine medicine, int quantity) 
    {
    	if(medicine.getQuantity() < quantity)
    	{
    		return false;
    	}
    	medicine.setQuantity( medicine.getQuantity() - quantity );
    	
    	//Checking if already present
    	boolean found=false;
    	for(MedicineSale m : this.medicineSales)
    	{
    		if(m.getMedicine().getId().equals(medicine.getId()))
    		{
    			int quant = m.getQuantity();
    			m.setQuantity(quantity + quant);
    			found=true;
    			break;
    		}
    	}
    	if(!found)
    		this.medicineSales.add(new MedicineSale(quantity, medicine));
    	
    	//Calculating total
    	float total=0;
        for(MedicineSale s: this.medicineSales)
        {
        	total+=s.getSubTotal();
        }
        this.total=total;
    	return true;
    }
    
    public boolean removeMedicine(Medicine medicine)
    {
    	MedicineSale medSale=null;;
    	for(MedicineSale m : this.medicineSales )
    	{
    		if(m.getMedicine().getId().equals(medicine.getId()))
    		{
    			medSale=m;
    			break;
    		}
    	}
    	
    	if(medSale==null)
    		return false;
    	int quant = medicine.getQuantity();
    	medicine.setQuantity(quant + medSale.getQuantity());
    	this.medicineSales.remove(medSale);
    	
    	//Calculating total
    	float total=0;
        for(MedicineSale s: this.medicineSales)
        {
        	total+=s.getSubTotal();
        }
        this.total=total;
    	return true;
    }
   

    public float getTotal() 
    {
        return total;
    }
    
    public void cancelSale()
    {
    	for(MedicineSale s: this.medicineSales)
    	{
    		int quant = s.getMedicine().getQuantity();
    		s.getMedicine().setQuantity(quant + s.getQuantity());
    	}
    }
    
    //Getter Setters

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public String getDateTime() {
        return dateTime;
    }

    public int getSaleNumber() {
		return saleNumber;
	}

	public void setSaleNumber(int saleNumber) {
		this.saleNumber = saleNumber;
	}

	public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public ArrayList<MedicineSale> getMedicineSales() {
        return medicineSales;
    }

    public void setMedicineSales(ArrayList<MedicineSale> medicineSales) {
        this.medicineSales = medicineSales;
    }
    public void setTotal(float total) {
		this.total = total;
	}



    public void markCompleted() {
        this.complete = true;
    }
}
