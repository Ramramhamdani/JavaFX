package sample;

import DAL.OrderDAO;
import DAL.OrderitemDAO;
import Logic.Customer;
import Logic.Login;
import Logic.Order;
import Logic.OrderItem;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

import java.util.ArrayList;


public class OrderList extends Stage {
    VBox orderListContainer;
    Label orderList, details;
    TableView orderView, detailView;
    HBox menu;


    MenuBar menuBar;
    Menu home, sales;
    MenuItem createOrder, orders, stock, dashboard;

    public OrderList(Login login) {
        this.setTitle("Order list");
        this.setHeight(440);
        this.setWidth(500);
        initializePageComponents(login);

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
                ArrayList<OrderItem> orderItems = new ArrayList<>();
                close();
                new Content(login).orderPanel(null,login, orderItems);
            }
        });


    }

    public OrderList(Login login, Customer customer) {
        //this.show();

    }

    public void initializePageComponents(Login login)
    {
        this.orderListContainer = new VBox();
        this.orderList = new Label("Orders");
        this.details = new Label("Details");
        orderView = new TableView();
        detailView = new TableView();

        createMenuBar();
        userPrivilege(login);

        orderListContainer.getChildren().addAll(menuBar, orderList, orderView, details, detailView);

        fillOrderView(orderView);
        Scene scene = new Scene(orderListContainer);
        this.setScene(scene);
        this.show();

        orderView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Order>() {
            @Override
            public void changed(ObservableValue<? extends Order> observableValue, Order order, Order t1) {
                fillDetailsView(detailView, t1);
            }
        });
    }

    public void fillOrderView(TableView tableView)
    {
        tableView = new TableView();
        OrderDAO orderDAO = new OrderDAO();
        ObservableList<Order> orders = FXCollections.observableArrayList(orderDAO.getOrders());

        orderView(tableView, orders);
    }

    static void orderView(TableView tableView, ObservableList<Order> orders) {
        //ID, Date, Customer Name, City, Phone, Email, count, Total
        TableColumn<Order, String> quantityColumn = new TableColumn<>("ID");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));

        TableColumn<Order, String> brandColumn = new TableColumn<>("Date");
        brandColumn.setCellValueFactory(new PropertyValueFactory<>("Date"));

        TableColumn<Order, String> modelColumn = new TableColumn<>("Customer name");
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));

        TableColumn<Order, String> acousticColumn = new TableColumn<>("City");
        acousticColumn.setCellValueFactory(new PropertyValueFactory<>("city"));

        TableColumn<Order, String> typeColumn = new TableColumn<>("Phone");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

        TableColumn<Order, String> priceColumn = new TableColumn<>("Count");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("count"));

        TableColumn<Order, String> totalColumn = new TableColumn<>("Total");
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
        tableView.getColumns().addAll(quantityColumn, brandColumn, modelColumn, acousticColumn, typeColumn, priceColumn);
        tableView.setItems(orders);
    }

    public void fillDetailsView(TableView tableView, Order order)
    {
        tableView = new TableView();
        OrderitemDAO orderitemDAO = new OrderitemDAO();
        ObservableList<OrderItem> orderItems = FXCollections.observableArrayList(orderitemDAO.getOrderItemsOfOrder(order));

        //brand,model,acoustic,type,price,quality

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

        tableView.getColumns().addAll(brandColumn, modelColumn, acousticColumn, typeColumn, priceColumn, qualityColumn);
        tableView.setItems(orderItems);
    }

    public void createMenuBar()
    {
        menu = new HBox();
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
