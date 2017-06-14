
package jreactive.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import jreactive.dao.ProductDao;
import jreactive.dao.PurchaseOrderDao;
import jreactive.model.OrderItem;
import jreactive.model.Product;
import jreactive.model.PurchaseOrder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:testDataSourceContext.xml"})
public class TestChallengeDaos
{
     
    @Autowired
    private ProductDao productDao;
     
    @Autowired
    private PurchaseOrderDao purchaseOrderDao;
     
    @Test
    public void testGetCatalog()
    {
        List<Product> catalog = productDao.getCatalog();
        assertEquals(4, catalog.size());
    }
     
    @Test
    public void testGetProduct()
    {
        Product product = productDao.getProduct(1L);
        assertEquals("TEST ID", new Long(1), product.getId() );
        assertEquals("TEST SKU", "111-AA", product.getSku() );
        assertEquals("TEST NAME", "Widget", product.getName() );
        assertEquals("TEST DESCRIPTION", "Cool Widget", product.getDescription() );
        assertEquals("TEST CATEGORY", "Widget", product.getCategory());
    }

    @Test
    public void testGetPurchaseOrderList()
    {
        List<PurchaseOrder> purchaseOrderList = purchaseOrderDao.getPurchaseOrderList();
        assertEquals("TEST PURCHASEORDERLIST", 1, purchaseOrderList.size());
    }

    @Test
    public void testGetPurchaseOrder()
    {
        PurchaseOrder purchaseOrder = purchaseOrderDao.getPurchaseOrder(1L);
        assertEquals("TEST ID", new Long(1), purchaseOrder.getId() );
        assertEquals("TEST COMMENT", "First Order, Yea!", purchaseOrder.getComment() );
        assertEquals("TEST ORDERLIST SIZE", 2, purchaseOrder.getOrderItemList().size() );
        try {
            assertEquals("TEST ORDERDATE", 
                new SimpleDateFormat("yyyy-MM-dd").parse("2017-03-31"), 
                purchaseOrder.getOrderDate() 
            );
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testTnsertAndUpdatePurchaseOrder()
    {
        assertNull("TEST NO ID 2" , purchaseOrderDao.getPurchaseOrder(2L));
        PurchaseOrder newPurchaseOrder = new PurchaseOrder();
        newPurchaseOrder.setComment("Second Order");
        
        OrderItem newOrderItem1 = new OrderItem();
        newOrderItem1.setItemPrice(new BigDecimal(1.99));
        newOrderItem1.setQuantity(1);
        newOrderItem1.setProductId(1L);        
        newPurchaseOrder.getOrderItemList().add(newOrderItem1);

        OrderItem newOrderItem2 = new OrderItem();
        newOrderItem2.setItemPrice(new BigDecimal(2.99));
        newOrderItem2.setQuantity(1);
        newOrderItem2.setProductId(2L);        
        newPurchaseOrder.getOrderItemList().add(newOrderItem2);

        newPurchaseOrder.setComment("Second Order");
        try {
            newPurchaseOrder.setOrderDate(new SimpleDateFormat("yyyy-MM-dd").parse("2017-03-31"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        assertEquals("TEST INSERT PURCHASE ORDER", 
                "PurchaseOrder information saved successfully with ID 2", 
                purchaseOrderDao.insertNewPurchaseOrder(newPurchaseOrder) );
        
        PurchaseOrder updatePurchaseOrder = purchaseOrderDao.getPurchaseOrder(2L);
        assertNotNull("TEST UPDATE PURCHASE ORDER", updatePurchaseOrder);
        updatePurchaseOrder.setComment(updatePurchaseOrder.getComment() + "Modified");
        updatePurchaseOrder.getOrderItemList().remove(1);
        assertEquals("TEST INSERT PURCHASE ORDER", 
                "PurchaseOrder information updated successfully", 
                purchaseOrderDao.updatePurchaseOrder(updatePurchaseOrder) );

        assertNull("TEST NO ID 3" , purchaseOrderDao.getPurchaseOrder(3L));

        PurchaseOrder purchaseOrder = purchaseOrderDao.getPurchaseOrder(2L);
        assertEquals("TEST ID", new Long(2), purchaseOrder.getId() );
        assertEquals("TEST COMMENT", "Second Order" + "Modified", purchaseOrder.getComment() );
        assertEquals("TEST ORDERLIST SIZE", 1, purchaseOrder.getOrderItemList().size() );
        try {
            assertEquals("TEST ORDERDATE", 
                new SimpleDateFormat("yyyy-MM-dd").parse("2017-03-31"), 
                purchaseOrder.getOrderDate() 
            );
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        
    }
}
