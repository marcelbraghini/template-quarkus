package templatequarkus.template.adapters.controllers;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

import java.time.Instant;
import java.util.UUID;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {

    private static final Logger logger = Logger.getLogger(GlobalExceptionMapper.class);

    @Override
    public Response toResponse(final Throwable exception) {
        final String errorId = UUID.randomUUID().toString();
        logger.errorf(exception, "Erro não tratado (id=%s): %s", errorId, exception.getMessage());

        if (exception instanceof WebApplicationException) {
            final Response original = ((WebApplicationException) exception).getResponse();
            final int status = original.getStatus();
            final ErrorPayload payload = new ErrorPayload(errorId, safeMessage(exception), status, Instant.now().toString());
            return Response.status(status)
                    .type(MediaType.APPLICATION_JSON)
                    .entity(payload)
                    .build();
        }

        final ErrorPayload payload = new ErrorPayload(errorId, "Ocorreu um erro interno", 500, Instant.now().toString());
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .type(MediaType.APPLICATION_JSON)
                .entity(payload)
                .build();
    }

    private String safeMessage(final Throwable ex) {
        final String msg = ex.getMessage();
        return (msg == null || msg.isBlank()) ? "Erro" : msg;
    }

    public static class ErrorPayload {
        public String errorId;
        public String message;
        public int status;
        public String timestamp;

        public ErrorPayload(String errorId, String message, int status, String timestamp) {
            this.errorId = errorId;
            this.message = message;
            this.status = status;
            this.timestamp = timestamp;
        }
    }
}
