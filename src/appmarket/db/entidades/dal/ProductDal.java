/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appmarket.db.entidades.dal;

import appmarket.db.connect.DB;
import appmarket.db.entities.Categorie;
import appmarket.db.entities.Product;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author junio
 */
public class ProductDal {
    
     public boolean create(Product p){
        String sql="INSERT INTO products VALUES (default,'#1','#2' , '#3' , '#4')";
        sql=sql.replace("#1",p.getName());
        sql=sql.replace("#2",""+p.getPrice());
        sql=sql.replace("#3",""+p.getStock());
        sql=sql.replace("#4",""+p.getCategorie().getId());
 
        //System.out.println(sql);
        return DB.getCon().manipular(sql);
     
    }
    
    
    public boolean update(Product p){
        System.out.println(p.getId());
        System.out.println(p.getCategorie().getId());
        String sql="UPDATE products SET pro_name = '#1',pro_price = '#2', pro_stock = '#3', cat_id = '#4' WHERE pro_id = "+p.getId();
        sql=sql.replace("#1",p.getName());
        sql=sql.replace("#2",""+p.getPrice());
        sql=sql.replace("#3",""+p.getStock());
        sql=sql.replace("#4",""+p.getCategorie().getId());
 
        return DB.getCon().manipular(sql);
    }
    
    public boolean delete (int id){
        String sql = "DELETE FROM products WHERE pro_id = " + id;
     
          return DB.getCon().manipular(sql);
    }
    
    public Product get(int id)
    {   Product aux=null;
        String sql="SELECT * FROM products WHERE pro_id="+id;
        ResultSet rs = DB.getCon().consultar(sql);
        try{
           if(rs.next())
             aux = new Product(rs.getInt("pro_id"),
                             rs.getString("pro_name"),
                             rs.getDouble("pro_price"),
                             rs.getInt("pro_stock"),
                             new CategorieDal().get(rs.getInt("cat_id"))) ;
        }catch(SQLException e){}
        return aux;
    }
        
    public List<Product> get(String filter)
    {   List <Product> products=new ArrayList();
        String sql="SELECT * FROM products";
        if (!filter.isEmpty())
            sql+=" WHERE "+filter;
        ResultSet rs = DB.getCon().consultar(sql);
        try{
           while(rs.next())
             products.add(new Product(rs.getInt("pro_id"),
                             rs.getString("pro_name"),
                             rs.getDouble("pro_price"),
                             rs.getInt("pro_stock"),
                            new CategorieDal().get(rs.getInt("cat_id")))); 
        }catch(SQLException e){}
        return products;
    }
    
}
