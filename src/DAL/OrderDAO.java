package DAL;

import Logic.Order;

import java.io.File;
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
}
