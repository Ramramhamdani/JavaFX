package Logic;

public class OrderItem {
    String uuid;
    String brand;
    String model;
    String acoustic;
    String type;
    String price;
    String quantity;

    public OrderItem(String uuid, String brand, String model, String acoustic, String type, String price, String quantity) {
        this.uuid = uuid;
        this.brand = brand;
        this.model = model;
        this.acoustic = acoustic;
        this.type = type;
        this.price = price;
        this.quantity = quantity;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
