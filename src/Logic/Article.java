package Logic;

public class Article {
    String brand;
    String model;
    String acoustic;
    String type;
    String price;
    String stock;

    public Article(String brand, String model, String acoustic, String type, String price, String stock) {
        this.brand = brand;
        this.model = model;
        this.acoustic = acoustic;
        this.type = type;
        this.price = price;
        this.stock = stock;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
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
