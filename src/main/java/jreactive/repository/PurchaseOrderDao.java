package jreactive.repository;

import jreactive.model.PurchaseOrder;

/**
 * PurchaseOrderDao Interface
 * 
 * @author Karl Nicholas
 * @version 2017.04.02
 * 
 */
public interface PurchaseOrderDao {
 
	PurchaseOrder save(PurchaseOrder entity); 
	PurchaseOrder update(PurchaseOrder entity); 
	PurchaseOrder findOne(Long primaryKey);       
    Iterable<PurchaseOrder> findAll();          
    Long count();                   
    void delete(PurchaseOrder entity);          
    boolean exists(Long primaryKey);  
}