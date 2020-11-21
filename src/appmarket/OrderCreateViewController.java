/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appmarket;

import static appmarket.OrderListViewController.order;
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
import javafx.scene.input.DragEvent;
import javafx.scene.input.InputMethodEvent;

/**
 * FXML Controller class
 *
 * @author junio
 */
public class OrderCreateViewController implements Initializable {

    @FXML
    private ComboBox<Client> cbClient;
  
    @FXML
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
    
    private ArrayList<Order.ItemOrder> itemsOrder; 
    private Double total = 0.0;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cbAmount.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1));
        itemsOrder = new ArrayList();
        tbProd.setCellValueFactory(new PropertyValueFactory<>("product"));
        tbPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        tbAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        dpData.setValue(LocalDate.now());
        if(OrderListViewController.order != null){
            dpData.setValue(order.getDate());
            txTotal.setText(""+order.getTotal());
            txFreight.setText(""+ order.getFreight());
            cbClient.setValue(order.getClient());
            itemsOrder.addAll(order.getItens());
            table.setItems(FXCollections.observableArrayList(itemsOrder));          
        }
        
        cbPrice.setDisable(true);
        txTotal.setDisable(true);
        cbLoad();
    }    
    
    public void cbLoad(){
        //Default params initialize
        ClientDal cdal = new ClientDal();
        ProductDal pdal= new ProductDal();
//      cbPrice.setText();;
        cbClient.setItems(FXCollections.observableArrayList(new ClientDal().get("")));
        cbProduct.setItems(FXCollections.observableArrayList(new ProductDal().get("")));
  
    }

    @FXML
    private void evtCreate(ActionEvent event) {
   
        Client cli = cbClient.getValue();
        total+= Double.parseDouble(txFreight.getText());
        this.txTotal.setText(String.valueOf(total));

        Order o = new Order(cli, dpData.getValue(), Double.parseDouble(txFreight.getText()), total);
        o.setItens(itemsOrder);
        OrderDal odal= new OrderDal(); 
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if(OrderListViewController.order != null){
            o.setId(order.getId());
            if(new OrderDal().update(o)){
               alert.setHeaderText("Success! order Updated");
               alert.setHeaderText("Order updated with Success!!!");
               alert.showAndWait();   
            }
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
            Product p = cbProduct.getValue();
            int amount = cbAmount.getValue();
            int pStock = p.getStock();
            int atualStock = pStock - amount;
            
            if(atualStock >= 0)
            {
                ItemOrder io = new ItemOrder(p,amount , Double.parseDouble(cbPrice.getText()));  
                System.out.println(io.getAmount());
                itemsOrder.add(io);
                table.setItems(FXCollections.observableArrayList(itemsOrder));  
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro, invalid amount");
                alert.setHeaderText("Erro, invalid amount. The stock of product selectioned is bigger then atual Stock ");
                alert.showAndWait();
            }
                            
        }
        catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR); 
            alert.setTitle("Erro, Value NULL in Fields");
            alert.setHeaderText("Erro: " + e);
            alert.showAndWait();
        }
        int i=0;
        total=0.0;
        while(i < itemsOrder.size()){
            total+=itemsOrder.get(i).getPrice() * itemsOrder.get(i).getAmount();
            i++;
        }
        this.txTotal.setText(String.valueOf(total));
       
    }

    @FXML
    private void evtDelItemProduct(ActionEvent event) {
        ItemOrder io = table.getSelectionModel().getSelectedItem();
        itemsOrder.remove(io);
        table.setItems(FXCollections.observableArrayList(itemsOrder));
        
        int i=0;
        total=0.0;
        while(i < itemsOrder.size()){
            total+=itemsOrder.get(i).getPrice() * itemsOrder.get(i).getAmount();
            i++;
        }
        
    }


    @FXML
    private void evtChange(ActionEvent event) {
        System.out.println("alo");
            this.cbPrice.setText(String.valueOf(cbProduct.getSelectionModel().getSelectedItem().getPrice()));

    }
    
}
