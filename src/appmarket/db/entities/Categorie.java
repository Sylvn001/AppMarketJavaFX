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
public class Categorie {
    private int id; 
    private String name, description; 
    
    public Categorie(){
        this(0,"","");
    }
    
    public Categorie(String name, String description) {
        this(0,name,description);
    }
    
    public Categorie(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return name;
    }

    
    
    
    
    
}
