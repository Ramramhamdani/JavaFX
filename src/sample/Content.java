package sample;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import Logic.Login;



public class Content extends Stage {
    Label welcome, dateTime, role;
    HBox hBox;

    public Content(Login login) {
        this.setHeight(1080);
        this.setWidth(1080);
        this.show();
        this.setTitle("Guitarshop - Dashboard");

        hBox = new HBox();
        welcome = new Label("Welcome " + login.getFullName());
        role = new Label("Your role is: " + login.getRole());
        dateTime = new Label("Today is: " + dateTime);


    }
}

