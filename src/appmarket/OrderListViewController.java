/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appmarket;

import appmarket.db.connect.DB;
import appmarket.db.entidades.dal.OrderDal;
import appmarket.db.entidades.dal.ProductDal;
import appmarket.db.entities.Client;
import appmarket.db.entities.Order;
import appmarket.db.entities.Product;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.view.JasperViewer;

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

    public static Order order;
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
        tableLoad("");
    }

    @FXML
    private void evtOrderInfo(ActionEvent event) throws IOException, JRException {
           try
        { //sql para obter os dados para o relatorio
          String sql  = "";
          String relat = "";
          ResultSet rs = DB.getCon().consultar(sql);
          //implementação da interface JRDataSource para DataSource ResultSet
          JRResultSetDataSource jrRS = new JRResultSetDataSource(rs);
          //chamando o relatório
          String jasperPrint =          
              JasperFillManager.fillReportToFile(relat,null, jrRS);
          JasperViewer viewer = new JasperViewer(jasperPrint, false, false);
          viewer.setExtendedState(JasperViewer.MAXIMIZED_BOTH);//maximizado
          viewer.setTitle("Relatório de Alunos");//titulo do relatório
          viewer.setVisible(true);
        } catch (JRException erro)
        {  erro.printStackTrace(); }
    }

    @FXML
    private void evtEditOrder(ActionEvent event) throws IOException {
        order = table.getSelectionModel().getSelectedItem();
        Parent root = FXMLLoader.load(getClass().getResource("OrderCreateView.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        order = null;
        tableLoad("");
    }

    @FXML
    private void EvtDeleteOrder(ActionEvent event) {
        int id = table.getSelectionModel().getSelectedItem().getId();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Operation");
        alert.setContentText("This operation delete permamentily the Order + "+ id + " you sure are sure to continue?");
        
        Optional<ButtonType> confirm = alert.showAndWait();
    
        if(confirm.get() == ButtonType.OK){
 
            new OrderDal().delete(id);
            Alert alertSuccess = new Alert(Alert.AlertType.INFORMATION); 
            alertSuccess.setHeaderText("Order deleted!");
            alertSuccess.setContentText("The Order are deleted!");
            alertSuccess.showAndWait();
            tableLoad("");
        }   
    }

    @FXML
    private void evtCloseOrder(ActionEvent event) {
        Order ord = table.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Close Order!!");
        alert.setContentText("you sure are sure to continue?");
        int amountUpdate=0;
        int i=0;
        boolean flag = true;
        ProductDal pdal = new ProductDal();
        Optional<ButtonType> confirm = alert.showAndWait();
    
        if(confirm.get() == ButtonType.OK){
            int stock;
            int stockNew=0;
            stock = ord.getItens().get(i).getProduct().getStock();
            while(i < ord.getItens().size())
            {
                amountUpdate = ord.getItens().get(i).getAmount() ;
                System.out.println(amountUpdate);
                System.out.println(stock);   
                stockNew = stock -amountUpdate; 
                if(stockNew < 0)
                {
                    Alert alertBox = new Alert(Alert.AlertType.ERROR);
                    alertBox.setHeaderText("Erro, this value set stock negative, please insert a valid value for order " + ord.getItens().get(i).getProduct().getName());
                    alertBox.setTitle("Stock ERROR, Value negative!");
                    alertBox.showAndWait();
                    flag = false;
                }else{
                    pdal.updateStock(ord.getItens().get(i).getProduct().getId(), stockNew);   
                }
                i++;
            }
            if(flag)
            {
                System.out.println(amountUpdate);
                new OrderDal().delete(ord.getId());
                Alert alertSuccess = new Alert(Alert.AlertType.INFORMATION);
                alertSuccess.setHeaderText("Order Completed!!");
                alertSuccess.setContentText("The stock of products list are updated!");
                alertSuccess.showAndWait();
                tableLoad("");  
            }  
        }   
    }
}
