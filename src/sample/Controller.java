package sample;

import DAL.LoginDAO;
import Logic.Login;

import java.util.ArrayList;

public class Controller {
    LoginDAO loginDAO = new LoginDAO();
    public ArrayList<Login> GetLoginService(){
        ArrayList<Login> logins = new ArrayList<>();
        logins = loginDAO.getLogins();
        return logins;
    }


}
