/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pnp.pnp.services;

import com.pnp.pnp.entity.User;
import com.pnp.pnp.entity.Product;
import com.pnp.pnp.repositories.ProductRepo;
import com.pnp.pnp.entity.Product;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProductService {

    String status;
    String message;
    HashMap map = new HashMap();
    
    
    
    @Autowired
    private ProductRepo productRepository;
    

    
    
    public HashMap addproduct(Product product) {

        Product prod = productRepository.findById(product.getId());     // find by email

        if (prod == null) {
            productRepository.save(product);                  // use crud interface and save data in database
            message = "Added product succesfully";
            status = "success";
        } else {
            message = "Adding product unsuccesfull";
            status = "fail";
        }

        // values for angualar
        map.put("status", status);
        map.put("message", message);
        return map;
    }
    
      public ArrayList<Product> getAllProducts() {

        productRepository.findAll();

        ArrayList<Product> products = new ArrayList<>();
         productRepository.findAll().forEach(products::add);
        

        return products;
    }
    
    
    public List<Product> listProducts(){
        ArrayList<Product> list = new ArrayList<>()  ;
          productRepository.findAll().forEach(list::add);
          return list;
       }
    
      public HashMap deleteProduct(Long Id){
       map = new HashMap();
        message = "Product Removed.";
       status = "OK";
        productRepository.delete(Id);
        
       map.put("status", status);
        map.put("message", message);
        return map;
    }
      
      
  

}
    


