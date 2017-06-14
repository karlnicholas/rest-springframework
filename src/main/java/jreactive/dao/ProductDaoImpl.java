package jreactive.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import jreactive.model.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * ProductDao Implementation
 * @author Karl Nicholas
 * @version 2017.04.02
 * 
 */
@Repository("ProductDao")
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     *  Read a specific Product by Id 
     */
    @Override
    @Transactional(readOnly = true)
    public Product getProduct(Long Id) {
        // retrieve Product object based on the id supplied in the formal
        // argument
        Product Product = (Product) sessionFactory.getCurrentSession().get(Product.class, Id);
        return Product;
    }

    /**
     * Get list of Products.
     * @return product @{link List<Product>} 
     */
    @SuppressWarnings("unchecked")
    @Override
    @Transactional(readOnly = true)
    public List<Product> getCatalog() {
        // get all Products info from database
        List<Product> lstProduct = sessionFactory.getCurrentSession().createCriteria(Product.class).list();
        return lstProduct;
    }
}