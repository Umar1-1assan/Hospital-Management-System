package hospital;

public class Medicine 
{
    private String name;
    private String id;
    private int quantity;
    private float price;


    public Medicine() 
    {
        this.name = "";
        this.id = "";
        this.quantity = 0;
        this.price = 0;
    }
    public Medicine(String name, String id, int quantity, float price) {
        this.name = name;
        this.id = id;
        this.quantity = quantity;
        this.price = price;
    }
    
    //Getter Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
