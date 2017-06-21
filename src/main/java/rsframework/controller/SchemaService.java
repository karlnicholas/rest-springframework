package rsframework.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;

import rsframework.types.ProductListType;
import rsframework.types.ProductType;
import rsframework.types.PurchaseOrderListType;
import rsframework.types.PurchaseOrderType;

/**
 * Service to provide JSON schemas for endpoints
 * @author Karl Nicholas
 * @version 2017.04.02
 *
 */
@RestController
@RequestMapping("schema")
public class SchemaService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Rest endpoint for getCatalog schema
     * /rest/schema/getcatalog
     * @return {@link JsonSchema}
     */
    @RequestMapping(method=RequestMethod.GET, path="getcatalog", produces="application/json")
    public JsonSchema getCatalog() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            // configure mapper, if necessary, then create schema generator
            JsonSchemaGenerator schemaGen = new JsonSchemaGenerator(mapper);
            JsonSchema schema = schemaGen.generateSchema(ProductListType.class);
            return schema;
        } catch (JsonMappingException ex) {
            logger.error(ex.getLocalizedMessage());
            throw new RuntimeException(ex.getLocalizedMessage());
        }
    }

    /**
     * REST endpoint for getProduct schema
     * /rest/schema/getproduct
     * @return {@link JsonSchema}
     */
    @RequestMapping(method=RequestMethod.GET, path="getproduct", produces="application/json")
    public JsonSchema getProduct() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            // configure mapper, if necessary, then create schema generator
            JsonSchemaGenerator schemaGen = new JsonSchemaGenerator(mapper);
            JsonSchema schema = schemaGen.generateSchema(ProductType.class);
            return schema;
        } catch (JsonMappingException ex) {
            logger.error(ex.getLocalizedMessage());
            throw new RuntimeException(ex.getLocalizedMessage());
        }
    }
    /**
     * REST endpoint for getPurchaseOrder schema
     * /rest/schema/getpurchaseorder
     * @return {@link JsonSchema}
     */
    @RequestMapping(method=RequestMethod.GET, path="getpurchaseorder", produces="application/json")
    public JsonSchema getGetPurchaseOrder() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            // configure mapper, if necessary, then create schema generator
            JsonSchemaGenerator schemaGen = new JsonSchemaGenerator(mapper);
            JsonSchema schema = schemaGen.generateSchema(PurchaseOrderType.class);
            return schema;
        } catch (JsonMappingException ex) {
            logger.error(ex.getLocalizedMessage());
            throw new RuntimeException(ex.getLocalizedMessage());
        }
    }
    /**
     * REST endpoint for getPurchaseOrderList schema
     * /rest/schema/getpurchaseorderlist
     * @return {@link JsonSchema}
     */
    @RequestMapping(method=RequestMethod.GET, path="getpurchaseorderlist", produces="application/json")
    public JsonSchema getGetPurchaseOrderList() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            // configure mapper, if necessary, then create schema generator
            JsonSchemaGenerator schemaGen = new JsonSchemaGenerator(mapper);
            JsonSchema schema = schemaGen.generateSchema(PurchaseOrderListType.class);
            return schema;
        } catch (JsonMappingException ex) {
            logger.error(ex.getLocalizedMessage());
            throw new RuntimeException(ex.getLocalizedMessage());
        }
    }
}