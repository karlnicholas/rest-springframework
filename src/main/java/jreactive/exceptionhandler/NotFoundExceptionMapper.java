package jreactive.exceptionhandler;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {
    private final Logger logger = LoggerFactory.getLogger(getClass()); 
    public Response toResponse(NotFoundException ex) {
        logger.error(ex.getLocalizedMessage());
        return Response.status(404)
                .entity(ex.getMessage())
                .type("text/plain")
                .build();
    }
}
