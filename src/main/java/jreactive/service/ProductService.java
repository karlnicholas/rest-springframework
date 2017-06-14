package jreactive.service;

import jreactive.types.ProductListType;
import jreactive.types.ProductType;

/**
 * ProductService interface
 * 
 * @author Karl Nicholas
 * @version 2017.04.02
 * 
 */
public interface ProductService {
 
    public ProductType getProduct(Long productId) throws Exception;
    public ProductListType getCatalog() throws Exception;
}