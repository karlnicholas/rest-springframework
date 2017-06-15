package jreactive.controller;

import jreactive.types.ProductListType;
import jreactive.types.ProductType;
import jreactive.dao.ProductDao;
import jreactive.model.Product;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * ProductService Implementation
 * 
 * @author Karl Nicholas
 * @version 2017.04.02
 * 
 */

@RestController
public class ProductController {
 
    @Autowired
    private ProductDao productDao;

    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }

    /**
     * Rest endpoint for a ProductType
     * rest/productservice/getproduct/{id}
     * @param id @{link Long}
     * @return {@link ProductType}
     */
    @RequestMapping(method=RequestMethod.GET, path="getproduct/{id}", produces="application/json")
    public ProductType getProduct(@PathVariable Long id) throws Exception {
        // retrieve product information based on the id supplied in the formal argument
        Product getProduct = productDao.findOne(id);
        if ( getProduct == null )
            throw new IllegalArgumentException("Product not found for id: " + id);
        ProductType productType = getProduct.asProductType();
        return productType;
    }

    /**
     * Rest endpoint for Product Catalog
     * /rest/productservice/getcatalog
     * @return @{link ProductListType}
     */
    @RequestMapping(method=RequestMethod.GET, path="getcatalog", produces="application/json")
    public ProductListType getCatalog() throws Exception {
        List<Product> listProducts = new ArrayList<>();
        Iterable<Product> pit = productDao.findAll();
        pit.forEach(listProducts::add);
 
        // create a object of type ProductListType which takes Product objects in its list
        ProductListType productListType = new ProductListType();
 
        for(Product product : listProducts) {
            ProductType productType = product.asProductType();
            // add to ProductListType
            productListType.getProductType().add(productType);
        }
        return productListType;
    }

}