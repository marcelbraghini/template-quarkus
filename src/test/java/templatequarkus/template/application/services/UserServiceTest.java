package templatequarkus.template.application.services;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import templatequarkus.template.adapters.databases.entities.UserEntity;
import templatequarkus.template.adapters.databases.repositories.UserRepository;

import javax.ws.rs.NotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private ObjectId testId;
    private UserEntity testUser;

    @BeforeEach
    void setUp() {
        testId = new ObjectId();
        testUser = new UserEntity();
        testUser.setLogin("testuser");
        testUser.setName("Test User");
        testUser.setBlog("https://blog.test");
        testUser.setLocation("Test City");
        testUser.setBio("Test bio");
    }

    @Test
    void shouldReturnAllUsers() {
        final List<UserEntity> expected = Arrays.asList(testUser, new UserEntity());
        when(userRepository.listAll()).thenReturn(expected);

        final List<UserEntity> result = userService.getAll();

        assertEquals(expected, result);
        verify(userRepository, times(1)).listAll();
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void shouldSaveUser() {
        when(userRepository.save(testUser)).thenReturn(testUser);

        final UserEntity result = userService.save(testUser);

        assertEquals(testUser, result);
        verify(userRepository, times(1)).save(testUser);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void shouldUpdateUser() {
        final UserEntity updatedData = new UserEntity();
        updatedData.setLogin("newlogin");
        updatedData.setName("New Name");
        updatedData.setBlog("https://newblog.com");
        updatedData.setLocation("New City");
        updatedData.setBio("New bio");

        when(userRepository.findByIdOptional(testId)).thenReturn(Optional.of(testUser));

        userService.update(testId, updatedData);

        assertEquals("newlogin", testUser.getLogin());
        assertEquals("New Name", testUser.getName());
        assertEquals("https://newblog.com", testUser.getBlog());
        assertEquals("New City", testUser.getLocation());
        assertEquals("New bio", testUser.getBio());
        verify(userRepository, times(1)).findByIdOptional(testId);
        verify(userRepository, times(1)).update(testUser);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void shouldFindUserById() {
        when(userRepository.findByIdOptional(testId)).thenReturn(Optional.of(testUser));

        final UserEntity result = userService.findById(testId);

        assertEquals(testUser, result);
        verify(userRepository, times(1)).findByIdOptional(testId);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void shouldThrowNotFoundExceptionWhenUserNotFoundById() {
        when(userRepository.findByIdOptional(testId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.findById(testId));
        verify(userRepository, times(1)).findByIdOptional(testId);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void shouldFindUserByName() {
        when(userRepository.findByName("Test User")).thenReturn(testUser);

        final UserEntity result = userService.findByName("Test User");

        assertEquals(testUser, result);
        verify(userRepository, times(1)).findByName("Test User");
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void shouldFindUserByLogin() {
        when(userRepository.findByLogin("testuser")).thenReturn(testUser);

        final UserEntity result = userService.findByLogin("testuser");

        assertEquals(testUser, result);
        verify(userRepository, times(1)).findByLogin("testuser");
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void shouldRemoveUser() {
        final String idStr = testId.toHexString();

        userService.remove(idStr);

        verify(userRepository, times(1)).deleteById(testId);
        verifyNoMoreInteractions(userRepository);
    }
}
