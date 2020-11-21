/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appmarket;

import appmarket.db.entidades.dal.OrderDal;
import appmarket.db.entidades.dal.ProductDal;
import appmarket.db.entities.Client;
import appmarket.db.entities.Order;
import appmarket.db.entities.Product;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author junio
 */
public class OrderListViewController implements Initializable {

    @FXML
    private TextField inputSearch;
    @FXML
    private TableView<Order> table;
    @FXML
    private TableColumn<Order, Integer> id;
    @FXML
    private TableColumn<Order, Client> cli;
    @FXML
    private TableColumn<Order, Date> data;
    @FXML
    private TableColumn<Order, Integer> freight;

    @FXML
    private TableColumn<Order, Double> total;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        cli.setCellValueFactory(new PropertyValueFactory<>("client"));
        data.setCellValueFactory(new PropertyValueFactory<>("date"));
        freight.setCellValueFactory(new PropertyValueFactory<>("freight"));
        total.setCellValueFactory(new PropertyValueFactory<>("total"));
        tableLoad("");
        
    }    
    
    private void tableLoad(String filter) 
    {
        OrderDal dal = new OrderDal();
        List <Order> orders = dal.get(filter);
        table.setItems(FXCollections.observableArrayList(orders));                   
    }

    @FXML
    private void evtSearch(ActionEvent event) {
        String filter=" upper(ord_cli) like '%#%'";
        filter=filter.replace("#", this.inputSearch.getText().toUpperCase());
       
        tableLoad(filter);
    }

    @FXML
    private void evtNew(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("OrderCreateView.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    @FXML
    private void evtOrderInfo(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("OrderInfoView.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    @FXML
    private void evtEditOrder(ActionEvent event) {
//        cbCategorie.setItems(FXCollections.observableArrayList(new CategorieDal().get("")));
    }

    @FXML
    private void EvtDeleteOrder(ActionEvent event) {
    }

    
}
