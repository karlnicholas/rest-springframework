package jreactive.dao;

import java.util.List;

import jreactive.model.PurchaseOrder;

/**
 * PurchaseOrderDao Interface
 * 
 * @author Karl Nicholas
 * @version 2017.04.02
 * 
 */
public interface PurchaseOrderDao {
 
    public String insertNewPurchaseOrder(PurchaseOrder PurchaseOrder);
    public String updatePurchaseOrder(PurchaseOrder PurchaseOrder);
    public List<PurchaseOrder> getPurchaseOrderList();
    public PurchaseOrder getPurchaseOrder(Long PurchaseOrderId);
}