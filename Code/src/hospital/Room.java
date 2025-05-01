package hospital;
import java.util.ArrayList;
public class Room
{
	private int roomNumber;
	ArrayList<Bed> beds;
	
	public Room()
	{
		roomNumber = -1;
		beds = new ArrayList<>();
	}
	public Room(int rNo, ArrayList<Bed> b)
	{
		this.roomNumber = rNo;
		this.beds = b;
	}
	
	public ArrayList<Bed> getAvailableBeds()
	{
		ArrayList<Bed> availBeds = new ArrayList<>();
		for(Bed b : beds)
		{
			if(b.isAvailable())
			{
				availBeds.add(b);
			}
		}
		return availBeds;
	}
	public float getBedPrice(int bNo)
	{
		float price=0;
		for(Bed b: beds)
		{
			if(b.getBedNumber() == bNo)
			{
				price = b.getPricePerDay();
				break;
			}
		}
		return price;
	}
	public boolean setBedAvailable(int bedNo, boolean b)
	{
		for(Bed bed: beds)
		{
			if(bed.getBedNumber() == bedNo)
			{
				bed.setAvailable(b);
				return true;
			}
		}
		return false;
	}
	public boolean getBedAvailable(int bedNo)
	{
		for(Bed b: beds)
		{
			if(b.getBedNumber()==bedNo)
				return b.isAvailable();
		}
		return false;
	}
	
	public void addBed(Bed b)
	{
		this.beds.add(b);
	}
	public void removeBed(Bed b)
	{
		this.beds.remove(b);
	}
	
	//Getter Setters
	public int getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}
	public ArrayList<Bed> getBeds() {
		return beds;
	}
	public void setBeds(ArrayList<Bed> beds) {
		this.beds = beds;
	}

	
	
}