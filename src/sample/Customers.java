package sample;

import DAL.CustomerDAO;
import Logic.Customer;
import Logic.Login;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.List;

public class Customers extends Stage {


    public Customers(String name, Login login) {
        this.setHeight(440);
        this.setWidth(500);
        this.show();
        this.setTitle("Customers");

        HBox container = new HBox();

        CustomerDAO customerDAO = new CustomerDAO();
        ArrayList<Customer> customers = customerDAO.getCustomers();
        ArrayList<Customer> foundCustomers = new ArrayList<>();
        List<Character> characters = new ArrayList<>();

        for (Customer customer: customers)
        {
            String customerName = customer.getFirstName();
            if (customerName.equals(name))
            {
                foundCustomers.add(customer);
            }
            else if (customerName.contains(name)){
                foundCustomers.add(customer);
            }
        }

        ObservableList<Customer> customersList = FXCollections.observableArrayList(foundCustomers);

        TableView customerView = new TableView();
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
        customerView.setItems(customersList);

        container.getChildren().addAll(customerView);


        Scene scene = new Scene(container);
        this.setScene(scene);
        this.show();

        customerView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Customer>() {
            @Override
            public void changed(ObservableValue observableValue, Customer o, Customer t1) {
                fillContent(login, t1);
            }
        });

    }
    public void fillContent(Login login, Customer customer)
    {
        new Content(login, customer);
        close();
    }
}
