package rsframework.test;

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

import rsframework.model.OrderItem;
import rsframework.model.Product;
import rsframework.model.PurchaseOrder;
import rsframework.repo.ProductRepository;
import rsframework.repo.PurchaseOrderRepository;
import rsframework.application.WebMvcConfiguration;
import rsframework.controller.ProductController;
import rsframework.controller.PurchaseOrderController;
import rsframework.types.OrderItemType;
import rsframework.types.ProductType;
import rsframework.types.PurchaseOrderType;

import static org.mockito.Mockito.*;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceTestConfiguration.class, WebMvcConfiguration.class} )
@WebAppConfiguration
public class ControllerTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private PurchaseOrderRepository purchaseOrderRepository;

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
        productController.setProductDao(productRepository);
        purchaseOrderController.setPurchaseOrderDao(purchaseOrderRepository);

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

        when(productRepository.findOne(1L)).thenReturn(product);
        productController.getProduct(1L);
        verify(productRepository).findOne(1L);
        verifyZeroInteractions(productRepository);

        when(productRepository.findAll()).thenReturn(productList);
        productController.getCatalog();
        verify(productRepository).findAll();
        verifyZeroInteractions(productRepository);
    }

    @Test
    public void testPurchaseOrder() throws Exception {

        // make a purchase order list
        List<PurchaseOrder> purchaseOrderList = new ArrayList<>();
        purchaseOrderList.add(purchaseOrder);

        when(purchaseOrderRepository.findOne(1L)).thenReturn(purchaseOrder);
        purchaseOrderController.getPurchaseOrder(1L);
        verify(purchaseOrderRepository).findOne(1L);
        verifyNoMoreInteractions(purchaseOrderRepository);

        when(purchaseOrderRepository.findAll()).thenReturn(purchaseOrderList);
        purchaseOrderController.getPurchaseOrderList();
        verify(purchaseOrderRepository).findAll();
        verifyNoMoreInteractions(purchaseOrderRepository);

        // need to test create and update with rest
        // create the services create new objects 
    }

}
