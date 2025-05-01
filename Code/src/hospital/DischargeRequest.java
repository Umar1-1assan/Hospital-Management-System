package hospital;

public class DischargeRequest 
{
	int requestNumber;
    private String dateTime;
    private BedAllocation bedAllocation;
    String status; //Pending Completed

    
    //Getter Setters
    public DischargeRequest()
    {
    	
    }

	public DischargeRequest(String dateTime, BedAllocation bedAllocation, String status, int reqNo) 
    {
        this.dateTime = dateTime;
        this.bedAllocation = bedAllocation;
        this.status=status;
        this.requestNumber = reqNo;
    }

    public int getRequestNumber() {
		return requestNumber;
	}

	public void setRequestNumber(int requestNumber) {
		this.requestNumber = requestNumber;
	}

	public String getDateTime() 
    {
        return dateTime;
    }

    public void setDateTime(String dateTime) 
    {
        this.dateTime = dateTime;
    }

    public BedAllocation getBedAllocation() 
    {
        return bedAllocation;
    }

    public void setBedAllocation(BedAllocation bedAllocation) {
        this.bedAllocation = bedAllocation;
    }
    public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    


}
