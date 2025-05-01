package hospital;

public class MedicineSale 
{
    private int quantity;
    private Medicine medicine;
    

    public MedicineSale() 
    {
    	this.quantity=0;
    	this.medicine=new Medicine();
    }

    public MedicineSale(int quantity, Medicine medicine) 
    {
        this.quantity = quantity;
        this.medicine = medicine;
    }

    public float getSubTotal()
    {
    	return this.medicine.getPrice() * this.quantity;
    }
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

}
