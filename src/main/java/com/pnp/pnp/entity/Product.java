/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pnp.pnp.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



@Entity
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
   
    private Long id; 
    private String name ;
    private String description;  
    private double price;  
    private int quantity;
    private String category;
    private String image ;
    

    public Product() {
    }
    public Product(String name, String description, double price, int quantity ,String category,String image) {
        this.name = name ;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.image = image;
        
    }
  
   
    
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
    
    
    
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public String getProductDesc() {
        return description;
    }

    public void setProductDesc(String productDesc) {
        this.description= productDesc;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }
    
     

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageURL(){
        return "img/" + name +".jpg" ;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Product)) {
            return false;
        }
        Product other = (Product) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Product{" + "name=" + name + ",description=" + description + ", price=" + price + ", quantity=" + quantity + ", category=" + category + ", image=" + image+ '}';
    }

   

}
