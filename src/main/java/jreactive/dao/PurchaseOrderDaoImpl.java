package jreactive.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import jreactive.model.OrderItem;
import jreactive.model.OrderItem_;
import jreactive.model.PurchaseOrder;
import jreactive.model.PurchaseOrder_;

/**
 * PurchaseOrderDao Implementation
 * 
 * @author Karl Nicholas
 * @version 2017.04.02
 * 
 */
@Repository
public class PurchaseOrderDaoImpl implements PurchaseOrderDao {
    @PersistenceContext
    private EntityManager em;

	@Override
	public PurchaseOrder save(PurchaseOrder entity) {
		return em.merge(entity);
	}

	@Override
	public PurchaseOrder findOne(Long primaryKey) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<PurchaseOrder> q = cb.createQuery(PurchaseOrder.class);
		Root<PurchaseOrder> r = q.from(PurchaseOrder.class);
		Fetch<PurchaseOrder, OrderItem> orderItemList = r.fetch(PurchaseOrder_.orderItemList, JoinType.LEFT);
		orderItemList.fetch(OrderItem_.product, JoinType.LEFT);
		return em.createQuery(q.select(r).where(cb.equal(r.get(PurchaseOrder_.id), primaryKey))).getSingleResult();
	}

	
	@Override
	public Iterable<PurchaseOrder> findAll() {
		CriteriaQuery<PurchaseOrder> q = em.getCriteriaBuilder().createQuery(PurchaseOrder.class);
		return em.createQuery(q.select(q.from(PurchaseOrder.class))).getResultList();
	}

	@Override
	public Long count() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> q = cb.createQuery(Long.class);
		return em.createQuery(q.select(cb.count(q.from(PurchaseOrder.class)))).getSingleResult();
	}

	@Override
	public void delete(PurchaseOrder entity) {
		em.remove(entity);
	}

	@Override
	public boolean exists(Long primaryKey) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> q = cb.createQuery(Long.class);
		Root<PurchaseOrder> r = cb.createQuery(PurchaseOrder.class).from(PurchaseOrder.class);
		Path<Long> id = r.get(PurchaseOrder_.id);
		return em.createQuery(q.select(cb.count(id)).where(cb.equal(id, primaryKey))).getSingleResult().equals(1L);
	}
}