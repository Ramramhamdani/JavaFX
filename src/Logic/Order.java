package Logic;

public class Order {
    //ID, Date, Customer Name, City, Phone, Email, count, Total
    String ID;
    String date;
    String customerName;
    String city;
    String phone;
    String email;
    String count;
    String total;

    public Order(String ID, String date, String customerName, String city, String phone, String email, String count, String total) {
        this.ID = ID;
        this.date = date;
        this.customerName = customerName;
        this.city = city;
        this.phone = phone;
        this.email = email;
        this.count = count;
        this.total = total;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
