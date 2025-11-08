package templatequarkus.template.adapters.databases.repositories;

import io.quarkus.mongodb.panache.PanacheQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import templatequarkus.template.adapters.databases.entities.UserEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRepositoryTest {

    @InjectMocks
    private UserRepository userRepository;

    private UserEntity testUser;

    @BeforeEach
    void setUp() {
        testUser = new UserEntity("testuser", "Test User", "https://blog.test", "Test City", "Test bio");
    }

    @Test
    void shouldFindByName() {
        final PanacheQuery<UserEntity> query = mock(PanacheQuery.class);
        when(query.firstResult()).thenReturn(testUser);

        final UserRepository spyRepo = spy(userRepository);
        doReturn(query).when(spyRepo).find(eq("name"), eq("Test User"));

        final UserEntity result = spyRepo.findByName("Test User");

        assertEquals(testUser, result);
        verify(spyRepo, times(1)).find("name", "Test User");
        verify(query, times(1)).firstResult();
    }

    @Test
    void shouldFindByLogin() {
        final PanacheQuery<UserEntity> query = mock(PanacheQuery.class);
        when(query.firstResult()).thenReturn(testUser);

        final UserRepository spyRepo = spy(userRepository);
        doReturn(query).when(spyRepo).find(eq("login"), eq("testuser"));

        final UserEntity result = spyRepo.findByLogin("testuser");

        assertEquals(testUser, result);
        verify(spyRepo, times(1)).find("login", "testuser");
        verify(query, times(1)).firstResult();
    }

    @Test
    void shouldSaveEntity() {
        final UserRepository spyRepo = spy(userRepository);
        doNothing().when(spyRepo).persist(testUser);

        final UserEntity result = spyRepo.save(testUser);

        assertEquals(testUser, result);
        verify(spyRepo, times(1)).persist(testUser);
    }
}

