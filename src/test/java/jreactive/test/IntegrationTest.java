package jreactive.test;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;
import static io.restassured.module.mockmvc.matcher.RestAssuredMockMvcMatchers.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import jreactive.application.WebMvcConfiguration;
import jreactive.controller.PurchaseOrderController;
import jreactive.dao.ProductDao;
import jreactive.dao.PurchaseOrderDao;
import jreactive.types.OrderItemType;
import jreactive.types.PurchaseOrderType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceTestConfiguration.class, WebMvcConfiguration.class} )
@WebAppConfiguration
public class IntegrationTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ProductDao productDao;
    @Autowired
    private PurchaseOrderDao purchaseOrderDao;

    @Before public void
    rest_assured_is_initialized_with_the_web_application_context_before_each_test() {
        RestAssuredMockMvc.webAppContextSetup(context);
    }

    @After public void
    rest_assured_is_reset_after_each_test() {
        RestAssuredMockMvc.reset();
    }

    @Test
    public void test() throws Exception {
/*    	
    	RestAssuredMockMvc.standaloneSetup(new SchemaService());
    	get("/schema/getcatalog").then().statusCode( 200 );        
    	get("/schema/getproduct").then().statusCode( 200 );        
    	get("/schema/getpurchaseorderlist").then().statusCode( 200 );        
    	get("/schema/getpurchaseorder").then().statusCode( 200 );        
    	RestAssuredMockMvc.reset();
//    	RestAssuredMockMvc.standaloneSetup(new ProductController());
    	ProductController productController = new ProductController();
    	productController.setProductDao(productDao);
    	RestAssuredMockMvc.standaloneSetup(productController);
        get("/getcatalog").then().statusCode( 200 );        
        get("/getproduct/1").then().statusCode( 200 );
        
        RestAssuredMockMvc.reset();
*/    	
        PurchaseOrderController purchaseOrderController = new PurchaseOrderController();
        purchaseOrderController.setPurchaseOrderDao(purchaseOrderDao);
//        RestAssuredMockMvc.webAppContextSetup(context);
//    	RestAssuredMockMvc.standaloneSetup(purchaseOrderController);
//        get("/getpurchaseorderlist").then().statusCode( 200 );
//        get("/getpurchaseorder/1").then().statusCode( 200 );
        // do some testing.
        // do an insert
        
        PurchaseOrderType purchaseOrderType = get("/getpurchaseorder/1").as(PurchaseOrderType.class);
        purchaseOrderType.setId(null);
        purchaseOrderType.setComment("Second Order.");
        OrderItemType orderItemType1 = purchaseOrderType.getOrderItemListType().getOrderItemType().remove(0);
        OrderItemType orderItemType2 = purchaseOrderType.getOrderItemListType().getOrderItemType().remove(0);

        given().
                contentType(ContentType.JSON).
                body(purchaseOrderType).
        when().
                post("/addpurchaseorder").
        then().
                statusCode(200);

        // compare new PO with original.
        PurchaseOrderType poResponse = get("/getpurchaseorder/3").as(PurchaseOrderType.class);
        purchaseOrderType.setId(3L);

        ObjectMapper objectMapper = new ObjectMapper();
        
        assertEquals(
                "Compare JSON response with original", 
                objectMapper.writeValueAsString(purchaseOrderType), 
                objectMapper.writeValueAsString(poResponse)
            );
        
        // do an update
        poResponse.setComment("Second Order (Modified).");
        orderItemType2.setId(null);
        poResponse.getOrderItemListType().getOrderItemType().add(orderItemType2);
        orderItemType1.setId(null);
        poResponse.getOrderItemListType().getOrderItemType().add(orderItemType1);

        given().
                contentType(ContentType.JSON).
                body(poResponse).
        when().
                put("/updatepurchaseorder").
        then().
                statusCode(200);

        // compare response with original.
        PurchaseOrderType poResponse2 = when().get("/getpurchaseorder/3").as(PurchaseOrderType.class);
        orderItemType2.setId(3L);
        orderItemType1.setId(4L);
        assertEquals(
                "Compare JSON response with original", 
                objectMapper.writeValueAsString(poResponse), 
                objectMapper.writeValueAsString(poResponse2)
            );
  }  

}