/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appmarket;

import appmarket.db.entidades.dal.CategorieDal;
import appmarket.db.entidades.dal.ProductDal;
import appmarket.db.entities.Categorie;
import appmarket.db.entities.Product;
import appmarket.useful.MaskFieldUtil;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author junio
 */
public class ProcutCreateViewController implements Initializable {

    @FXML
    private ComboBox<Categorie> cbCategorie;
    @FXML
    private TextField pPrice;
    @FXML
    private TextField pName;
    @FXML
    private Spinner<Integer> pStock;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        MaskFieldUtil.monetaryField(this.pPrice);
        cbCategorie.setItems(FXCollections.observableArrayList(new CategorieDal().get("")));
        pStock.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 1));
 
        if(ProductListViewController.product != null)
        {
            Product p=ProductListViewController.product;
            pName.setText(""+p.getName());
            pPrice.setText(String.valueOf(p.getPrice()).replace(".", ",") );
            pStock.getValueFactory().setValue(p.getStock()); 
            cbCategorie.setValue(p.getCategorie());
        }
           
        Platform.runLater(()-> {pName.requestFocus();});
    }    

    @FXML
    private void evtCreateProduct(ActionEvent event) {
       // System.out.println(cbCategorie.getValue().getId());
       //System.out.println(c.getName() + " " + c.getDescription() + "" );
       Categorie categorie = cbCategorie.getValue();
       Double price=0.0;
       int stock = 0;
       try{
        
             price = Double.parseDouble(pPrice.getText().replace(",", "."));
             stock = pStock.getValue().intValue();
       }
       catch(NumberFormatException e){
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setHeaderText("Error. Invalid data in forms! Please verify if inputs a EMPTY");
           alert.setContentText("Erro: " + e);
           alert.showAndWait();
       }
            
        Product p = new Product(pName.getText(), price, stock, categorie);
        ProductDal pdal= new ProductDal(); 
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if(ProductListViewController.product != null){
            p.setId(ProductListViewController.product.getId());
            pdal.update(p);
            alert.setHeaderText("Success! Product Updated");
            alert.setHeaderText("Product updated with Success!!!");
            alert.showAndWait();
            closeW();
        }else{
      
            if(pdal.create(p) ){
                
                alert.setHeaderText("Success! Product Created");
                alert.setHeaderText("Product Created with Success!!!");
                alert.showAndWait();
                closeW();
            }
        }
    
    }
    
    private void closeW(){
        pName.getScene().getWindow().hide();
    }

    @FXML
    private void evtCancel(ActionEvent event) {
        closeW();
    }
    
}
