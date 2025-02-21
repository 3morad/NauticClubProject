package entities;

public class Equipment {
    private String name;
    private String type;
    private boolean available;
    private int price;
    private int Id;

    public Equipment(int id) {
        Id = id;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public Equipment(int id, String name, boolean available, String type, int price) {
        this.name = name;
        this.available= available;
        this.type = type;
        this.price = price;
    }



    public Equipment(int id, String name, String type, boolean availability, double price) {
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Equipment{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", available=" + available +
                ", price=" + price +
                '}';
    }

}
