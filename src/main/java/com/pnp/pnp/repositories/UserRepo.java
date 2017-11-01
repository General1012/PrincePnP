/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pnp.pnp.repositories;


import com.pnp.pnp.entity.Customer;
import com.pnp.pnp.entity.User;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Long>{
      public List<User> findByUserRole(String role);
      public User findByEmail(String email);
  
    //public void updateUser(UserTB user);
}
