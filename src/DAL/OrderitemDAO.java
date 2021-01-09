package DAL;

import Logic.Customer;
import Logic.Order;
import Logic.OrderItem;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OrderitemDAO {
    ArrayList<OrderItem> orderItems;

    public ArrayList<OrderItem> getOrderitem()
    {
        orderItems = new ArrayList<>();
        Path path = Paths.get("src/Database/orderItem.csv");
        File src = new File(String.valueOf(path));
        int count = 0;
        try {
            Scanner scanner = new Scanner(src);
            while (scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                String[] columns = line.split(",");
                //ID, First name, Last name, City, Address, Phone number, Email
                OrderItem orderItem = new OrderItem(columns[0],columns[1],columns[2],columns[3],columns[4],columns[5],columns[6]);
                if (count != 0) {
                    orderItems.add(orderItem);
                }
                count++;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return orderItems;
    }
    public ArrayList<OrderItem> getOrderItemsOfOrder(Order order)
    {
        orderItems = new ArrayList<>();
        orderItems = getOrderitem();
        for (OrderItem orderItem :
                orderItems) {
            if (order.getID().equals(orderItem.getUuid()))
            {
                orderItems.add(orderItem);
            }
        }
        return orderItems;
    }

}
