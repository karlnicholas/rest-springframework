package jreactive.service;

import jreactive.types.ProductListType;
import jreactive.types.ProductType;
import jreactive.dao.ProductDao;
import jreactive.model.Product;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ProductService Implementation
 * 
 * @author Karl Nicholas
 * @version 2017.04.02
 * 
 */
@Service
@Path("/productservice")
public class ProductServiceImpl implements ProductService {
 
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
    @GET
    @Path("getproduct/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public ProductType getProduct(@PathParam("id") Long id) throws Exception {
        // retrieve product information based on the id supplied in the formal argument
        Product getProduct = productDao.getProduct(id);
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
    @GET
    @Path("getcatalog")
    @Produces({MediaType.APPLICATION_JSON})
    public ProductListType getCatalog() throws Exception {
        List<Product> listProducts = productDao.getCatalog();
 
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