package lv.lpb.rest.errorHandling;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ServerExceptionMapper implements ExceptionMapper<Throwable> {

    private static Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;
    
    @Override
    public Response toResponse(Throwable exception) {
        return Response.status(status.getStatusCode())
                .entity(new ErrorMessage(status.getStatusCode(), status.getReasonPhrase()))
                .type(MediaType.APPLICATION_JSON) 
                .build();
    }
}
