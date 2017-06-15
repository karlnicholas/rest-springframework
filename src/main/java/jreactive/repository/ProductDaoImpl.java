package jreactive.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jreactive.model.Product;
import jreactive.model.Product_;

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
		CriteriaQuery<Product> q = em.getCriteriaBuilder().createQuery(Product.class);
		return em.createQuery(q.select(q.from(Product.class))).getResultList();
	}

	@Override
	public Long count() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> q = cb.createQuery(Long.class);		
		return em.createQuery(q.select(cb.count(q.from(Product.class)))).getSingleResult();
	}

	@Override
	public void delete(Product entity) {
		em.remove(entity);
	}

	@Override
	public boolean exists(Long primaryKey) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> q = cb.createQuery(Long.class);
		Root<Product> r = q.from(Product.class);
		Path<Long> id = r.get(Product_.id);
		return em.createQuery(q.select(cb.count(id)).where(cb.equal(id, primaryKey))).getSingleResult().equals(1L);
	}    
    
}