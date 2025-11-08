package templatequarkus.template.adapters.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionMapperTest {

    private GlobalExceptionMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new GlobalExceptionMapper();
    }

    @Test
    void shouldMapWebApplicationException() {
        final WebApplicationException exception = new NotFoundException("Resource not found");

        final Response response = mapper.toResponse(exception);
        final GlobalExceptionMapper.ErrorPayload payload = (GlobalExceptionMapper.ErrorPayload) response.getEntity();

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_TYPE, response.getMediaType());
        assertNotNull(payload.errorId);
        assertEquals("Resource not found", payload.message);
        assertEquals(404, payload.status);
        assertNotNull(payload.timestamp);
    }

    @Test
    void shouldMapGenericException() {
        final RuntimeException exception = new RuntimeException("Internal error");

        final Response response = mapper.toResponse(exception);
        final GlobalExceptionMapper.ErrorPayload payload = (GlobalExceptionMapper.ErrorPayload) response.getEntity();

        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_TYPE, response.getMediaType());
        assertNotNull(payload.errorId);
        assertEquals("Ocorreu um erro interno", payload.message);
        assertEquals(500, payload.status);
        assertNotNull(payload.timestamp);
    }

    @Test
    void shouldHandleNullMessage() {
        final RuntimeException exception = new RuntimeException((String) null);

        final Response response = mapper.toResponse(exception);
        final GlobalExceptionMapper.ErrorPayload payload = (GlobalExceptionMapper.ErrorPayload) response.getEntity();

        assertEquals(500, response.getStatus());
        assertEquals("Ocorreu um erro interno", payload.message);
    }

    @Test
    void shouldHandleBlankMessage() {
        final RuntimeException exception = new RuntimeException("   ");

        final Response response = mapper.toResponse(exception);
        final GlobalExceptionMapper.ErrorPayload payload = (GlobalExceptionMapper.ErrorPayload) response.getEntity();

        assertEquals(500, response.getStatus());
        assertEquals("Ocorreu um erro interno", payload.message);
    }
}

