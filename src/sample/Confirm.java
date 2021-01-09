package sample;

import DAL.OrderDAO;
import Logic.Customer;
import Logic.Login;
import Logic.Order;
import Logic.OrderItem;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Confirm extends Stage {

    VBox mainContainer, nameAddressBox, qtyBox, brandBox, modelBox, typeBox, priceBox;
    HBox customerBox, secondContainer;
    Label customerlbl, namelbl, addresslbl, citylbl, numberlbl, emaillbl, qtylbl, brandlbl, modellbl, typelbl, pricelbl;
    Button confirm;

    public Confirm(Login login, Customer customer, ArrayList<OrderItem> orderItems) {
        //ID, Quantity, Brand, Model, Acoustic, Type, Price, Customer_FK

        customerlbl = new Label("Customer");
        namelbl = new Label(customer.getLastName() +" "+ customer.getLastName());
        addresslbl = new Label(customer.getAddress());
        citylbl = new Label(customer.getCity());
        numberlbl = new Label(customer.getPhone());
        emaillbl = new Label(customer.getEmail());
        mainContainer = new VBox();
        nameAddressBox = new VBox();
        qtyBox = new VBox();
        brandBox = new VBox();
        modelBox = new VBox();
        typeBox = new VBox();
        priceBox = new VBox();
        customerBox = new HBox();
        secondContainer = new HBox();



        qtylbl = new Label("QTY");
        brandlbl = new Label("Brand");
        modellbl = new Label("Model");
        typelbl = new Label("Type");
        pricelbl = new Label("Price");

        qtyBox = new VBox();
        qtyBox.getChildren().add(qtylbl);
        brandBox = new VBox();
        brandBox.getChildren().add(brandlbl);
        modelBox = new VBox();
        modelBox.getChildren().add(modellbl);
        typeBox = new VBox();
        typeBox.getChildren().add(typelbl);
        priceBox = new VBox();
        priceBox.getChildren().add(pricelbl);
        double totalPrice = 0;

        for (OrderItem orderItem :
                orderItems) {
            qtyBox.getChildren().add(new Label(orderItem.getQuantity()));
            brandBox.getChildren().add(new Label(orderItem.getBrand()));
            modelBox.getChildren().add(new Label(orderItem.getModel()));
            typeBox.getChildren().add((new Label(orderItem.getType())));
            priceBox.getChildren().add(new Label(orderItem.getPrice()));
            double price = Double.valueOf(orderItem.getPrice());
            totalPrice+= price;
        }
        confirm = new Button("Confirm");
        Label total =  new Label("Total Price: "+ totalPrice);
        mainContainer.getChildren().addAll(customerBox, secondContainer,total, confirm);
        customerBox.getChildren().addAll(customerlbl, nameAddressBox);
        nameAddressBox.getChildren().addAll(namelbl, addresslbl, citylbl, numberlbl, emaillbl, qtylbl, brandlbl, modellbl, typelbl, pricelbl);

        Scene scene = new Scene(mainContainer);
        this.setScene(scene);
        this.show();

        double finalTotalPrice = totalPrice;
        confirm.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ArrayList<Order> orders = new ArrayList<>();
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                Date date = new Date(System.currentTimeMillis());
                String id = null;
                boolean notNull = false;
                try {
                    id = orderItems.get(0).getUuid();
                    notNull = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (notNull) {
                    Order order = new Order(id, format.format(date),new String(customer.getFirstName()+customer.getLastName()), customer.getCity(), customer.getPhone(), customer.getEmail(), String.valueOf(orderItems.size()), Double.toString(finalTotalPrice));
                    OrderDAO orderDAO = new OrderDAO();
                    orderDAO.setOrders(orders);
                    close();
                    new Content(login);
                }
            }
        });

    }
}
