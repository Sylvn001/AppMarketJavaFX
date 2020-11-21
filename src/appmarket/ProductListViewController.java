/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appmarket;

import appmarket.db.entidades.dal.ProductDal;
import appmarket.db.entities.Categorie;
import appmarket.db.entities.Product;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
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
public class ProductListViewController implements Initializable {

    @FXML
    private TextField inputSearch;
    @FXML
    private Button Pexit;
    @FXML
    private TableColumn<Product, Integer> id;
    @FXML
    private TableColumn<Product, String> name;
    @FXML
    private TableColumn<Product, Double> price;
    @FXML
    private TableColumn<Product, Integer> stock;
    @FXML
    private TableColumn<Product, Categorie> categorie;
    @FXML
    private TableView<Product> table;
    @FXML
    private Button Pexit1;
    public static Product product=null;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //(GlyphsDude.createIcon(FontAwesomeIcons.BARCODE, "5px"));
        
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        categorie.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        
        tableLoad("");
    }    
    
    private void tableLoad(String filter){
        ProductDal dal = new ProductDal();
        List <Product> products = dal.get(filter);
        table.setItems(FXCollections.observableArrayList(products));       
    }

    @FXML
    private void evtSearch(ActionEvent event) {
         String filter=" upper(pro_name) like '%#%'";
        filter=filter.replace("#", this.inputSearch.getText().toUpperCase());
       
        tableLoad(filter);
    }

    @FXML
    private void evtNewProduct(ActionEvent event) throws IOException {    
        Parent root = FXMLLoader.load(getClass().getResource("ProcutCreateView.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        tableLoad("");
    }

    @FXML
    private void evtClose(ActionEvent event) {
        this.table.getScene().getWindow().hide();
    }

    @FXML
    private void pDelete(ActionEvent event) {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Operation");
        alert.setContentText("This operation delete permamentily the Product, you sure are sure to continue?");
        
        Optional<ButtonType> confirm = alert.showAndWait();
    
        if(confirm.get() == ButtonType.OK){
            Product p = table.getSelectionModel().getSelectedItem(); 
            ProductDal pdal = new ProductDal(); 
            pdal.delete(p.getId());
            Alert alertSuccess = new Alert(Alert.AlertType.INFORMATION); 
            alertSuccess.setHeaderText("Product deleted!");
            alertSuccess.setContentText("The product are deleted!");
            alertSuccess.showAndWait();
            tableLoad("");
        }
        
       
    }

    @FXML
    private void pUpdate(ActionEvent event) throws IOException {
        
        ProductListViewController.product = table.getSelectionModel().getSelectedItem(); 
        
        Parent root = FXMLLoader.load(getClass().getResource("ProcutCreateView.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        tableLoad("");
        ProductListViewController.product=null;
    }
    
}
