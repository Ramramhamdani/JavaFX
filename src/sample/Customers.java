package sample;

import DAL.CustomerDAO;
import Logic.Customer;
import Logic.Login;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.Content;

import java.util.ArrayList;

public class Customers extends Stage {
    TableView customerView;

    public Customers(String name, Login login) {
        this.setHeight(440);
        this.setWidth(440);
        this.show();
        this.setTitle("Customers");

        CustomerDAO customerDAO = new CustomerDAO();
        ArrayList<Customer> customers = customerDAO.getCustomers();
        ArrayList<Customer> foundCustomers = new ArrayList<>();

        for (Customer customer: customers)
        {
            String customerName = customer.getFirstName();
            if (customerName == name)
            {
                foundCustomers.add(customer);
            }
        }


        customerView = new TableView();
        TableColumn<Customer, String> firstNameColumn = new TableColumn<>("First Name");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<Customer, String> lastName = new TableColumn<>("Last Name");
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn<Customer, String> streetColumn = new TableColumn<>("Street Address");
        streetColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        TableColumn<Customer, String> cityColumn = new TableColumn<>("City");
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));

        TableColumn<Customer, String> phoneColumn = new TableColumn<>("Phone");
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

        TableColumn<Customer, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("Email"));

        customerView.getColumns().addAll(firstNameColumn, lastName, streetColumn, cityColumn, phoneColumn, emailColumn);
        customerView.setPlaceholder(new Label("no rows to display"));



        HBox container = new HBox();
        Scene scene = new Scene(container);
        this.setScene(scene);
        this.show();

        customerView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {

            }
        });

    }

    public Customers(StageStyle stageStyle) {
        super(stageStyle);
    }
}
