
package com.pnp.pnp.controller;

import com.pnp.pnp.entity.User;
import com.pnp.pnp.entity.Product;
import com.pnp.pnp.repositories.ProductRepo;
import com.pnp.pnp.services.UserService;
import com.pnp.pnp.services.ProductService;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ProductController {
    
    @Autowired
    private ProductService productService;
    


    @RequestMapping(value = "/addproduct", method = RequestMethod.POST)
    public HashMap addproduct(@RequestBody Product product) {
        HashMap map = productService.addproduct(product);
        return map;
    }
    
    
    @RequestMapping(value = "edit_deleteproduct/{id}", method = RequestMethod.POST )
    public HashMap remove(@PathVariable("id") Long id){
        HashMap map = productService.deleteProduct(id);
        return map;
    }
    
    @RequestMapping(value = "/product")
    public List<Product> allProducts(){
       return  productService.listProducts();
    } 
    
    
}
