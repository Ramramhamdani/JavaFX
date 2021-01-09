package sample;

import DAL.ArticlesDAO;
import DAL.OrderDAO;
import Logic.*;
import javafx.beans.property.StringProperty;
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

import java.util.ArrayList;

public class AddArticle extends Stage {
    VBox mainContainer;
    HBox secondContainer;
    TableView articlesView;
    Button add, cancel, done;
    TextField quantityFiled;
    Label alertLbl;
    ArrayList<Order> orders;
    ArrayList<OrderItem> orderItems;
    Article temp;
    public AddArticle(Login login, Customer customer) {
        this.setHeight(440);
        this.setWidth(500);
        this.show();
        this.setTitle("Add article");
        quantityFiled = new TextField();
        quantityFiled.setPromptText("Quantity");
        alertLbl = new Label();
        add = new Button("Add");
        add.setVisible(true);
        cancel = new Button("Cancel");
        done = new Button("Done");
        orderItems = new ArrayList<>();
        mainContainer = new VBox();

        articlesView = new TableView();
        fillTable(articlesView);

        secondContainer = new HBox();
        secondContainer.setPadding(new Insets(10));
        quantityFiled.setPadding(new Insets(20));
        add.setPadding(new Insets(10));
        done.setPadding(new Insets(10));
        cancel.setPadding(new Insets(10));
        secondContainer.getChildren().addAll(quantityFiled, add, done, cancel);
        mainContainer = new VBox();
        mainContainer.setPadding(new Insets(10));
        mainContainer.getChildren().addAll(articlesView, secondContainer, alertLbl);

        StringProperty alertLabelProperty = quantityFiled.textProperty();

        alertLabelProperty.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s1) {
                add.setVisible(checkStock(temp,s1));
            }
        });
        articlesView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Article>() {
            @Override
            public void changed(ObservableValue observableValue, Article o, Article t1) {
                temp = t1;
            }
        });
        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                
                        alertLbl = new Label("Not enough in stock for " +temp.getBrand() + " " + temp.getModel() + ". Only" + temp.getStock()+ " remaining");
                        alertLbl.setVisible(checkStock(temp, quantityFiled.getText()));


                orders = new ArrayList<>();
                OrderDAO orderDAO = new OrderDAO();
                int id = orderDAO.getLastId() + 1;
                Order order = new Order(String.valueOf(id),quantityFiled.getText(), temp.getBrand(), temp.getModel(), temp.getAcoustic(), temp.getType(), temp.getPrice(), customer.getID());
                //region Set and delete from db
                int inStock = Integer.parseInt(temp.getStock());
                int ordered = Integer.parseInt(quantityFiled.getText());
                int stockLeft = inStock - ordered;
                String orderItemEdit = temp.getBrand()+","+temp.getModel()+","+temp.getAcoustic()+","+temp.getType()+","+temp.getPrice()+","+String.valueOf(stockLeft);
                String toEdit = temp.getBrand()+","+temp.getModel()+","+temp.getAcoustic()+","+temp.getType()+","+temp.getPrice()+","+temp.getStock();
                ArticlesDAO articlesDAO = new ArticlesDAO();
                articlesDAO.editStock(new Article(temp.getBrand(),temp.getModel(),temp.getAcoustic(),temp.getType(),temp.getPrice(),String.valueOf(stockLeft)));

                // add to a list of orderItems then send to content page
                //uuid,brand,model,acoustic,type,price,quality
                orderItems.add(new OrderItem(String.valueOf(id), temp.getBrand(), temp.getModel(), temp.getAcoustic(), temp.getType(), temp.getPrice(), quantityFiled.getText()));
                //endregion
                alertLbl = new Label("Article is added successfully");
                

            }
        });


        cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                close();
            }
        });

        done.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                close();
                new Content(login, customer, orderItems);
            }
        });


        Scene scene = new Scene(mainContainer);
        this.setScene(scene);
        this.show();

    }

    public void fillTable(TableView tableView)
    {
        ArticlesDAO articlesDAO = new ArticlesDAO();
        ObservableList<Article> articles = FXCollections.observableArrayList(articlesDAO.getArticles());

        //brand,model,acoustic,type,price,stock
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


        tableView.getColumns().addAll(brandColumn, modelColumn, acousticColumn, typeColumn, priceColumn, stockColumn);
        tableView.setPlaceholder(new Label("no article to display!"));
        tableView.setItems(articles);


    }

    public boolean checkStock(Article article, String stock)
    {
        int inStock = Integer.parseInt(article.getStock());
        int ordered = Integer.parseInt(stock);
        if (inStock >= ordered)
        {
            return true;
        }
        return false;
    }
}
