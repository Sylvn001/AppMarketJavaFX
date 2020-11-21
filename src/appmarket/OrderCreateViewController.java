/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appmarket;

import appmarket.db.entidades.dal.ClientDal;
import appmarket.db.entidades.dal.OrderDal;
import appmarket.db.entidades.dal.ProductDal;
import appmarket.db.entities.Client;
import appmarket.db.entities.Order;
import appmarket.db.entities.Order.ItemOrder;
import appmarket.db.entities.Product;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author junio
 */
public class OrderCreateViewController implements Initializable {

    @FXML
    private ComboBox<Client> cbClient;
  
    private DatePicker dpData;
    @FXML
    private TextField txFreight;
    @FXML
    private TextField txTotal;
    @FXML
    private ComboBox<Product> cbProduct;
    @FXML
    private Spinner<Integer> cbAmount;
    @FXML
    private TextField cbPrice;
    @FXML
    private TableView<ItemOrder> table;
    @FXML
    private TableColumn<Order.ItemOrder, Product> tbProd;
    @FXML
    private TableColumn<Order.ItemOrder, Double> tbPrice;
    @FXML
    private TableColumn<Order.ItemOrder, Integer> tbAmount;
    
    private List itemsOrder; 

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cbAmount.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 1));
        tbProd.setCellValueFactory(new PropertyValueFactory<>("product"));
        tbPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        tbAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        itemsOrder = new ArrayList();
        cbLoad();
    }    
    
    public void cbLoad(){
        //Default params initialize
        ClientDal cdal = new ClientDal();
        ProductDal pdal= new ProductDal();
        cbClient.setItems(FXCollections.observableArrayList(new ClientDal().get("")));
        cbProduct.setItems(FXCollections.observableArrayList(new ProductDal().get("")));
  
    }

    @FXML
    private void evtCreate(ActionEvent event) {
   
       Client cli = cbClient.getValue();
       Double price=0.0;
          
        Order o = new Order(cli, LocalDate.MAX, 0, 0);
        OrderDal odal= new OrderDal(); 
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if(ProductListViewController.product != null){
            o.setId(ProductListViewController.product.getId());
            //odal.update(p);
            alert.setHeaderText("Success! order Updated");
            alert.setHeaderText("Order updated with Success!!!");
            alert.showAndWait();
            closeW();
        }else{
      
            if(odal.create(o) ){
                alert.setHeaderText("Order Created with Success!!!");
                alert.showAndWait();
                closeW();
            }else{
                alert.setHeaderText("Erro");
                alert.showAndWait(); 
                closeW();
            }
        }
    }
    
    public void closeW(){
        this.cbAmount.getScene().getWindow().hide();
    }

    @FXML
    private void evtCancel(ActionEvent event) {
        closeW();
    }

    @FXML
    private void newItemOrder(ActionEvent event) {
        try{
            int v  = cbAmount.getValue().intValue();
            Double d = Double.parseDouble(cbPrice.getText());
            itemsOrder.add(new ItemOrder(cbProduct.getValue(), v, d));
            System.out.println(v);
            System.out.println(d);
        }
        catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR); 
            alert.setTitle("Erro, Value NULL in Fields");
            alert.setHeaderText("Erro: " + e);
            alert.showAndWait();
        }
        table.setItems(FXCollections.observableArrayList(itemsOrder));                   
    }
    
}
