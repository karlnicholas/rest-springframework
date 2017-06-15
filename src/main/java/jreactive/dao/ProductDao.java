package jreactive.dao;

import jreactive.model.Product;

/**
 * Product Repository interface - emulates Spring Data
 * 
 * @author Karl Nicholas.
 * @version 2017.04.02
 *
 */
public interface ProductDao {
	Product save(Product entity); 
    Product findOne(Long primaryKey);       
    Iterable<Product> findAll();          
    Long count();                   
    void delete(Product entity);          
    boolean exists(Long primaryKey);  
}