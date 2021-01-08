package sample;

import DAL.OrderDAO;
import Logic.Customer;
import Logic.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import Logic.Login;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Content extends Stage {
    Label welcome, dateTime, role;
    HBox hBox, menu;
    VBox homeContainer;
    MenuBar menuBar;
    Menu home, sales, stock;
    MenuItem orderItem;
    String userType;


    //region order variables
    Label orderNr, customer, articles, firstName, lastName, streetAddress, city, phNumber, email;
    Label reFirstName, reLastName, reAddress, reCity, rePhoneNumber, reEmail;
    Button add, delete, confirm, reset, search;
    TableView orderView;
    TextField searchbar;
    //endregion
    //region OrderPage Boxes
    VBox mainContainer, searchBox,thirdContainer, infoBox1, infoBox2, infoBox3, infoBox4, listBox;
    HBox title, secondContainer, searchBarNBtn, buttons;
    //endregion

    public Content(Login login) {
        this.setHeight(440);
        this.setWidth(500);
        //this.show();
        this.setTitle("Guitarshop - Dashboard");

        showHomeComponents(login);

        home.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // change the content page to show home
                showHomeComponents(login);
            }
        });
        orderItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // go to sales page
                orderPanel(null, login);
                initializeComponents(mainContainer);

                search.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        close();
                        new Customers(searchbar.getText(),login);
                    }
                });
            }
        });
    }
    public void fillForm(Customer customer)
    {
        infoBox1 = new VBox();
        infoBox2 = new VBox();
        infoBox3 = new VBox();
        infoBox4 = new VBox();
        if (customer != null) {
            //firstName, lastName, streetAddress, city, phNumber, email;
            reFirstName = new Label("First name:            ");
            firstName = new Label(customer.getFirstName());
            reAddress = new Label("Street address:          ");
            streetAddress = new Label(customer.getAddress());
            rePhoneNumber = new Label("Phone number:            ");
            phNumber = new Label(customer.getPhone());
            reLastName = new Label("Last name:          ");
            lastName = new Label(customer.getLastName());
            reCity = new Label("City:           ");
            city = new Label(customer.getCity());
            reEmail = new Label("Email address:         ");
            email = new Label(customer.getEmail());
            infoBox1.getChildren().addAll(reFirstName, reAddress, rePhoneNumber);
            infoBox2.getChildren().addAll(reLastName, reCity, reEmail);
            infoBox3.getChildren().addAll(firstName, streetAddress, phNumber);
            infoBox4.getChildren().addAll(lastName, city, email);
        }
        else
        {
            reFirstName = new Label("First name:            " );
            reAddress = new Label("Street address:          " );
            rePhoneNumber = new Label("Phone number:            ");
            reLastName = new Label("Last name:          ");
            reCity = new Label("City:           ");
            reEmail = new Label("Email address:         ");
            infoBox1.getChildren().addAll(reFirstName, reAddress, rePhoneNumber);
            infoBox2.getChildren().addAll(reLastName, reCity, reEmail);
        }

    }
    public void showHomeComponents(Login login)
    {
        homeContainer = new VBox();
        userType = login.getRole();

        createMenuBar();

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());

        hBox = new HBox();
        welcome = new Label("Welcome " + login.getFullName());
        role = new Label("Your role is: " + login.getRole());
        dateTime = new Label("Today is: " + format.format(date));

        //region auth
        userPrivilege(login);
        //end region
        homeContainer.getChildren().addAll(menuBar, welcome, dateTime, role);
        initializeComponents(homeContainer);
    }
    public void initializeComponents(VBox vBox)
    {
        Scene scene = new Scene(vBox);
        this.setScene(scene);
        this.show();
    }

    public Content(Login login, Customer customer) {
        //new Content(login);
        createMenuBar();
        fillForm(customer);
        orderPanel(customer, login);
        initializeComponents(mainContainer);
        
    }
    public void orderPanel(Customer customerObj, Login login)
    {
        fillOrderList();
        fillForm(customerObj);
        userPrivilege(login);
        mainContainer = new VBox();
        secondContainer = new HBox();
        thirdContainer = new VBox();
        searchBox = new VBox();
        articles = new Label("Articles");
        buttons = new HBox();
        customer = new Label("Customer");
        searchBarNBtn = new HBox();
        searchbar = new TextField();
        searchbar.setPromptText("Customer's name");
        search = new Button("Search");
        mainContainer.getChildren().addAll(menuBar, orderNr/*, title*/, secondContainer, thirdContainer);
        secondContainer.getChildren().addAll(searchBox, infoBox1,infoBox3, infoBox2, infoBox4);
        thirdContainer.getChildren().addAll(articles, orderView, buttons);
        searchBox.getChildren().addAll(customer, searchBarNBtn);
        searchBarNBtn.getChildren().addAll(searchbar, search);
    }
    public void fillOrderList()
    {
        ArrayList names = new ArrayList();
        OrderDAO orderDAO = new OrderDAO();
        ObservableList<Order> orders = FXCollections.observableArrayList(orderDAO.getOrders());

        orderView = new TableView();
        TableColumn<Order, String> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn<Order, String> brandColumn = new TableColumn<>("Brand");
        brandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));

        TableColumn<Order, String> modelColumn = new TableColumn<>("Model");
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));

        TableColumn<Order, String> acousticColumn = new TableColumn<>("Acoustic");
        acousticColumn.setCellValueFactory(new PropertyValueFactory<>("acoustic"));

        TableColumn<Order, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<Order, String> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        orderView.getColumns().addAll(quantityColumn, brandColumn, modelColumn, acousticColumn, typeColumn, priceColumn);
        orderView.setItems(orders);

        //region OrderPage Labels
        orderNr = new Label("Create order #" + (orders.size()+1));

        //endregion
    }
    public void createMenuBar()
    {
        menu = new HBox();
        home = new Menu("Home");
        sales = new Menu("Sales");
        stock = new Menu("Stock");
        orderItem = new MenuItem("Order");
        sales.getItems().addAll(orderItem);
        menuBar = new MenuBar();
        menuBar.getMenus().addAll(home, sales, stock);
    }
    public void userPrivilege(Login login)
    {
        if (login.getRole().equals("manager"))
        {
            sales.setVisible(false);
            stock.setVisible(true);
            home.setVisible(true);
        }
        else
        {
            stock.setVisible(false);
            sales.setVisible(true);
            home.setVisible(true);
        }
    }
}

