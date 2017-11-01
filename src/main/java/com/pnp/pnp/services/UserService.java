/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pnp.pnp.services;

import com.pnp.pnp.repositories.UserRepo;
import com.pnp.pnp.entity.Customer;
import com.pnp.pnp.entity.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    
    @Autowired
    private UserRepo userRepository;
   // @Autowired
    //private EmailService emailService;
    
    public HashMap registerCustomer(User user){
       
        
        List<User> allUsers = getAllUsers();
        
        HashMap response = new HashMap();
        boolean isUnique = true;
        String message = "";
        String url = "";
        
        for (int i = 0; i < allUsers.size(); i++) {
            User arrayUser = allUsers.get(i);
            
            if(arrayUser.getEmail().equals(user.getEmail())){
                isUnique = false;
            }
        }
        
        if (isUnique) {
            userRepository.save(user);
            message = "You are successfully registered";
            url = "/";
            response.put("HttpStatus", HttpStatus.CREATED);
            
        }else{
            message = "You are already registered, Please use your details to login.";
            url = "/login";
            response.put("HttpStatus", HttpStatus.CONFLICT);
        }
        
            response.put("message", message);
            response.put("url", url);
                   
       return response ;
    }
    
    public List<User> getAllUserByRole(String role){
        
        return userRepository.findByUserRole(role);
    }
    
    public HashMap login(User user,HttpSession session){
       
       List<User> allUsers = getAllUsers();
        
       String email = user.getEmail();
       String password = user.getPassword();
        
       String message = "";
       String url = "/login";
       HttpStatus status =  null;
       String sessionID = "";
       
       User userIn = null;
       
       HashMap response = new HashMap();
       
       
           for (int i = 0; i < allUsers.size(); i++) {
            User arrayUser = allUsers.get(i);
               
                if(arrayUser.getEmail().equals(email)){
                   
                    if(arrayUser.getPassword().equals(password)){
                        status = HttpStatus.FOUND;
                        message = "You have successfully logged in";
                        url = "/theproduct";
                        userIn = arrayUser;
                        sessionID = session.getId();
                        break;
                    }else{
                        status = HttpStatus.NOT_FOUND;
                        message = "Enter the correct password";
                    }
                    //break to outside of the loop if the email exist
                    i = allUsers.size()+1;
                }else{
                    
                    message = "Email address entered doesn't exist, Please enter the correct one.";
                }
    
            }

           response.put("HttpStatus",status);
           response.put("url", url);
           response.put("message", message);
           response.put("sessionID", sessionID);
           response.put("userIn", userIn);
           
        
        return response;
    }
     public List<User> listCustomers(){
        ArrayList<User> list = new ArrayList<>()  ;
          userRepository.findAll().forEach(list::add);
          return list;
       }
    
    private List<User> getAllUsers(){
        Iterable<User> tempAllUser = userRepository.findAll();
        List<User> allUsers = new ArrayList<>();
     
        tempAllUser.forEach(allUsers::add);
        
        return allUsers;
    }

    public HashMap updateProfile(String data, HttpSession session) {
       
        HashMap response = new HashMap();
        JSONObject jObject = new JSONObject(data);
  
        String sessionID = jObject.getString("sessionID");
        
        JSONObject newData = (JSONObject) jObject.get("newData");
        
        
        String securityQuestuion = newData.getString("securityQuestuion");
        String answer = newData.getString("answer");
        Long id = newData.getLong("id");
        String firstname = newData.getString("firstname");
        String lastname = newData.getString("lastname");
        String email  = newData.getString("email");
        String password = newData.getString("password");
        String userRole = newData.getString("userRole");
        String cellphonNumber = newData.getString("cellphonNumber");
        String gender = newData.getString("gender");
        String address = newData.getString("address");
        String dateOfBirth = newData.getString("dateOfBirth");

        User user = new Customer(securityQuestuion, answer,id, firstname, lastname, email, password, userRole, cellphonNumber, gender,address, dateOfBirth);
        
        String message = firstname+" "+lastname+" your details are not updated";
        HttpStatus status =  HttpStatus.NOT_FOUND;       

        System.out.println(sessionID);
        System.out.println(password);
        
            if(sessionID.equals(session.getId())){
               
                userRepository.save(user);
                status =  HttpStatus.FOUND;
                message = firstname+" "+lastname+" your profile is updated"; 
            }

           response.put("HttpStatus",status);
           response.put("userIn", user);
           response.put("message", message);
           response.put("sessionID", sessionID);
           
           return response;   
    }

    public HashMap getPassword(String email) {
 
        HashMap response = new HashMap();
        System.out.println( email);
        Customer customer = (Customer) userRepository.findByEmail(email);
        
        System.out.println(customer);
        
        response.put("status","OK");
        response.put("customer",customer);
        
        return response;
    }

    public HashMap completePasswordRetrival(String customerData) {
        
         HashMap response = new HashMap();
         
         JSONObject obj = new JSONObject(customerData);

         String answer = obj.getString("answer");
         String email = obj.getString("email");
         
         Customer customer = (Customer) userRepository.findByEmail(email);
         
         String message = "You have provided the wrong answer, Please enter the correct one.";
         String status = "FAILED";
         
         if(answer.equals(customer.getAnswer())){
             
             //send an email to customer
             
             status = "OK";
             message = "An email was sent to you with your password";   
         }
         
         response.put("status",status);
         response.put("message",message);
        
        return response;
    }

    public HashMap getQuestion(String email){
        
        HashMap response = new HashMap();
        
        Customer customer = (Customer) userRepository.findByEmail(email);
        
        String question = customer.getSecurityQuestuion();
        
        response.put("question",question);
        
        return response;
    }
    
 
}
