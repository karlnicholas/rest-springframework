package jreactive.test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import jreactive.dao.ProductDao;
import jreactive.dao.PurchaseOrderDao;
import jreactive.model.OrderItem;
import jreactive.model.Product;
import jreactive.model.PurchaseOrder;
import jreactive.application.WebMvcConfiguration;
import jreactive.controller.ProductController;
import jreactive.controller.PurchaseOrderController;
import jreactive.types.OrderItemType;
import jreactive.types.ProductType;
import jreactive.types.PurchaseOrderType;

import static org.mockito.Mockito.*;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceTestConfiguration.class, WebMvcConfiguration.class} )
@WebAppConfiguration
public class ControllerTest {

    @Mock
    private ProductDao productDao;
    @Mock
    private PurchaseOrderDao purchaseOrderDao;

    @Autowired
    private ProductController productController;
    @Autowired
    private PurchaseOrderController purchaseOrderController;
    
    private Product product;
    private PurchaseOrder purchaseOrder;
    private ProductType productType;
    private PurchaseOrderType purchaseOrderType;
    private OrderItemType orderItemType;


    @Before
    public void setupMock() {
        MockitoAnnotations.initMocks(this);
        productController.setProductDao(productDao);
        purchaseOrderController.setPurchaseOrderDao(purchaseOrderDao);

        product = new Product();
        product.setId(1L);
        product.setName("Widget");

        purchaseOrder = new PurchaseOrder();
        purchaseOrder.setId(1L);
        purchaseOrder.setComment("First Order");
        try {
            purchaseOrder.setOrderDate(new SimpleDateFormat("yyyy-MM-dd").parse("2017-03-31"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        purchaseOrder.setOrderItemList(new ArrayList<>());
        
        OrderItem orderItem = new OrderItem();
        orderItem.setItemPrice(new BigDecimal(1.99));
        orderItem.setQuantity(1);
        orderItem.setProduct(product);

        productType = new ProductType();
        productType.setId(1L);
        productType.setName("Widget");

        purchaseOrderType = new PurchaseOrderType();
        purchaseOrderType.setId(1L);
        purchaseOrderType.setComment("First Order");
        try {
            GregorianCalendar gregory = new GregorianCalendar();
            gregory.setTimeInMillis(purchaseOrder.getOrderDate().getTime());
            purchaseOrderType.setOrderDate(
                    DatatypeFactory.newInstance().newXMLGregorianCalendar(gregory));
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }
        
        orderItemType = new OrderItemType();
        orderItemType.setItemPrice(new BigDecimal(1.99));
        orderItemType.setQuantity(1);
        orderItemType.setProductType(productType);
        
    }

    @Test
    public void testProduct() throws Exception {

        List<Product> productList = new ArrayList<>();
        productList.add(product);

        when(productDao.findOne(1L)).thenReturn(product);
        productController.getProduct(1L);
        verify(productDao).findOne(1L);
        verifyZeroInteractions(productDao);

        when(productDao.findAll()).thenReturn(productList);
        productController.getCatalog();
        verify(productDao).findAll();
        verifyZeroInteractions(productDao);
    }

    @Test
    public void testPurchaseOrder() throws Exception {

        // make a purchase order list
        List<PurchaseOrder> purchaseOrderList = new ArrayList<>();
        purchaseOrderList.add(purchaseOrder);

        when(purchaseOrderDao.findOne(1L)).thenReturn(purchaseOrder);
        purchaseOrderController.getPurchaseOrder(1L);
        verify(purchaseOrderDao).findOne(1L);
        verifyNoMoreInteractions(purchaseOrderDao);

        when(purchaseOrderDao.findAll()).thenReturn(purchaseOrderList);
        purchaseOrderController.getPurchaseOrderList();
        verify(purchaseOrderDao).findAll();
        verifyNoMoreInteractions(purchaseOrderDao);

        // need to test create and update with rest
        // create the services create new objects 
    }

}
