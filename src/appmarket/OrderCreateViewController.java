/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appmarket;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author junio
 */
public class OrderCreateViewController implements Initializable {

    @FXML
    private ComboBox<?> cbClient;
    @FXML
    private TextField txId;
    @FXML
    private DatePicker dpData;
    @FXML
    private TextField txFreight;
    @FXML
    private TextField txTotal;
    @FXML
    private ComboBox<?> cbProduct;
    @FXML
    private Spinner<?> cbAmount;
    @FXML
    private TextField cbPrice;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void evtCreate(ActionEvent event) {
    }

    @FXML
    private void evtCancel(ActionEvent event) {
    }
    
}
