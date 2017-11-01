/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pnp.pnp.controller;

import com.pnp.pnp.entity.TheOrder;
import com.pnp.pnp.services.OrderService;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController

public class OrderController {
    
    @Autowired
    private OrderService orderService;
    
    @RequestMapping(value = "/checkout" ,method = RequestMethod.POST)
    public HashMap checkout(@RequestBody String orderData,HttpServletRequest request){
       
       
        System.out.println(orderData);
        //HashMap hashMap = new HashMap();
        HashMap response = orderService.processOrder(orderData,request.getSession());
           
        return response;
    }
    
    @RequestMapping(method = RequestMethod.GET,value = "/displayAllOrders/{sessionID}")
    public HashMap displayAllOrders(@PathVariable String sessionID,HttpServletRequest request){

        HashMap response = orderService.getAllOrders(sessionID,request.getSession());
 
        return response;
    }
    
    @RequestMapping(method = RequestMethod.GET,value = {"/deleteOrder/{sessionID}/{orderID}",})
    public HashMap deleteOrder(@PathVariable() Long orderID,@PathVariable String sessionID,HttpServletRequest request){

        HashMap response = orderService.getAllOrders(sessionID,request.getSession());
 
        return response;
    }
    
     @RequestMapping(value = "/order")
    public List<TheOrder> allOrders(){
       return  orderService.listOrders();
    } 
    
    
    
}
