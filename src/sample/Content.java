package sample;

import DAL.OrderDAO;
import Logic.Customer;
import Logic.Order;
import Logic.OrderItem;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
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
    Menu home, sales;
    MenuItem createOrder, orders, stock, dashboard;
    String userType;

    Customer customer;
    ArrayList<OrderItem> orderItems;

    //region order variables
    Label orderNr, customerlbl, articles, firstName, lastName, streetAddress, city, phNumber, email;
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
        orderItems = new ArrayList<>();

        showHomeComponents(login);


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
        add = new Button("Add");
        delete = new Button("Delete");
        confirm = new Button("Confirm");
        reset = new Button("Reset");
        search = new Button("Search");

        //region auth
            userPrivilege(login);
        //endregion
        homeContainer.getChildren().addAll(menuBar, welcome, dateTime, role);
        initializeComponents(homeContainer, login, customer);
    }
    public void initializeComponents(VBox vBox, Login login, Customer customer)
    {
        dashboard.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // change the content page to show home
                close();
                new Content(login);
                //showHomeComponents(login);
            }
        });
        createOrder.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // go to sales page
                orderPanel(customer, login, orderItems);
                //initializeComponents(mainContainer);

                search.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        close();
                        new Customers(searchbar.getText(),login);
                    }
                });
            }
        });
        search.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                close();
                new Customers(searchbar.getText(),login);
            }
        });
        stock.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                new StockMaintenance(login);
            }
        });
        orders.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                close();
                new OrderList(login);

            }
        });
        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (customer != null)
                {
                    close();
                    new AddArticle(login, customer);
                }
                else
                {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Alert");
                    alert.setHeaderText(null);
                    alert.setContentText("Select a customer first!");
                    alert.showAndWait();
                }
            }
        });
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                orderView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<OrderItem>() {
                    @Override
                    public void changed(ObservableValue<? extends OrderItem> observableValue, OrderItem orderItem, OrderItem t1) {
                        orderItems.remove(t1);
                    }
                });
            }
        });
        reset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                new Content(login);
            }
        });
        confirm.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if ((customer != null) && (orderItems != null)) {
                    new Confirm(login, customer, orderItems);
                }
                else
                {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Alert");
                    alert.setHeaderText(null);
                    alert.setContentText("Select an order first!");
                    alert.showAndWait();
                }
            }
        });
        this.setTitle("Guitarshop");
        Scene scene = new Scene(vBox);
        this.setScene(scene);
        this.show();
    }

    public Content(Login login, Customer customer, ArrayList<OrderItem> orderItems) {
        //new Content(login);
        this.orderItems = orderItems;
        this.customer = customer;
        createMenuBar();
        fillForm(customer);
        orderPanel(customer, login, orderItems);
        //initializeComponents(mainContainer);

        
    }
    public void orderPanel(Customer customerObj, Login login, ArrayList<OrderItem> orderItems)
    {

        fillOrderList(orderItems);
        fillForm(customerObj);
        userPrivilege(login);
        mainContainer = new VBox();
        mainContainer.setPadding(new Insets(5));
        secondContainer = new HBox();
        thirdContainer = new VBox();
        searchBox = new VBox();
        articles = new Label("Articles");
        buttons = new HBox();
        customerlbl = new Label("Customer");
        searchBarNBtn = new HBox();
        searchbar = new TextField();
        searchbar.setPromptText("Customer's name");
        search = new Button("Search");
        add = new Button("Add");
        delete = new Button("Delete");
        confirm = new Button("Confirm");
        reset = new Button("Reset");
        mainContainer.getChildren().addAll(menuBar, orderNr/*, title*/, secondContainer, thirdContainer);
        secondContainer.getChildren().addAll(searchBox, infoBox1,infoBox3, infoBox2, infoBox4);
        thirdContainer.getChildren().addAll(articles, orderView, buttons);
        searchBox.getChildren().addAll(customerlbl, searchBarNBtn);
        searchBarNBtn.getChildren().addAll(searchbar, search);
        buttons.getChildren().addAll(add, delete, confirm, reset);
        buttons.setPadding(new Insets(40));
        infoBox3.setPadding(new Insets(6));
        infoBox4.setPadding(new Insets(6));
        infoBox2.setPadding(new Insets(6));
        infoBox1.setPadding(new Insets(6));
        initializeComponents(mainContainer, login, customerObj);
    }
    public void fillOrderList(ArrayList<OrderItem> orderItems)
    {

        ObservableList<OrderItem> orders = FXCollections.observableArrayList(orderItems);

        orderView = new TableView();
        TableColumn<OrderItem, String> brandColumn = new TableColumn<>("Brand");
        brandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));

        TableColumn<OrderItem, String> modelColumn = new TableColumn<>("Model");
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));

        TableColumn<OrderItem, String> acousticColumn = new TableColumn<>("Acoustic");
        acousticColumn.setCellValueFactory(new PropertyValueFactory<>("acoustic"));

        TableColumn<OrderItem, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<OrderItem, String> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<OrderItem, String> qualityColumn = new TableColumn<>("Quantity");
        qualityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        orderView.getColumns().addAll(brandColumn, modelColumn, acousticColumn, typeColumn, priceColumn, qualityColumn);
        orderView.setItems(orders);

        //region OrderPage Labels
        orderNr = new Label("Create order #" + (orders.size()+1));

        //endregion
    }
    public void createMenuBar()
    {
        //menu = new HBox();
        home = new Menu("Home");
        sales = new Menu("Sales");
        stock = new MenuItem("Stock");
        orders = new MenuItem("View orders");
        createOrder = new MenuItem("Order");
        dashboard = new MenuItem("Dashboard");
        sales.getItems().addAll(createOrder, stock, orders);
        home.getItems().addAll(dashboard);
        menuBar = new MenuBar();
        menuBar.getMenus().addAll(home, sales);
    }
    public void userPrivilege(Login login)
    {
        if (login.getRole().equals("manager"))
        {
            sales.setVisible(true);
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

