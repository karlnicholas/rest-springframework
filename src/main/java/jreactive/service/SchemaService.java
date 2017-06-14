package jreactive.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;

import jreactive.types.ProductListType;
import jreactive.types.ProductType;
import jreactive.types.PurchaseOrderListType;
import jreactive.types.PurchaseOrderType;

/**
 * Service to provide JSON schemas for endpoints
 * @author Karl Nicholas
 * @version 2017.04.02
 *
 */
@Service
@Path("schema")
public class SchemaService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Rest endpoint for getCatalog schema
     * /rest/schema/getcatalog
     * @return {@link JsonSchema}
     */
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    @Path("getcatalog")
    public JsonSchema getCatalog() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            // configure mapper, if necessary, then create schema generator
            JsonSchemaGenerator schemaGen = new JsonSchemaGenerator(mapper);
            JsonSchema schema = schemaGen.generateSchema(ProductListType.class);
            return schema;
        } catch (JsonMappingException ex) {
            logger.error(ex.getLocalizedMessage());
            ResponseBuilder builder = Response.status(Response.Status.INTERNAL_SERVER_ERROR);
            builder.entity(ex.getMessage());
            Response response = builder.build();
            throw new WebApplicationException(response);
        }
    }

    /**
     * REST endpoint for getProduct schema
     * /rest/schema/getproduct
     * @return {@link JsonSchema}
     */
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    @Path("getproduct")
    public JsonSchema getProduct() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            // configure mapper, if necessary, then create schema generator
            JsonSchemaGenerator schemaGen = new JsonSchemaGenerator(mapper);
            JsonSchema schema = schemaGen.generateSchema(ProductType.class);
            return schema;
        } catch (JsonMappingException ex) {
            logger.error(ex.getLocalizedMessage());
            ResponseBuilder builder = Response.status(Response.Status.INTERNAL_SERVER_ERROR);
            builder.entity(ex.getMessage());
            Response response = builder.build();
            throw new WebApplicationException(response);
        }
    }
    /**
     * REST endpoint for getPurchaseOrder schema
     * /rest/schema/getpurchaseorder
     * @return {@link JsonSchema}
     */
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    @Path("getpurchaseorder")
    public JsonSchema getGetPurchaseOrder() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            // configure mapper, if necessary, then create schema generator
            JsonSchemaGenerator schemaGen = new JsonSchemaGenerator(mapper);
            JsonSchema schema = schemaGen.generateSchema(PurchaseOrderType.class);
            return schema;
        } catch (JsonMappingException ex) {
            logger.error(ex.getLocalizedMessage());
            ResponseBuilder builder = Response.status(Response.Status.INTERNAL_SERVER_ERROR);
            builder.entity(ex.getMessage());
            Response response = builder.build();
            throw new WebApplicationException(response);
        }
    }
    /**
     * REST endpoint for getPurchaseOrderList schema
     * /rest/schema/getpurchaseorderlist
     * @return {@link JsonSchema}
     */
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    @Path("getpurchaseorderlist")
    public JsonSchema getGetPurchaseOrderList() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            // configure mapper, if necessary, then create schema generator
            JsonSchemaGenerator schemaGen = new JsonSchemaGenerator(mapper);
            JsonSchema schema = schemaGen.generateSchema(PurchaseOrderListType.class);
            return schema;
        } catch (JsonMappingException ex) {
            logger.error(ex.getLocalizedMessage());
            ResponseBuilder builder = Response.status(Response.Status.INTERNAL_SERVER_ERROR);
            builder.entity(ex.getMessage());
            Response response = builder.build();
            throw new WebApplicationException(response);
        }
    }
}