package hospital;
import java.util.ArrayList;
public class Schedule
{
	private ArrayList<String> days;
	public Schedule()
	{
		days = new ArrayList<>();
	}
	public Schedule(ArrayList<String>d)
	{
		days = d;
	}
	public boolean addDay(String dayToAdd)
	{
		for(String day : days)
		{
			if(day.equalsIgnoreCase(dayToAdd))
				return false;
		}
		
		days.add(dayToAdd);
		return true;
	}
	
	//Getter Setters
	public ArrayList<String> getDays() {
		return days;
	}
	public void setDays(ArrayList<String> days) {
		this.days = days;
	}
	
}