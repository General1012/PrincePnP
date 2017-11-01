/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pnp.pnp.entity;

import java.io.Serializable;
import javax.persistence.Entity;


@Entity
public class Customer extends User implements Serializable {

  
    private static final long serialVersionUID = 1L;
	
    private String securityQuestuion;
    private String answer;

    public Customer() {
    }

    public Customer(String securityQuestuion, String answer, Long id, String firstname, String lastname, String email, String password, String userRole, String cellphonNumber, String gender,String address, String dateOfBirth) {
        super(id, firstname, lastname, email, password, userRole, cellphonNumber, gender,address, dateOfBirth);
        this.securityQuestuion = securityQuestuion;
        this.answer = answer;
    }

   

    public String getSecurityQuestuion() {
        return securityQuestuion;
    }

    public void setSecurityQuestuion(String securityQuestuion) {
        this.securityQuestuion = securityQuestuion;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

}
