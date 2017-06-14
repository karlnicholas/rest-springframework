package jreactive.service;

import jreactive.types.PurchaseOrderListType;
import jreactive.types.PurchaseOrderType;

/**
 * PurchaseOrderService Interface
 * @author Karl Nicholas
 * @version 2017.04.02
 */
public interface PurchaseOrderService {
 
    // Basic operations for PurchaseOrder Service
    public String createPurchaseOrder(PurchaseOrderType PurchaseOrderType) throws Exception;
    public String updatePurchaseOrder(PurchaseOrderType PurchaseOrderType) throws Exception;
    public PurchaseOrderListType getPurchaseOrderList() throws Exception;
    public PurchaseOrderType getPurchaseOrder(Long PurchaseOrderId) throws Exception;
}