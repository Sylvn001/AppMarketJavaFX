/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appmarket;

import appmarket.db.entidades.dal.ClientDal;
import appmarket.db.entities.Client;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author junio
 */
public class ClientCreateViewController implements Initializable {

    @FXML
    private TextField txDocument;
    @FXML
    private TextField txName;
    @FXML
    private TextArea txAddress;
    @FXML
    private TextField txNeigh;
    @FXML
    private TextField txCity;
    @FXML
    private TextField txCep;
    @FXML
    private TextField txUf;
    @FXML
    private TextField txEmail;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        if(ClientViewController.client != null)
        {
            Client  c = ClientViewController.client;
            txDocument.setText(""+c.getDocument());
            txName.setText(c.getName() );
            txAddress.setText(c.getAddress()); 
            txNeigh.setText(c.getNeighborhood());
            txCity.setText(c.getCity());
            txCep.setText(c.getCep());
            txUf.setText(c.getUf());
            txEmail.setText(c.getEmail());
        }
           
        Platform.runLater(()-> {txDocument.requestFocus();});
    }    

    @FXML
    private void Create(ActionEvent event) {
                 
        Client c = new Client(
            Integer.parseInt(txDocument.getText()),
            txName.getText(),
            txAddress.getText(),
            txNeigh.getText(),
            txCity.getText(), 
            txCep.getText(), 
            txUf.getText(),
            txEmail.getText()
        );
        
        ClientDal cdal= new ClientDal(); 
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if(ClientViewController.client != null){
            c.setId(ClientViewController.client.getId());
            cdal.update(c);
            alert.setHeaderText("Success! Client Updated");
            alert.setHeaderText("Client updated with Success!!!");
            alert.showAndWait();
            closeW();
        }else{
      
            if(cdal.create(c) ){
                
                alert.setHeaderText("Success! Client registed");
                alert.setHeaderText("Client Created with Success!!!");
                alert.showAndWait();
                closeW();
            }
        }
    }
    
    public void closeW(){
        txDocument.getScene().getWindow().hide();
    }

    @FXML
    private void Cancel(ActionEvent event) {
        closeW();
    }
    
}
