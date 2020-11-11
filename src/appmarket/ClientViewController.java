/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appmarket;

import appmarket.db.entidades.dal.ClientDal;
import appmarket.db.entities.Client;
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
public class ClientViewController implements Initializable {

    @FXML
    private TextField txFilter;
    @FXML
    private TableView<Client> table;
    @FXML
    private TableColumn<Client, Integer> tbId;
    @FXML
    private TableColumn<Client, Integer> tbDocument;
    @FXML
    private TableColumn<Client, String> tbName;
    @FXML
    private TableColumn<Client, String> tbAddress;
    @FXML
    private TableColumn<Client, String> tbNeighborhood;
    @FXML
    private TableColumn<Client, String> tbCity;
    @FXML
    private TableColumn<Client, String> tbCep;
    @FXML
    private TableColumn<Client, String> tbUf;
    @FXML
    private TableColumn<Client, String> tbEmail;
    
    public static Client client;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO.
        tbId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tbDocument.setCellValueFactory(new PropertyValueFactory<>("document"));
        tbName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tbAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        tbNeighborhood.setCellValueFactory(new PropertyValueFactory<>("neighborhood"));
        tbCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        tbCep.setCellValueFactory(new PropertyValueFactory<>("cep"));
        tbUf.setCellValueFactory(new PropertyValueFactory<>("uf"));
        tbEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tableLoad("");
    }

    private  void tableLoad(String filter){
        ClientDal cliDal = new ClientDal();
        List <Client> clients = cliDal.get(filter);
        System.out.println(clients);
        table.setItems(FXCollections.observableArrayList(clients));
    }    

    @FXML
    private void evtSearch(ActionEvent event) {
        String filter=" upper(cli_name) like '%#%'";
        filter=filter.replace("#", this.txFilter.getText().toUpperCase());
        tableLoad(filter);
    }

    @FXML
    private void evtNewClient(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ClientCreateView.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        tableLoad("");
    }

    @FXML
    private void evtClose(ActionEvent event) {
        txFilter.getScene().getWindow().hide();
    }

    @FXML
    private void edit(ActionEvent event) throws IOException {
        this.client = table.getSelectionModel().getSelectedItem();
    
        Parent root = FXMLLoader.load(getClass().getResource("ClientCreateView.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        tableLoad("");
        ClientViewController.client=null;
    }

    @FXML
    private void delete(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Operation");
        alert.setContentText("This operation delete permamentily the Categorie, you sure are sure to continue?");
        
        Optional<ButtonType> confirm = alert.showAndWait();
    
        if(confirm.get() == ButtonType.OK){
             this.client = table.getSelectionModel().getSelectedItem();
            ClientDal cdal = new ClientDal(); 
            cdal.delete(client.getId());
            Alert alertSuccess = new Alert(Alert.AlertType.INFORMATION); 
            alertSuccess.setHeaderText("Categorie deleted!");
            alertSuccess.setContentText("The Categorie are deleted!");
            alertSuccess.showAndWait();
            tableLoad("");
        }
    }
    
}
