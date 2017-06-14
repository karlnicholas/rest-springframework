package jreactive.service;

import jreactive.types.PurchaseOrderListType;
import jreactive.types.PurchaseOrderType;
import jreactive.dao.PurchaseOrderDao;
import jreactive.model.PurchaseOrder;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * PurchaseOrderService Implementation
 * @author Karl Nicholas
 * @version 2017.04.02
 */
@Service
@Path("/purchaseorderservice")
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

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
    @GET
    @Path("getpurchaseorderlist")
    @Produces({MediaType.APPLICATION_JSON})
    public PurchaseOrderListType getPurchaseOrderList() throws Exception {
        PurchaseOrderListType purchaseOrderListType = new PurchaseOrderListType();
        List<PurchaseOrder> listPurchaseOrders = purchaseOrderDao.getPurchaseOrderList();
        for(PurchaseOrder purchaseOrder : listPurchaseOrders){
            purchaseOrderListType.getPurchaseOrderType().add( purchaseOrder.asPurchaseOrderType() );
        }
        return purchaseOrderListType;
    }

    /**
     * REST endpoint for getPurchaseOrder
     * /rest/purchaseorderservice/getpurchaseorder/{id}
     * @return {@link PurchaseOrderType}
     */
    @GET
    @Path("getpurchaseorder/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public PurchaseOrderType getPurchaseOrder(@PathParam("id") Long id) throws Exception {
        // retrieve PurchaseOrder information based on the id supplied 
        PurchaseOrder purchaseOrder = purchaseOrderDao.getPurchaseOrder(id);            
        if ( purchaseOrder == null ) throw new IllegalArgumentException("PurchaseOrder not found for id: " + id);
        PurchaseOrderType purchaseOrderType = purchaseOrder.asPurchaseOrderType();
        return purchaseOrderType;
    }
 
    /**
     * REST endpoint for addPurchaseOrder
     * /rest/purchaseorderservice/addpurchaseorder
     * @return {@link String}
     */
    @POST
    @Path("addpurchaseorder")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces(MediaType.TEXT_PLAIN)
    public String createPurchaseOrder(PurchaseOrderType purchaseOrderType) throws Exception {
        // PurchaseOrder from PurchaseOrderType 
        return purchaseOrderDao.insertNewPurchaseOrder(
                new PurchaseOrder().fromPurchaseOrderType(purchaseOrderType)
            );
    }
 
    /**
     * REST endpoint for updatepurchaseorder
     * /rest/purchaseorderservice/updatepurchaseorder
     * @return {@link String}
     */
    @PUT
    @Path("updatepurchaseorder")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces(MediaType.TEXT_PLAIN)
    public String updatePurchaseOrder(PurchaseOrderType purchaseOrderType) throws Exception {        
        // Find PurchaseOrder in the database 
        PurchaseOrder modifyPurchaseOrder = purchaseOrderDao.getPurchaseOrder(purchaseOrderType.getId());
        if ( modifyPurchaseOrder == null ) 
            throw new IllegalArgumentException("PurchaseOrder not found for id: " + purchaseOrderType.getId());
        modifyPurchaseOrder.getOrderItemList().clear();
        modifyPurchaseOrder.fromPurchaseOrderType(purchaseOrderType);     
        // update PurchaseOrder info and return SUCCESS message
        return purchaseOrderDao.updatePurchaseOrder(modifyPurchaseOrder);
    }

}