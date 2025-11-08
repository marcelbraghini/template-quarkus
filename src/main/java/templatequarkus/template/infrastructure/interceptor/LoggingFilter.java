package templatequarkus.template.infrastructure.interceptor;

import io.vertx.core.http.HttpServerRequest;
import org.jboss.logging.Logger;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

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

