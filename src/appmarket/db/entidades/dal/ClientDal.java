/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appmarket.db.entidades.dal;

import appmarket.db.connect.DB;
import appmarket.db.entities.Client;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author junio
 */
public class ClientDal {
     
    public boolean create(Client c){
     String sql="INSERT INTO clients VALUES (default,'#1','#2' , '#3' , '#4' , '#5' , '#6', '#7', '#8'  )";
     sql=sql.replace("#1",""+c.getDocument());
     sql=sql.replace("#2",c.getName());
     sql=sql.replace("#3",c.getAddress());
     sql=sql.replace("#4",c.getNeighborhood());
     sql=sql.replace("#5",c.getCity());
     sql=sql.replace("#6",c.getCep());
     sql=sql.replace("#7",c.getUf());
     sql=sql.replace("#8",c.getEmail());
     //System.out.println(sql);
     return DB.getCon().manipular(sql);
 }
    public boolean update(Client c){
        System.out.println(c.getId());
        String sql="UPDATE clients SET"
                + " cli_document = '#1', cli_name = '#2', cli_address = '#3', cli_neighborhood = '#4', cli_city = '#5', cli_cep = '#6', cli_uf = '#7', cli_email = '#8' "
                + " WHERE cli_id = "+c.getId();
     
        sql=sql.replace("#1",""+c.getDocument());
        sql=sql.replace("#2",c.getName());
        sql=sql.replace("#3",c.getAddress());
        sql=sql.replace("#4",c.getNeighborhood());
        sql=sql.replace("#5",c.getCity());
        sql=sql.replace("#6",c.getCep());
        sql=sql.replace("#7",c.getUf());
        sql=sql.replace("#8",c.getEmail());
        System.out.println(sql);
        return DB.getCon().manipular(sql);
    } 
    
    public boolean delete (int id){
        String sql = "DELETE FROM clients WHERE cli_id = " + id;
     
          return DB.getCon().manipular(sql);
    }
    
      public Client get(int id)
    {   Client aux=null;
        String sql="SELECT * FROM clients WHERE cli_id="+id;
        ResultSet rs = DB.getCon().consultar(sql);
        try{
           if(rs.next())
             aux = new Client(rs.getInt("cli_id"),
                             rs.getInt("cli_document"),
                             rs.getString("cli_name"),
                             rs.getString("cli_address"),
                             rs.getString("cli_neighborhood"),
                             rs.getString("cli_city"),
                             rs.getString("cli_cep"),
                             rs.getString("cli_uf"), 
                             rs.getString("cli_email")) ;
        }catch(SQLException e){}
        return aux;
    }
      
    public List<Client> get(String filter)
    {   List <Client> clients=new ArrayList();
        String sql="SELECT * FROM clients";
        if (!filter.isEmpty())
            sql+=" WHERE "+filter;
        ResultSet rs = DB.getCon().consultar(sql);
        try{  
            
           
           while(rs.next())
             clients.add(new Client(rs.getInt("cli_id"),
                             rs.getInt("cli_document"),
                             rs.getString("cli_name"),
                             rs.getString("cli_address"),
                             rs.getString("cli_neighborhood"),
                             rs.getString("cli_city"),
                             rs.getString("cli_cep"),
                             rs.getString("cli_uf"), 
                             rs.getString("cli_email"))); 
        }catch(SQLException e){System.out.println("Error: " + e);}
      
        return clients;
    }
}
