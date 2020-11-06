/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appmarket;

import appmarket.db.entities.Categorie;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void evtSearch(ActionEvent event) {
    }

    @FXML
    private void evtCreate(ActionEvent event) {
    }

    @FXML
    private void evtClose(ActionEvent event) {
        table.getScene().getWindow().hide();
    }

    @FXML
    private void evtEdit(ActionEvent event) {
    }

    @FXML
    private void evtDelete(ActionEvent event) {
    }
    
}
