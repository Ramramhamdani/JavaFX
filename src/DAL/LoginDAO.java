package DAL;

import Logic.Login;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class LoginDAO {
    ArrayList<Login> logins;

    public ArrayList<Login> getLogins() {
        this.logins = new ArrayList<>();
        Path path = Paths.get("src/Database/login.csv");
        File src = new File(String.valueOf(path));
        int count = 0;
        try {
            Scanner dbScanner = new Scanner(src);
            while (dbScanner.hasNextLine()) {

                String line = dbScanner.nextLine();
                String[] lines = line.split(",");
                Login login = new Login(lines[0], lines[1], lines[2], lines[3], lines[4]);
                if (count != 0)
                {
                    logins.add(login);
                }
                count++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return logins;
    }
}
