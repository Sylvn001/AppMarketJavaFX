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
public class Client {
    private int id; 
    private int cli_document;
    private String name;
    private String address;
    private String neighborhood; 
    private String city; 
    private String cep; 
    private String uf; 
    private String email; 
    
     public Client() {
         this(0,0,"","","","","","", "");
     }
     

    public Client(int cli_document, String name, String address, String neighborhood, String city, String cep, String uf, String email) {
      this(0,cli_document,name,address,neighborhood, city, cep, uf, email);
    }

    public Client(int id, int cli_document, String name, String address, String neighborhood, String city, String cep, String uf, String email) {
        this.id = id;
        this.cli_document = cli_document;
        this.name = name;
        this.address = address;
        this.neighborhood = neighborhood;
        this.city = city;
        this.cep = cep;
        this.uf = uf;
        this.email = email;
    }

   

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCli_document() {
        return cli_document;
    }

    public void setCli_document(int cli_document) {
        this.cli_document = cli_document;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
    
}
