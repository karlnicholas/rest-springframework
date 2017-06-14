package jreactive.test;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;

import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.test.DeploymentContext;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.ServletDeploymentContext;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.web.context.ContextLoaderListener;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jreactive.application.ChallengeApplication;
import jreactive.types.OrderItemType;
import jreactive.types.PurchaseOrderType;

public class TestChallengeRest extends JerseyTest {

    @Override
    protected TestContainerFactory getTestContainerFactory() {
        return new GrizzlyWebTestContainerFactory();
    }

    @Override
    protected DeploymentContext configureDeployment(){
        return ServletDeploymentContext
            .forServlet(new ServletContainer(new ChallengeApplication()))
            .addListener(ContextLoaderListener.class)
            .contextParam("contextConfigLocation", "classpath:applicationContext.xml, classpath:testDataSourceContext.xml")
            .build();
    }
    
    @BeforeClass
    public static void setup() {
        RestAssured.port = 9998;
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    public void test() throws Exception {       
        get("/schema/getcatalog").then().statusCode( 200 );        
        get("/productservice/getcatalog").then().statusCode( 200 );        
        get("/productservice/getproduct/1").then().statusCode( 200 );        
        get("/purchaseorderservice/getpurchaseorderlist").then().statusCode( 200 );
        get("/purchaseorderservice/getpurchaseorder/1").then().statusCode( 200 );
        // do some testing.
        // do an insert
        PurchaseOrderType purchaseOrderType = when().get("/purchaseorderservice/getpurchaseorder/1").as(PurchaseOrderType.class);
        purchaseOrderType.setId(null);
        purchaseOrderType.setComment("Second Order.");
        OrderItemType orderItemType1 = purchaseOrderType.getOrderItemListType().getOrderItemType().remove(0);
        OrderItemType orderItemType2 = purchaseOrderType.getOrderItemListType().getOrderItemType().remove(0);

        given().
                contentType(ContentType.JSON).
                body(purchaseOrderType).
        when().
                post("/purchaseorderservice/addpurchaseorder").
        then().
                statusCode(200);

        // compare new PO with original.
        PurchaseOrderType poResponse = when().get("/purchaseorderservice/getpurchaseorder/2").as(PurchaseOrderType.class);
        purchaseOrderType.setId(2L);

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
                put("/purchaseorderservice/updatepurchaseorder").
        then().
                statusCode(200);

        // compare response with original.
        PurchaseOrderType poResponse2 = when().get("/purchaseorderservice/getpurchaseorder/2").as(PurchaseOrderType.class);
        orderItemType2.setId(3L);
        orderItemType1.setId(4L);
        assertEquals(
                "Compare JSON response with original", 
                objectMapper.writeValueAsString(poResponse), 
                objectMapper.writeValueAsString(poResponse2)
            );
  }  

}