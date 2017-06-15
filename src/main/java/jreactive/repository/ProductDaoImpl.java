package jreactive.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jreactive.model.Product;

/**
 * ProductDao Implementation
 * @author Karl Nicholas
 * @version 2017.04.02
 * 
 */
@Repository
public class ProductDaoImpl implements ProductDao {

    @PersistenceContext
    private EntityManager em;

	@Override
	@Transactional
	public Product save(Product entity) {
		em.persist(entity);
		return entity;
	}

	@Override
	public Product findOne(Long primaryKey) {
		return em.find(Product.class, primaryKey);
	}

	@Override
	public Iterable<Product> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long count() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Product entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean exists(Long primaryKey) {
		return em.createQuery("select count(*) from Product where id = :id", Long.class).setParameter("id", primaryKey).getSingleResult().equals(1L);
	}    
    
}