/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pnp.pnp.services;

import com.pnp.pnp.repositories.OrderRepo;
import com.pnp.pnp.repositories.ProductRepo;

import com.pnp.pnp.entity.TheOrder;
import com.pnp.pnp.entity.OrderedProduct;

import com.pnp.pnp.entity.Product;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepo OrderRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepo productRepository;
    // @Autowired
    // private EmailService emailService;

    public HashMap processOrder(String orderData, HttpSession session) {

        HashMap response = new HashMap();

        JSONObject jObj = new JSONObject(orderData);

        System.out.println(orderData);
        JSONArray jArray = (JSONArray) jObj.get("orderItems");
        //  JSONObject destinationObj = (JSONObject) jObj.get("destinationInfo");
//        Address address = (Address) jObj.get("addressInfo");
        JSONObject customerData = new JSONObject(jObj.getString("user"));

        Long customerID = customerData.getLong("id");

        String customerNAME = customerData.getString("firstname");

        String customerCELLPHONE = customerData.getString("cellphonNumber");

        String customerADDRESS = customerData.getString("address");

        String sessionID = (String) jObj.get("sessionID");
        
        

        //  JSONObject addressObj = (JSONObject) destinationObj.get("addressInfo");
        /// String houseNumber = addressObj.getString("houseNumber");
        // String streetName = addressObj.getString("streetName");
        // String surburb = addressObj.getString("surburb");
        // String city = addressObj.getString("city");
        //  String postalCode = addressObj.getString("postalCode");
        //  String province = addressObj.getString("province");
        // Address address = new Address(houseNumber,streetName,surburb,city,postalCode,province);
        List<OrderedProduct> orderedproducts = createLineProduct(jArray);

        // double shippingCost = destinationObj.getDouble("cost");
        // OrderDestination destination = new OrderDestination(shippingCost, address);
        //Create timestamp for the order
        Date date = new Date(System.currentTimeMillis());
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        //Create order date
        String orderDate = cal.get(Calendar.DAY_OF_MONTH) + "/" + (cal.get(Calendar.MONTH) + 1) + "/"
                + cal.get(Calendar.YEAR) + " <<>> " + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE);

        TheOrder clientOrder = new TheOrder(customerID, customerNAME, orderDate, customerCELLPHONE, customerADDRESS, orderedproducts);

        String url = "/";
        String message = "";
        Long OrderNumber = null;

        //Persist customer order
        if (sessionID.equals(session.getId())) {
            OrderRepository.save(clientOrder);

            ArrayList<Product> allProducts = productService.getAllProducts();

            updateQuantity(allProducts, orderedproducts);

            //Get all specific customer orders
            List<TheOrder> orders = OrderRepository.findByUserID(customerID);

            //Get customer's latest order number
            OrderNumber = orders.get(orders.size() - 1).getId();

            response.put("status", HttpStatus.CREATED);

        } else {

            response.put("HttpStatus", HttpStatus.CONFLICT);
            message = "It looks like you did not login";
            url = "/login";

        }

        //Send Email to the customer
        if (OrderNumber != null) {

            message = "Hello again :" + customerNAME + " Your order is successfully placed.We deliver orders after 24 hours.Your order will be delivered after 24 hours to address: " + customerADDRESS;

        }

        System.out.println(message);

        response.put("message", message);
        response.put("url", url);

        return response;
    }

    private List<OrderedProduct> createLineProduct(JSONArray jArray) {

        List<OrderedProduct> orderedproducts = new ArrayList<>();

        for (int i = 0; i < jArray.length(); i++) {
            JSONObject obj = (JSONObject) jArray.get(i);

            int productID = obj.getInt("id");
            int quantity = obj.getInt("id");
            String name = obj.getString("name");
            double price = obj.getDouble("price");
            String image = obj.getString("image");

            OrderedProduct product = new OrderedProduct(productID, quantity, name, price, image);
            orderedproducts.add(product);
        }

        return orderedproducts;
    }

    public HashMap getAllOrders(String sessionID, HttpSession session) {

        HashMap response = new HashMap();

        List<TheOrder> allOrders = getAllOrders();

        if (sessionID.equals(session.getId())) {

            response.put("allOrders", allOrders);

        }

        return response;
    }

    private List<TheOrder> getAllOrders() {
        Iterable<TheOrder> tempAllUser = OrderRepository.findAll();
        List<TheOrder> allOrders = new ArrayList<>();

        tempAllUser.forEach(allOrders::add);

        return allOrders;
    }

    private void updateQuantity(ArrayList<Product> allProducts, List<OrderedProduct> orderedproducts) {

        for (int i = 0; i < allProducts.size(); i++) {
            Product product = allProducts.get(i);

            for (int j = 0; j < orderedproducts.size(); j++) {
                OrderedProduct lineProduct = orderedproducts.get(j);

                if (product.getId() == lineProduct.getProductId()) {
                    int remainingQuantity = product.getQuantity() - lineProduct.getQuantity();
                    product.setQuantity(remainingQuantity);

                    productRepository.save(product);
                }
            }

        }
    }

    public List<TheOrder> listOrders() {
        ArrayList<TheOrder> list = new ArrayList<>();
        OrderRepository.findAll().forEach(list::add);
        return list;
    }

}
