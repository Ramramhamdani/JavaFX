package sample;

import javafx.application.Application;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import Logic.Login;


import java.util.ArrayList;

public class Main extends Application {

    Label userLabel, passwordLabel, visiblePassword;
    TextField userInput;
    PasswordField passwordField;
    Button loginButton;
    Controller controller = new Controller();

    @Override
    public void start(Stage login) throws Exception{
        login.setHeight(200);
        login.setWidth(350);
        login.setTitle("Login screen");

        VBox container = new VBox();
        userLabel = new Label("Username");
        passwordLabel = new Label("Password");
        visiblePassword = new Label();
        visiblePassword.setPadding(new Insets(10));

        userInput = new TextField();
        userInput.setPromptText("Username");

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        // live updated label for the password (addlistener: live update)
        StringProperty passwordFieldProperty = passwordField.textProperty();
        visiblePassword.textProperty().bind(passwordFieldProperty);
        passwordFieldProperty.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                loginButton.setVisible(isValidPassword(newValue));
            }
        });

        loginButton = new Button("Login");
        loginButton.setVisible(false);
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //logic
                ArrayList<Login> logins = controller.GetLoginService();
                for (Login entry :
                        logins) {
                    String tempName = entry.getUsername();
                    String tempPassword = entry.getPassword();
                    if (((userInput.textProperty()).toString() == tempName) && (passwordField.textProperty()).toString() == tempPassword)
                    {
                        login.close();
                        new Content(entry);
                    }
                    else
                    {
                        //login.close();
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Alert");
                        alert.setHeaderText(null);
                        alert.setContentText("Incorrect username or password");
                        alert.showAndWait();
                    }
                }
            }
        });

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(10);
        gridPane.setHgap(8);

        GridPane.setConstraints(userLabel, 0,0);
        GridPane.setConstraints(passwordLabel,0,1);
        GridPane.setConstraints(userInput,1,0);
        GridPane.setConstraints(passwordField,1,1);
        GridPane.setConstraints(loginButton,0,2);

        gridPane.getChildren().addAll(userLabel,passwordLabel,userInput,passwordField,loginButton);
        container.getChildren().addAll(gridPane,visiblePassword);

        Scene scene = new Scene(container);
        login.setScene(scene);
        login.show();
    }

    private Boolean isValidPassword(String newValue)
    {
        int letters = 0;
        int digits = 0;
        int others = 0;

        for (char c : newValue.toCharArray())
        {
            if (c > 48 && c <58)
            {
                digits += 1;
            }
            else if ((c > 64 && c < 91) || (c > 96 && c < 123))
            {
                letters += 1;
            }
            else
            {
                others += 1;
            }
        }
        return newValue.length() > 7 && (letters > 0 && digits > 0 && others > 0);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
