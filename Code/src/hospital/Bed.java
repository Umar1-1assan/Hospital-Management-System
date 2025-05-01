package hospital;

public class Bed
{
	private int bedNumber;
	private float pricePerDay;
	private boolean available;
	
	public Bed()
	{
		bedNumber=-1;
		pricePerDay=0;
		available=true;
	}
	public Bed(int bNo, float price, boolean avail)
	{
		bedNumber = bNo;
		pricePerDay = price;
		available = avail;
	}
	public int getBedNumber() {
		return bedNumber;
	}
	public void setBedNumber(int bedNumber) {
		this.bedNumber = bedNumber;
	}
	public float getPricePerDay() {
		return pricePerDay;
	}
	public void setPricePerDay(float pricePerDay) {
		this.pricePerDay = pricePerDay;
	}
	public boolean isAvailable() {
		return available;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}
	
	
}