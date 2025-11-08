package templatequarkus.template.adapters.controllers;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import templatequarkus.template.adapters.databases.entities.UserEntity;
import templatequarkus.template.application.services.UserService;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserResourceTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserResource userResource;

    private ObjectId testId;
    private UserEntity testUser;

    @BeforeEach
    void setUp() {
        testId = new ObjectId();
        testUser = new UserEntity("testuser", "Test User", "https://blog.test", "Test City", "Test bio");
        testUser._id = testId;
    }

    @Test
    void shouldReturnAllUsers() {
        final List<UserEntity> users = Arrays.asList(testUser, new UserEntity());
        when(userService.getAll()).thenReturn(users);

        final Response response = userResource.index();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(users, response.getEntity());
        verify(userService, times(1)).getAll();
        verifyNoMoreInteractions(userService);
    }

    @Test
    void shouldReturnUserById() {
        when(userService.findById(testId)).thenReturn(testUser);

        final Response response = userResource.read(testId.toHexString());

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(testUser, response.getEntity());
        verify(userService, times(1)).findById(testId);
        verifyNoMoreInteractions(userService);
    }

    @Test
    void shouldReturn404WhenUserNotFound() {
        when(userService.findById(testId)).thenThrow(new NotFoundException());

        final Response response = userResource.read(testId.toHexString());

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        verify(userService, times(1)).findById(testId);
        verifyNoMoreInteractions(userService);
    }

    @Test
    void shouldCreateUser() {
        when(userService.save(testUser)).thenReturn(testUser);

        final Response response = userResource.create(testUser);

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertNotNull(response.getLocation());
        verify(userService, times(1)).save(testUser);
        verifyNoMoreInteractions(userService);
    }

    @Test
    void shouldReturn400OnCreateError() {
        when(userService.save(testUser)).thenThrow(new RuntimeException("Validation error"));

        final Response response = userResource.create(testUser);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        verify(userService, times(1)).save(testUser);
        verifyNoMoreInteractions(userService);
    }

    @Test
    void shouldUpdateUser() {
        doNothing().when(userService).update(testId, testUser);

        final Response response = userResource.edit(testId.toHexString(), testUser);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        verify(userService, times(1)).update(testId, testUser);
        verifyNoMoreInteractions(userService);
    }

    @Test
    void shouldReturn404OnUpdateError() {
        doThrow(new NotFoundException()).when(userService).update(testId, testUser);

        final Response response = userResource.edit(testId.toHexString(), testUser);

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        verify(userService, times(1)).update(testId, testUser);
        verifyNoMoreInteractions(userService);
    }

    @Test
    void shouldDeleteUser() {
        doNothing().when(userService).remove(testId.toHexString());

        final Response response = userResource.delete(testId.toHexString());

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        verify(userService, times(1)).remove(testId.toHexString());
        verifyNoMoreInteractions(userService);
    }

    @Test
    void shouldReturn404OnDeleteError() {
        doThrow(new NotFoundException()).when(userService).remove(testId.toHexString());

        final Response response = userResource.delete(testId.toHexString());

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        verify(userService, times(1)).remove(testId.toHexString());
        verifyNoMoreInteractions(userService);
    }
}
