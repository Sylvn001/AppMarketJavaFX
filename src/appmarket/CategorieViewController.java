/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appmarket;

import appmarket.db.entidades.dal.CategorieDal;
import appmarket.db.entities.Categorie;
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
import javafx.scene.control.ButtonType;
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
public class CategorieViewController implements Initializable {

    @FXML
    private TextField txFilter;
    @FXML
    private TableView<Categorie> table;
    @FXML
    private TableColumn<Categorie, Integer> cID;
    @FXML
    private TableColumn<Categorie, String> cName;
    @FXML
    private TableColumn<Categorie, String> cDesc;
    
    public static Categorie categorie; 

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cID.setCellValueFactory(new PropertyValueFactory<>("id"));
        cName.setCellValueFactory(new PropertyValueFactory<>("name"));
        cDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
      
        tableLoad("");
    }    

    @FXML
    private void evtSearch(ActionEvent event) {
        String filter=" upper(cat_name) like '%#%'";
        filter=filter.replace("#", this.txFilter.getText().toUpperCase());
       
        tableLoad(filter);
    }
    
    private void tableLoad(String filter){
        CategorieDal dal = new CategorieDal();
        List <Categorie> categories = dal.get(filter);
        table.setItems(FXCollections.observableArrayList(categories));  
    }

    @FXML
    private void evtCreate(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("CategorieCreateView.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        tableLoad("");
    }

    @FXML
    private void evtClose(ActionEvent event) {
        table.getScene().getWindow().hide();
    }

    @FXML
    private void evtEdit(ActionEvent event) throws IOException {
        this.categorie = table.getSelectionModel().getSelectedItem();
    
        Parent root = FXMLLoader.load(getClass().getResource("CategorieCreateView.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        tableLoad("");
        CategorieViewController.categorie=null;
    }

    @FXML
    private void evtDelete(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Operation");
        alert.setContentText("This operation delete permamentily the Categorie, you sure are sure to continue?");
        
        Optional<ButtonType> confirm = alert.showAndWait();
    
        if(confirm.get() == ButtonType.OK){
            Categorie c = table.getSelectionModel().getSelectedItem(); 
            CategorieDal cdal = new CategorieDal(); 
            cdal.delete(c.getId());
            Alert alertSuccess = new Alert(Alert.AlertType.INFORMATION); 
            alertSuccess.setHeaderText("Categorie deleted!");
            alertSuccess.setContentText("The Categorie are deleted!");
            alertSuccess.showAndWait();
            tableLoad("");
        }
    }
    
}
