package jreactive.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import jreactive.model.PurchaseOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * PurchaseOrderDao Implementation
 * 
 * @author Karl Nicholas
 * @version 2017.04.02
 * 
 */
@Repository("PurchaseOrderDao")
public class PurchaseOrderDaoImpl implements PurchaseOrderDao {
    public static final String INSERT_SUCCESS = "PurchaseOrder information saved successfully with ID ";
    public static final String UPDATE_SUCCESS = "PurchaseOrder information updated successfully";
    @Autowired
    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Insert a new PurchaseOrder
     * @param purchaseOrder {@link PurchaseOrder }
     */
    @Override
    @Transactional
    public String insertNewPurchaseOrder(PurchaseOrder purchaseOrder) {
        // inserts into database & return Id (primary_key)
        Long Id = (Long) sessionFactory.getCurrentSession().save(purchaseOrder);
        return INSERT_SUCCESS + Id;
    }

    /**
     * Get a PurchaseOrder by id
     * @param id {@link Long}
     * @return {@link PurchaseOrder}
     */
    @Override
    @Transactional(readOnly = true)
    public PurchaseOrder getPurchaseOrder(final Long id) {
        PurchaseOrder purchaseOrder = (PurchaseOrder) sessionFactory.getCurrentSession().get(PurchaseOrder.class, id);
/*        
        if ( purchaseOrder != null ) {
            for ( OrderItem orderItem: purchaseOrder.getOrderItemList() ) {
                // handle lazy loading.
                Hibernate.initialize(orderItem.getProduct());
            }
        }
*/        
        return purchaseOrder;
    }

    /**
     * Update a PurchaseOrder.
     * @param updatePurchaseOrder {@link PurchaseOrder}
     * @return {@link String} 
     */
    @Override
    @Transactional
    public String updatePurchaseOrder(PurchaseOrder updatePurchaseOrder) {
        // update database with PurchaseOrder information and return success message
        sessionFactory.getCurrentSession().update(updatePurchaseOrder);
        return UPDATE_SUCCESS;
    }

    /**
     * Get a list of PurchaseOrders, no dependencies.
     * @return {@link List}
     */
    @SuppressWarnings("unchecked")
    @Override
    @Transactional(readOnly = true)
    public List<PurchaseOrder> getPurchaseOrderList() {
        // get all PurchaseOrders info from database
        List<PurchaseOrder> listPurchaseOrder = sessionFactory.getCurrentSession().createCriteria(PurchaseOrder.class).list();
        return listPurchaseOrder;
    }
}