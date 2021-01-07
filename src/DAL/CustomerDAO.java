package DAL;

import Logic.Customer;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class CustomerDAO {
    ArrayList<Customer> customers;

    public ArrayList<Customer> getCustomers() {
        customers = new ArrayList<>();
        Path path = Paths.get("src/Database/customer.csv");
        File src = new File(String.valueOf(path));
        int count = 0;
        try {
            Scanner scanner = new Scanner(src);
            while (scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                String[] columns = line.split(",");
                //ID, First name, Last name, City, Address, Phone number, Email
                Customer customer = new Customer(columns[0],columns[1],columns[2],columns[3],columns[4],columns[5],columns[6]);
                if (count != 0) {
                    customers.add(customer);
                }
                count++;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return customers;
    }
}
