package templatequarkus.template.service;

import templatequarkus.template.adapters.databases.entities.UserEntity;
import templatequarkus.template.application.services.UserService;
import io.quarkus.test.junit.QuarkusTest;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class UserEntityServiceTest {

    @Inject
    UserService userService;

    @ConfigProperty(name = "quarkus.token")
    String token;

    @Test
    public void testUserSave() {
        final UserEntity userEntity = new UserEntity("maquinaZ",
                "Maquina Z",
                "https://google.com.br",
                "Chapecó-SC-Brasil",
                "Desenvolvedor Front-End");
        assertNotNull(userService.save(userEntity));
    }

    @Test
    public void testUserAll() {
        assertNotNull(userService.getAll());
    }

    @Test
    public void testFindAndDelete() {
        final UserEntity userEntity = userService.save(new UserEntity("crazydog",
                "Crazy Dog",
                "https://google.com.br",
                "Chapecó-SC-Brasil",
                "Desenvolvedor Full Stack"));

        final UserEntity userEntitySaved = userService.findById(userEntity.get_id());
        assertEquals("Crazy Dog", userEntitySaved.getName());

        userService.remove(userEntitySaved.get_id().toHexString());
        assertNull(userService.findByName("Crazy Dog"));
        assertNull(userService.findByLogin("crazydog"));
    }

    @Test
    public void testToken() {
        assertEquals("abc", token);
    }

}
