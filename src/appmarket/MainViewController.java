/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appmarket;

import appmarket.db.connect.DB;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.view.JasperViewer;

/**
 * FXML Controller class
 *
 * @author junio
 */
public class MainViewController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
       
      
    }    

    @FXML
    private void evtProdList(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ProductListView.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    @FXML
    private void evtCategorieList(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("CategorieView.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        
    }

    @FXML
    private void evtClientList(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ClientView.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    @FXML
    private void evtOrderList(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("OrderListView.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
    
    private void  generateRelatorie(String sql, String relat){
        try
        { //sql para obter os dados para o relatorio

          ResultSet rs = DB.getCon().consultar(sql);
          //implementação da interface JRDataSource para DataSource ResultSet
          JRResultSetDataSource jrRS = new JRResultSetDataSource(rs);
          //chamando o relatório
          String jasperPrint =          
              JasperFillManager.fillReportToFile(relat,null, jrRS);
          JasperViewer viewer = new JasperViewer(jasperPrint, false, false);
          viewer.setExtendedState(JasperViewer.MAXIMIZED_BOTH);//maximizado
          viewer.setTitle("Relatório de Alunos");//titulo do relatório
          viewer.setVisible(true);
        } catch (JRException erro)
        {  erro.printStackTrace(); }
    }

    @FXML
    private void evtRelProducts(ActionEvent event) {
        generateRelatorie("select * from products left join categories as c on products.cat_id = c.cat_id","reports/Products.jasper" );
    }

    @FXML
    private void evtrelClients(ActionEvent event) {
         generateRelatorie("select * from clients","reports/Clients.jasper" );
    }

    @FXML
    private void evtRelOrders(ActionEvent event) {
        generateRelatorie("select * from orders as o left join items_order as ior on o.ord_id = ior.ord_id","reports/Order.jasper" );
    }

    @FXML
    private void evtHelpCredits(ActionEvent event) {
        
        File file = new File("help/help.chm");
        try {
            Runtime.getRuntime().exec("HH.EXE ms-its:" + file.getAbsolutePath() + "::/Credits.htm");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @FXML
    private void evtBackup(ActionEvent event) {
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Backup of DB");
        alert.setContentText("This operation realize Backup, you sure are sure to continue?");
        
        Optional<ButtonType> confirm = alert.showAndWait();
    
        if(confirm.get() == ButtonType.OK){
           try{
               DB.backup("db_bkp/appmarket.backup");
           }
           catch(Exception e){
               System.out.println(e);
           }
        }
    }
    
}
