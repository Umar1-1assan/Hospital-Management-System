package hospital;

public class Due
{
	private String description;
	private float price;
	private String status; //Paid UnPaid
	private String dateTime;
	private int dueNumber;
	
	public Due()
	{
		this.description="";
		this.price=0;
		this.status="";
		this.dateTime="";
		dueNumber=0;
	}
	public Due(String desc, float price, String status, String dateTime,int dueNumber)
	{
		this.description=desc;
		this.price=price;
		this.status=status;
		this.dateTime=dateTime;
		this.dueNumber = dueNumber;
	}
	
	public int getDueNumber() {
		return dueNumber;
	}
	public void setDueNumber(int dueNumber) {
		this.dueNumber = dueNumber;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}