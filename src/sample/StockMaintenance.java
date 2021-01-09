package sample;

import DAL.ArticlesDAO;
import DAL.OrderDAO;
import Logic.Article;
import Logic.Customer;
import Logic.Login;
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

public class StockMaintenance extends Stage {
    VBox mainContainer;
    HBox secondContainer;
    Label title;
    TableView stock;
    TextField quantity;
    RadioButton negate;
    Button add;

    MenuBar menuBar;
    Menu home, sales;
    MenuItem createOrder, orders, stockManagement, dashboard;

    public StockMaintenance(Login login) {
        mainContainer = new VBox();
        secondContainer = new HBox();
        title = new Label("Stock maintenance");
        quantity = new TextField();
        quantity.setPromptText("Quantity");
        negate = new RadioButton("Negate");
        add = new Button("Add");
        add.setVisible(true);
        menuBar = new MenuBar();
        createMenuBar();
        ArticlesDAO articlesDAO = new ArticlesDAO();


        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                stock.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Article>() {
                    @Override
                    public void changed(ObservableValue observableValue, Article o, Article t1) {
                        if (negate.isSelected())
                        {
                            String toEdit = t1.getBrand()+","+t1.getModel()+","+t1.getAcoustic()+","+t1.getType()+","+t1.getPrice()+","+t1.getStock();
                            String orderItemEdited = t1.getBrand()+","+t1.getModel()+","+t1.getAcoustic()+","+t1.getType()+","+t1.getPrice()+","+quantity.getText();
                            articlesDAO.editStock(new Article(t1.getBrand(),t1.getModel(),t1.getAcoustic(),t1.getType(),t1.getPrice(),quantity.getText()));
                        }
                    }
                });
            }
        });

        ObservableList<Article> articles = FXCollections.observableArrayList(articlesDAO.getArticles());
        //brand,model,acoustic,type,price,stock
        stock = new TableView();
        TableColumn<Article, String> brandColumn = new TableColumn<>("Brand");
        brandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));

        TableColumn<Article, String> modelColumn = new TableColumn<>("Model");
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));

        TableColumn<Article, String> acousticColumn = new TableColumn<>("Acoustic");
        acousticColumn.setCellValueFactory(new PropertyValueFactory<>("acoustic"));

        TableColumn<Article, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<Article, String> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Article, String> stockColumn = new TableColumn<>("Quantity");
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));

        stock.getColumns().addAll(stockColumn, brandColumn, modelColumn, acousticColumn, typeColumn, priceColumn);
        stock.setItems(articles);

        mainContainer.getChildren().addAll(menuBar, title, stock, secondContainer);
        secondContainer.getChildren().addAll(quantity, negate, add);



        userPrivilege(login);

        quantity.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    quantity.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        dashboard.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // change the content page to show home
                close();
                new Content(login);
                //showHomeComponents(login);
            }
        });
        createOrder.setVisible(false);
        orders.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                close();
                new OrderList(login);

            }
        });


        Scene scene = new Scene(mainContainer);
        this.setScene(scene);
        this.show();
    }
    public void createMenuBar()
    {
        home = new Menu("Home");
        sales = new Menu("Sales");
        stockManagement = new MenuItem("Stock");
        orders = new MenuItem("View orders");
        createOrder = new MenuItem("Order");
        dashboard = new MenuItem("Dashboard");
        sales.getItems().addAll(createOrder, stockManagement, orders);
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
