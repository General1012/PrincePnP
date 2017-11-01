
package com.pnp.pnp.controller;

import com.pnp.pnp.entity.Customer;
import com.pnp.pnp.entity.TheOrder;
import com.pnp.pnp.entity.User;
import com.pnp.pnp.repositories.UserRepo ;
import com.pnp.pnp.services.UserService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService customerService;
    @Autowired
    UserRepo customerRepository;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public HashMap register(@RequestBody Customer customer) {
        customer.setUserRole("customer");
        HashMap map = customerService.registerCustomer(customer);
        return map;
    }
    
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public HashMap login(@RequestBody Customer customer ,HttpServletRequest request) {
        
        HttpSession session = request.getSession();
        HashMap map = customerService.login(customer ,session );
        return map;
    }
    
     @RequestMapping(method = RequestMethod.GET,value = "/logout/{sessionID}")
	public HashMap signout(@PathVariable("sessionID") String sessionID,HttpServletRequest request){
            
            HttpSession session = request.getSession();
            HashMap response = new HashMap();
            
            if(session.getId().equals(sessionID)){
                
                session.invalidate();
                String status = "OK";
                response.put("status", status);
            }
            //System.out.println(response);

	    return response;
	}
        
        
         @RequestMapping(method = RequestMethod.POST,value = "/updateProfile")
	public HashMap updateProfile(@RequestBody String data,HttpServletRequest request){
            
            HttpSession session = request.getSession();
            
            HashMap response = customerService.updateProfile(data,session);
  
            return response;
	}
        
          @RequestMapping(value = "/customers")
           public List<User> allCustomers(){
           return  customerService.listCustomers();
    } 
    
    
    
}