package hospital;
public class RoomBed {
    private int roomNo;
    private int bedNo;
    private String status;
    private float price;

    public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public RoomBed(int roomNo, int bedNo, String status, float price) {
        this.roomNo = roomNo;
        this.bedNo = bedNo;
        this.status = status;
        this.price=price;
    }

    public int getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(int roomNo) {
        this.roomNo = roomNo;
    }

    public int getBedNo() {
        return bedNo;
    }

    public void setBedNo(int bedNo) {
        this.bedNo = bedNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}