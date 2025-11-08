package templatequarkus.template.infrastructure.interceptor;

import io.vertx.core.http.HttpServerRequest;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

@Provider
public class LoggingFilter implements ContainerRequestFilter {

    private static final Logger logger = Logger.getLogger(LoggingFilter.class);

    @Context
    public UriInfo info;

    @Context
    public HttpServerRequest request;

    @Override
    public void filter(final ContainerRequestContext context) {
        final String path = info.getPath();
        final String address = request.remoteAddress().toString();
        logger.infof("Request %s %s from IP %s", context.getMethod(), path, address);
    }
}
