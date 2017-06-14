package jreactive.exceptionhandler;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class DefaultExceptionMapper implements ExceptionMapper<Exception> {
    private final Logger logger = LoggerFactory.getLogger(getClass()); 

    public Response toResponse(Exception ex) {
        logger.error("DefaultExceptionMapper", ex);
        return Response.status(404)
                .entity(ex.getMessage())
                .type("text/plain")
                .build();
    }
}
