package Logic;

public class Order {
    //Quantity, Brand, Model, Acoustic, Type, Price
    String ID;
    String quantity;
    String brand;
    String model;
    String acoustic;
    String type;
    String price;
    String customer_FK;

    public Order(String ID, String quantity, String brand, String model, String acoustic, String type, String price, String customer_FK) {
        this.ID = ID;
        this.quantity = quantity;
        this.brand = brand;
        this.model = model;
        this.acoustic = acoustic;
        this.type = type;
        this.price = price;
        this.customer_FK = customer_FK;
    }

    public String getCustomer_FK() {
        return customer_FK;
    }

    public void setCustomer_FK(String customer_FK) {
        this.customer_FK = customer_FK;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getAcoustic() {
        return acoustic;
    }

    public void setAcoustic(String acoustic) {
        this.acoustic = acoustic;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
