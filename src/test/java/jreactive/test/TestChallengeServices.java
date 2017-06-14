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
import jreactive.service.ProductService;
import jreactive.service.ProductServiceImpl;
import jreactive.service.PurchaseOrderService;
import jreactive.service.PurchaseOrderServiceImpl;
import jreactive.types.OrderItemType;
import jreactive.types.ProductType;
import jreactive.types.PurchaseOrderType;

import static org.mockito.Mockito.*;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:testDataSourceContext.xml"})

public class TestChallengeServices {

    @Mock
    private ProductDao productDao;
    @Mock
    private PurchaseOrderDao purchaseOrderDao;

    @Autowired
    private ProductService productService;
    @Autowired
    private PurchaseOrderService purchaseOrderService;
    
    private Product product;
    private PurchaseOrder purchaseOrder;
    private ProductType productType;
    private PurchaseOrderType purchaseOrderType;
    private OrderItemType orderItemType;


    @Before
    public void setupMock() {
        MockitoAnnotations.initMocks(this);
        ((ProductServiceImpl)productService).setProductDao(productDao);
        ((PurchaseOrderServiceImpl)purchaseOrderService).setPurchaseOrderDao(purchaseOrderDao);

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
        
        OrderItem orderItem = new OrderItem();
        orderItem.setItemPrice(new BigDecimal(1.99));
        orderItem.setQuantity(1);
        orderItem.setProductId(product.getId());

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
        orderItemType.setProductIdType(productType.getId());
        
    }

    @Test
    public void testProduct() throws Exception {

        List<Product> productList = new ArrayList<>();
        productList.add(product);

        when(productDao.getProduct(1L)).thenReturn(product);
        productService.getProduct(1L);
        verify(productDao).getProduct(1L);
        verifyZeroInteractions(productDao);

        when(productDao.getCatalog()).thenReturn(productList);
        productService.getCatalog();
        verify(productDao).getCatalog();
        verifyZeroInteractions(productDao);
    }

    @Test
    public void testPurchaseOrder() throws Exception {

        // make a purchase order list
        List<PurchaseOrder> purchaseOrderList = new ArrayList<>();
        purchaseOrderList.add(purchaseOrder);

        when(purchaseOrderDao.getPurchaseOrder(1L)).thenReturn(purchaseOrder);
        purchaseOrderService.getPurchaseOrder(1L);
        verify(purchaseOrderDao).getPurchaseOrder(1L);
        verifyNoMoreInteractions(purchaseOrderDao);

        when(purchaseOrderDao.getPurchaseOrderList()).thenReturn(purchaseOrderList);
        purchaseOrderService.getPurchaseOrderList();
        verify(purchaseOrderDao).getPurchaseOrderList();
        verifyNoMoreInteractions(purchaseOrderDao);

        // need to test create and update with rest
        // create the services create new objects 
    }

}
