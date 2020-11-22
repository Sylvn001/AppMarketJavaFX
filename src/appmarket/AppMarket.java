/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appmarket;

import appmarket.db.connect.DB;
import appmarket.db.entidades.dal.CategorieDal;
import appmarket.db.entidades.dal.ProductDal;
import appmarket.db.entities.Categorie;
import appmarket.db.entities.Product;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 *
 * @author junio
 */
public class AppMarket extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
       
        Parent root = FXMLLoader.load(getClass().getResource("MainView.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if(DB.DBconnect())
        {
            launch(args);
        }
        else
        {
           JOptionPane.showMessageDialog(null, 
                  "Problemas ao conectar: "+DB.getCon().getMensagemErro());
            if(JOptionPane.showConfirmDialog(null, "Confirma a tentativa de criação de uma nova base de dados")==JOptionPane.YES_OPTION)
            {
               if(DB.criarBD("appmarket"))
                   try
                   {   DB.restaurar("db_bkp/appmarket.backup");  }
                   catch(Exception e){ }
               }
               Platform.exit();
           }
         }              
}
    
