 
package com.pnp.pnp.repositories;

import com.pnp.pnp.entity.Product;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.repository.CrudRepository;



public interface ProductRepo extends CrudRepository<Product, Long>{
    
    public Product findById(Long id);
    public Product findByName(String name);
    public ArrayList<Product> findByCategory(String category);
}
