package templatequarkus.template.infrastructure.interceptor;

import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.net.SocketAddress;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.UriInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoggingFilterTest {

    @Test
    void shouldLogRequestInfoWithoutErrors() {
        final ContainerRequestContext ctx = mock(ContainerRequestContext.class);
        final UriInfo uriInfo = mock(UriInfo.class);
        final HttpServerRequest request = mock(HttpServerRequest.class);
        final SocketAddress socketAddress = mock(SocketAddress.class);

        when(ctx.getMethod()).thenReturn("GET");
        when(uriInfo.getPath()).thenReturn("v1/users");
        when(request.remoteAddress()).thenReturn(socketAddress);
        when(socketAddress.toString()).thenReturn("127.0.0.1:12345");

        final LoggingFilter filter = new LoggingFilter();
        filter.info = uriInfo;
        filter.request = request;

        filter.filter(ctx);

        verify(ctx, times(1)).getMethod();
        verify(uriInfo, times(1)).getPath();
        verify(request, times(1)).remoteAddress();
        verifyNoMoreInteractions(ctx, uriInfo, request);
    }
}
