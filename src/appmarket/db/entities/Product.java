/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appmarket.db.entities;

/**
 *
 * @author junio
 */
public class Product {
    private int id; 
    private String name; 
    private double price; 
    private int stock;
    private Categorie categorie; 
    
     public Product() {
          this(0,"",0,0,new Categorie());
     }
     
     
    public Product(String name, double price, int stock, Categorie categorie) {
        this(0,name,price,stock,categorie);
    }

    public Product(int id, String name, double price, int stock, Categorie categorie) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.categorie = categorie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }
    
    
}
