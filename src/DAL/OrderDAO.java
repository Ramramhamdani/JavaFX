package DAL;

import Logic.Order;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class OrderDAO {
    ArrayList<Order> orders;

    public ArrayList<Order> getOrders()
    {
        this.orders = new ArrayList<>();
        Path path = Paths.get("src/Database/order.csv");
        File src = new File(String.valueOf(path));
        int count = 0;
        try {
            Scanner scanner = new Scanner(src);
            while (scanner.hasNextLine())
            {

                String line = scanner.nextLine();
                String[] columns = line.split(",");
                Order order = new Order(columns[0], columns[1], columns[2], columns[3], columns[4], columns[5], columns[6], columns[7]);
                if (count != 0) {
                    orders.add(order);
                }
                count++;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return orders;
    }
    public Integer getLastId()
    {
        orders = getOrders();
        int id = orders.size();
        return id;
    }
    public void setOrders(ArrayList<Order> orderArrayList)
    {
        //ID, Date, Customer Name, City, Phone, Email, count, Total
        Path path = Paths.get("src/Database/order.csv");
        File src = new File(String.valueOf(path));
        try {
            Writer writer = new FileWriter(src);
            for (Order order :
                    orderArrayList) {
                String line = order.getID()+","+order.getDate()+","+order.getCustomerName()+","+order.getCity()+","+order.getPhone()+","+order.getCount()+","+order.getTotal();
                writer.write(line);
            }
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
}
