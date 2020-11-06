/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appmarket.db.entidades.dal;

import appmarket.db.entities.Categorie;
import appmarket.db.connect.DB;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author junio
 */
public class CategorieDal {
    
    public boolean create(Categorie cat){
        String sql="INSERT INTO categories VALUES (default,'#1','#2')";
        sql=sql.replace("#1",cat.getName());
        sql=sql.replace("#2",cat.getDescription());
        //System.out.println(sql);
        return DB.getCon().manipular(sql);
     
    }
    
    
    public boolean update(Categorie cat){
        String sql="UPDATE categories SET cat_name='#1',cat_desc='#2' WHERE cat_id="+cat.getId();
        sql=sql.replace("#1",cat.getName());
        sql=sql.replace("#2",cat.getDescription());
        return DB.getCon().manipular(sql);
    }
    
    public boolean delete (int id){
        String sql = "DELETE INTO categories WHERE id = " + id;
     
          return DB.getCon().manipular(sql);
    }
    
    public Categorie get(int id)
    {   Categorie aux=null;
        String sql="SELECT * FROM categories WHERE cat_id="+id;
        ResultSet rs = DB.getCon().consultar(sql);
        try{
           if(rs.next())
             aux=new Categorie(rs.getInt("cat_id"),
                             rs.getString("cat_name"),
                             rs.getString("cat_desc"));
        }catch(SQLException e){}
        return aux;
    }
        
    public List<Categorie> get(String filter)
    {   List <Categorie> categories=new ArrayList();
        String sql="SELECT * FROM categories";
        if (!filter.isEmpty())
            sql+=" WHERE "+filter;
        ResultSet rs = DB.getCon().consultar(sql);
        try{
           while(rs.next())
             categories.add(new Categorie(rs.getInt("cat_id"),
                             rs.getString("cat_name"),
                             rs.getString("cat_desc")));
        }catch(SQLException e){}
        return categories;
    }
    
    
}
