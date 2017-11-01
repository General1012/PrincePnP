/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pnp.pnp.repositories;

import com.pnp.pnp.entity.TheOrder;
import java.util.List;
import org.springframework.data.repository.CrudRepository;


/**
 *
 * @author MANDELACOMP3
 */
public interface OrderRepo extends CrudRepository<TheOrder, Long>{
    
    public List<TheOrder> findByUserID(Long id);
}
