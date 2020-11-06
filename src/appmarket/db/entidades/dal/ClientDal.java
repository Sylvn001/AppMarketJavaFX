/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appmarket.db.entidades.dal;

import appmarket.db.connect.DB;
import appmarket.db.entities.Client;

/**
 *
 * @author junio
 */
public class ClientDal {
     
    public boolean create(Client c){
     String sql="INSERT INTO products VALUES (default,'#1','#2' , '#3' , '#4')";
//     sql=sql.replace("#1",c.getName());;
//     sql=sql.replace("#2",""+p.getPrice());
//     sql=sql.replace("#3",""+p.getStock());
//     sql=sql.replace("#4",""+p.getCategorie().getId());

     //System.out.println(sql);
     return DB.getCon().manipular(sql);

 }
    
    
}
