package jreactive.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import jreactive.model.PurchaseOrder;

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PurchaseOrder update(PurchaseOrder entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PurchaseOrder findOne(Long primaryKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<PurchaseOrder> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long count() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(PurchaseOrder entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean exists(Long primaryKey) {
		// TODO Auto-generated method stub
		return false;
	}
}