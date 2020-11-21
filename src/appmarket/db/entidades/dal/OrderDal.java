/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appmarket.db.entidades.dal;

import appmarket.db.connect.DB;
import appmarket.db.entities.Order;
import appmarket.db.entities.Order.ItemOrder;
import appmarket.db.entities.Product;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author junio
 */
public class OrderDal {
    public boolean create(Order order){
        boolean flag = false; 
        
        String sql="INSERT INTO order VALUES (default,'#1','#2', '#3', '#4')";
        sql=sql.replace("#1",""+order.getDate().toString());
        sql=sql.replace("#2",""+order.getFreight());
        sql=sql.replace("#3",""+order.getTotal());
        sql=sql.replace("#4",""+order.getClient().getId());
        
        if(DB.getCon().manipular(sql)){
            int odr = DB.getCon().getMaxPK("order", "ord_id");
            
            for(Order.ItemOrder io : order.getItens()){
                sql="insert into items_order values(default, #1, #2, #3, #4)";
                sql=sql.replace("#1",""+io.getAmount());
                sql=sql.replace("#2",""+io.getPrice());
                sql=sql.replace("#3",""+io.getProduct());
//                sql=sql.replace("#4",""+io.get);
                DB.getCon().manipular(sql); 
            }
            flag = true;
        }
        
        return flag;
    }
          
    
    public boolean alterar(){
        
        //update na tabela pedido 
        //apagar todos os itens do pedido
        //gravar novamente os itens
        
        return true;
    }
    
    public boolean apagar(int id){
        String sql = "delete from orders where ord_id="+id;
        if(DB.getCon().manipular(sql)){
            sql = "delete from items_order where cat_id="+id;
            
            if(DB.getCon().manipular(sql)){
                return true;
            }
            return false;
        }
        return false;
    }
    public Order get(int id) throws SQLException
    {   Order aux=null;
        String sql="SELECT * FROM orders WHERE ord_id="+id;
        ResultSet rs = DB.getCon().consultar(sql);
        try{
           if(rs.next())
             aux=new Order(rs.getInt("ord_id"),
             new ClientDal().get(rs.getInt("cli_id")),
             rs.getDate("ord_data").toLocalDate() ,
             rs.getDouble("ord_freight"),
             rs.getDouble("ord_total"));
           
             sql = "select * from items_order where ord_id = "+id;
            while(rs.next())
            aux.add(new ProductDal().get(rs.getInt("ior_id")),
                    rs.getInt("ior_amount"),
                    rs.getDouble("ior_price"));
        }catch(SQLException e){}
       
        return aux;
    }

    
    public List<Order> get(String filter) 
    {   List <Order> orders=new ArrayList();;
        ClientDal clientDal = new ClientDal();
        int pos=0;
        String sql="SELECT * FROM orders";
        if (!filter.isEmpty())
            sql+=" WHERE "+filter;
        ResultSet rs = DB.getCon().consultar(sql);
        ResultSet items_order_rs;
        try{
           while(rs.next())
           {
                orders.add( 
                    new Order(rs.getInt("ord_id"),
                    clientDal.get(rs.getInt("cli_id")),
                    rs.getDate("ord_data").toLocalDate() ,
                    rs.getDouble("ord_freight"),
                    rs.getDouble("ord_total"))
                );
                sql = "select * from items_order where ord_id = "+orders.get(pos).getId();    
           }
        }catch(SQLException e){}
        return orders;
    }    
}
