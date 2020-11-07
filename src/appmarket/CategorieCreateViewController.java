/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appmarket;

import appmarket.db.entidades.dal.CategorieDal;
import appmarket.db.entities.Categorie;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author junio
 */
public class CategorieCreateViewController implements Initializable {

   
    @FXML
    private TextArea cDescription;
    @FXML
    private TextField cName;
   

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
          if(CategorieViewController.categorie!= null)
        {
            Categorie c = CategorieViewController.categorie;
            cName.setText(""+c.getName());
            cDescription.setText("" + c.getDescription());
        
        }
           
        Platform.runLater(()-> {cName.requestFocus();});
        
    }    

    @FXML
    private void create(ActionEvent event) {
        Categorie c = new Categorie(cName.getText(), cDescription.getText());
        CategorieDal cdal= new CategorieDal(); 
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if(CategorieViewController.categorie != null){
            c.setId(CategorieViewController.categorie.getId());
            cdal.update(c);
            alert.setHeaderText("Success! Categorie Updated");
            alert.setHeaderText("Categorie updated with Success!!!");
            alert.showAndWait();
            closeWindow();
        }else{
      
            if(cdal.create(c) ){
                
                alert.setHeaderText("Success! Categorie Created");
                alert.setHeaderText("Categorie Created with Success!!!");
                alert.showAndWait();
                closeWindow();
            }
        }
    }
    
    public void closeWindow(){
         cName.getScene().getWindow().hide();
    }

    @FXML
    private void close(ActionEvent event) {
        closeWindow();
    }
    
}
