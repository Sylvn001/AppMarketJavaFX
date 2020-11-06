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
        if(!DB.DBconnect())
        {
            JOptionPane.showMessageDialog(null,"Erro: "+DB.getCon().getMensagemErro());
            Platform.exit();
        }
        
//        Categorie cat; ;
//        cat = new Categorie("batata", "algo");
//        System.out.println(cat.getName() + " " + cat.getDescription());
//        
//        CategorieDal catDal = new CategorieDal();
//        
//        System.out.println(catDal.create(cat));

//        Product p; 
//        p = new Product("Monster energetico", 7.9, 140, new Categorie(1, "", ""));
//        ProductDal pdal = new ProductDal();
//        System.out.println(pdal.create(p));
//        
        launch(args);;
    }
    
}
