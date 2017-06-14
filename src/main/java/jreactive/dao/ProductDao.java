package jreactive.dao;

import java.util.List;

import jreactive.model.Product;

/**
 * Product Dao interface
 * 
 * @author Karl Nicholas.
 * @version 2017.04.02
 *
 */
public interface ProductDao {
 
    public Product getProduct(Long productId);
    public List<Product> getCatalog();
}