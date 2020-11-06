/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appmarket.db.entities;

import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author junio
 */
public class Order {
    static public class ItemOrder{
        private int id;
        private Product product; 
        private int amount; 
        private double price;

        public ItemOrder() {
            this(0,new Product(),0,0);
        }
        
         public ItemOrder(Product product, int amount, double price) {
             this(0,product,0,0);
        }

        public ItemOrder(int id, Product product, int amount, double price) {
            this.id = id;
            this.product = product;
            this.amount = amount;
            this.price = price;
        }    

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }
    }
    
    private int id; 
    private Client client; 
    private LocalDate date; 
    private double freight; 
    private double total; 
    private List<ItemOrder> itens;

    public Order() {
        this(0,new Client(),LocalDate.now(),0,0);
    }

    public Order(Client client, LocalDate date, double freight, double total) {
         this(0,client,date,freight,total);
          

    }

    public Order(int id, Client client, LocalDate date, double freight, double total) {
        this.id = id;
        this.client = client;
        this.date = date;
        this.freight = freight;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getFreight() {
        return freight;
    }

    public void setFreight(double freight) {
        this.freight = freight;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
     
    public boolean add(Product p, int amount, double price)
    {
        return itens.add(new ItemOrder(p,amount,price));
    }
    public boolean add(ItemOrder io)
    {
        return itens.add(io);
    }
    public List<ItemOrder>getItens()
    {
        return itens;
    }    
  
    
}
