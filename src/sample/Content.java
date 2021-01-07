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
    MenuBar buttonBar;
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
    VBox mainContainer, searchBox, secondContainer,thirdContainer, infoBox1, infoBox2, listBox;
    HBox title, searchBarNBtn, buttons;
    //endregion

    public Content(Login login) {
        this.setHeight(1080);
        this.setWidth(1080);
        //this.show();
        this.setTitle("Guitarshop - Dashboard");

        userType = login.getRole();

        menu = new HBox();
        home = new Menu("Home");
        sales = new Menu("Sales");
        stock = new Menu("Stock");
        orderItem = new MenuItem("Order");
        sales.getItems().addAll(orderItem);
        buttonBar = new MenuBar();
        buttonBar.getMenus().addAll(home, sales, stock);

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());

        hBox = new HBox();
        welcome = new Label("Welcome " + login.getFullName());
        role = new Label("Your role is: " + login.getRole());
        dateTime = new Label("Today is: " + format.format(date));

        //region auth
        if (userType == "manager")
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
        //end region

        home.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // change the content page to show home
                showHomeComponents(buttonBar, welcome, dateTime, role);
            }
        });
        orderItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // go to sales page
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
                mainContainer = new VBox();
                secondContainer = new VBox();
                thirdContainer = new VBox();
                searchBox = new VBox();
                infoBox1 = new VBox();
                infoBox2 = new VBox();
                articles = new Label("Articles");
                buttons = new HBox();
                customer = new Label("Customer");
                searchBarNBtn = new HBox();
                searchbar = new TextField();
                searchbar.setPromptText("Customer's name");
                search = new Button("Search");
                mainContainer.getChildren().addAll(buttonBar, orderNr/*, title*/, secondContainer, thirdContainer);
                secondContainer.getChildren().addAll(searchBox, infoBox1, infoBox2);
                thirdContainer.getChildren().addAll(articles, orderView, buttons);
                searchBox.getChildren().addAll(customer, searchBarNBtn);
                searchBarNBtn.getChildren().addAll(searchbar, search);

                initializeComponents(mainContainer);

                search.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {

                    }
                });



            }
        });

        //region show components
        showHomeComponents(buttonBar, welcome, dateTime, role);
        //endregion
    }
    public void fillForm(Customer customer)
    {
        reFirstName = new Label(customer.getFirstName());
    }
    public void showHomeComponents(MenuBar menuBar, Label welcome, Label dateTime, Label role)
    {
        homeContainer = new VBox();
        homeContainer.getChildren().addAll(menuBar, welcome, dateTime, role);
        /*
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.getChildren().addAll(welcome, dateTime, role, buttonBar);*/
        //hBox.getChildren().addAll(gridPane);
        initializeComponents(homeContainer);
    }
    public void initializeComponents(VBox vBox)
    {
        Scene scene = new Scene(vBox);
        this.setScene(scene);
        this.show();
    }
}

