package DAL;

import Logic.Order;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class OderDAO {
    ArrayList<Order> orders;

    public ArrayList<Order> GetOrders()
    {
        this.orders = new ArrayList<>();
        Path path = Paths.get("src/Database/order.csv");
        File src = new File(String.valueOf(path));
        try {
            Scanner scanner = new Scanner(src);
            while (scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                String[] columns = line.split(",");
                Order order = new Order(columns[0], columns[1], columns[2], columns[3], columns[4], columns[5], columns[6]);
                orders.add(order);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return orders;
    }
}
