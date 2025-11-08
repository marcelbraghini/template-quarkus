package templatequarkus.template.adapters.databases.entities;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserEntityTest {

    @Test
    void shouldCreateEmptyEntity() {
        final UserEntity entity = new UserEntity();
        assertNotNull(entity);
        assertNotNull(entity.data);
    }

    @Test
    void shouldCreateEntityWithConstructor() {
        final UserEntity entity = new UserEntity("testuser", "Test User", "https://blog.test", "Test City", "Test bio");

        assertEquals("testuser", entity.getLogin());
        assertEquals("Test User", entity.getName());
        assertEquals("https://blog.test", entity.getBlog());
        assertEquals("Test City", entity.getLocation());
        assertEquals("Test bio", entity.getBio());
        assertNotNull(entity.data);
    }

    @Test
    void shouldSetAndGetLogin() {
        final UserEntity entity = new UserEntity();
        entity.setLogin("newlogin");
        assertEquals("newlogin", entity.getLogin());
    }

    @Test
    void shouldSetAndGetName() {
        final UserEntity entity = new UserEntity();
        entity.setName("New Name");
        assertEquals("New Name", entity.getName());
    }

    @Test
    void shouldSetAndGetBlog() {
        final UserEntity entity = new UserEntity();
        entity.setBlog("https://newblog.com");
        assertEquals("https://newblog.com", entity.getBlog());
    }

    @Test
    void shouldSetAndGetLocation() {
        final UserEntity entity = new UserEntity();
        entity.setLocation("New Location");
        assertEquals("New Location", entity.getLocation());
    }

    @Test
    void shouldSetAndGetBio() {
        final UserEntity entity = new UserEntity();
        entity.setBio("New bio");
        assertEquals("New bio", entity.getBio());
    }

    @Test
    void shouldGetObjectId() {
        final UserEntity entity = new UserEntity();
        final ObjectId id = new ObjectId();
        entity._id = id;
        assertEquals(id, entity.get_id());
    }

    @Test
    void shouldGenerateKeyWithoutSpaces() {
        final UserEntity entity = new UserEntity("testuser", "Test User", "blog", "loc", "bio");
        final String key = entity.generateKey();

        assertEquals("XxtestuserxTestUserxX", key);
        assertFalse(key.contains(" "));
    }

    @Test
    void shouldGenerateKeyReplacingSpaces() {
        final UserEntity entity = new UserEntity("my user", "My Full Name", "blog", "loc", "bio");
        final String key = entity.generateKey();

        assertEquals("XxmyuserxMyFullNamexX", key);
        assertFalse(key.contains(" "));
    }

    @Test
    void shouldHaveDataDefaultToToday() {
        final UserEntity entity = new UserEntity();
        assertEquals(LocalDate.now(), entity.data);
    }
}

