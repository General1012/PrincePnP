/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pnp.pnp.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;


@Entity
public class TheOrder implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(length = 9, nullable = false, unique = false)
    private Long userID;
    
     private String username ;
    @Column(length = 30, nullable = false)
    private String clientOrderDate;
    
    private String usercellphone ;
    private String destination;
    
    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<OrderedProduct> lineProducts;

    public TheOrder() {
    }

    public TheOrder(Long userID,String username, String clientOrderDate, String usercellphone, String destination, List<OrderedProduct> lineProducts) {
        this.userID = userID;
        this.username = username ;
        this.clientOrderDate = clientOrderDate;
        this.usercellphone = usercellphone;
        this.destination = destination;
        this.lineProducts = lineProducts;
    }

    
     public String getUserCellPhone() {
        return usercellphone;
    }

    public void setUserCellPhone(String usercellphone) {
        this.usercellphone = usercellphone;
    }
    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
    
     public String getUserName() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }
    
    
    public List<OrderedProduct> getLineProducts() {
        return lineProducts;
    }

    public Long getId() {
        return id;
    }

   

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getClientOrderDate() {
        return clientOrderDate;
    }

    public void setClientOrderDate(String clientOrderDate) {
        this.clientOrderDate = clientOrderDate;
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
        if (!(object instanceof TheOrder)) {
            return false;
        }
        TheOrder other = (TheOrder) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pnp.pnp.entity.Order[ id=" + id + " ]";
    }
}