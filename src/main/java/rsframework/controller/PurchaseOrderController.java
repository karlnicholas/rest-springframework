package rsframework.controller;

import rsframework.types.OrderItemType;
import rsframework.types.PurchaseOrderListType;
import rsframework.types.PurchaseOrderType;
import rsframework.dao.PurchaseOrderDao;
import rsframework.model.OrderItem;
import rsframework.model.PurchaseOrder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * PurchaseOrderService Implementation
 * @author Karl Nicholas
 * @version 2017.04.02
 */
@RestController
public class PurchaseOrderController  {

    @Autowired
    private PurchaseOrderDao purchaseOrderDao;
 
    public void setPurchaseOrderDao(PurchaseOrderDao purchaseOrderDao) {
        this.purchaseOrderDao = purchaseOrderDao;
    }

    // Basic operations for PurchaseOrder Service
 
    /**
     * REST endpoint for getPurchaseOrderList
     * /rest/purchaseorderservice/getpurchaseorderlist
     * @return {@link PurchaseOrderListType}
     */
    @RequestMapping(method=RequestMethod.GET, path="getpurchaseorderlist", produces="application/json")
    public PurchaseOrderListType getPurchaseOrderList() throws Exception {
        PurchaseOrderListType purchaseOrderListType = new PurchaseOrderListType();
        List<PurchaseOrder> listPurchaseOrders = new ArrayList<>(); 
        purchaseOrderDao.findAll().forEach(listPurchaseOrders::add);
        for(PurchaseOrder purchaseOrder : listPurchaseOrders){
            purchaseOrderListType.getPurchaseOrderType().add( purchaseOrder.asPurchaseOrderType(false) );
        }
        return purchaseOrderListType;
    }

    /**
     * REST endpoint for getPurchaseOrder
     * /rest/purchaseorderservice/getpurchaseorder/{id}
     * @return {@link PurchaseOrderType}
     */
    @RequestMapping(method=RequestMethod.GET, path="getpurchaseorder/{id}", produces="application/json")
    public PurchaseOrderType getPurchaseOrder(@PathVariable Long id) throws Exception {
        // retrieve PurchaseOrder information based on the id supplied 
        PurchaseOrder purchaseOrder = purchaseOrderDao.findOne(id);            
        if ( purchaseOrder == null ) throw new IllegalArgumentException("PurchaseOrder not found for id: " + id);
        PurchaseOrderType purchaseOrderType = purchaseOrder.asPurchaseOrderType(true);
        return purchaseOrderType;
    }
 
    /**
     * REST endpoint for addPurchaseOrder
     * /rest/purchaseorderservice/addpurchaseorder
     * @return {@link String}
     */
    @RequestMapping(
    		method=RequestMethod.POST, 
    		path="addpurchaseorder", 
    		consumes="application/json", 
    		produces="text/pain"
		)
    public String createPurchaseOrder(@RequestBody PurchaseOrderType purchaseOrderType) throws Exception {
        // PurchaseOrder from PurchaseOrderType 
        purchaseOrderDao.save(
                new PurchaseOrder().fromPurchaseOrderType(purchaseOrderType)
            );
        return "SUCCESS";
    }
 
    /**
     * REST endpoint for updatepurchaseorder
     * /rest/purchaseorderservice/updatepurchaseorder
     * @return {@link String}
     */
    @RequestMapping(
    		method=RequestMethod.PUT, 
    		path="updatepurchaseorder", 
    		consumes="application/json", 
    		produces="text/pain"
		)
    public String updatePurchaseOrder(@RequestBody PurchaseOrderType purchaseOrderType) throws Exception {        
        // Find PurchaseOrder in the database 
        PurchaseOrder modifyPurchaseOrder = purchaseOrderDao.findOne(purchaseOrderType.getId());
        if ( modifyPurchaseOrder == null ) 
            throw new IllegalArgumentException("PurchaseOrder not found for id: " + purchaseOrderType.getId());
//      modifyPurchaseOrder.getOrderItemList().clear();
        if ( modifyPurchaseOrder.getOrderItemList() != null ) {
        	Iterator<OrderItem> oiit = modifyPurchaseOrder.getOrderItemList().iterator();
        	while( oiit.hasNext() ) {
        		OrderItem orderItem = oiit.next();
            	boolean notfound = true;
            	int index = -1;
                for ( OrderItemType orderItemType : purchaseOrderType.getOrderItemListType().getOrderItemType() ) {
                	if ( orderItem.getId().equals(orderItemType.getId()) ) {
                		notfound = false;
                		index = purchaseOrderType.getOrderItemListType().getOrderItemType().indexOf(orderItemType);
                		break;
                	}
                }
                if ( notfound ) {
                	oiit.remove();
                } else {
                	purchaseOrderType.getOrderItemListType().getOrderItemType().remove(index);
                }
            }
        }
        modifyPurchaseOrder.fromPurchaseOrderType(purchaseOrderType);     
        // update PurchaseOrder info and return SUCCESS message
        purchaseOrderDao.save(modifyPurchaseOrder);
        return "SUCCESS";
    }

}