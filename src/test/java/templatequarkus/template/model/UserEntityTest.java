package templatequarkus.template.model;

import org.junit.jupiter.api.Test;
import templatequarkus.template.adapters.databases.entities.UserEntity;

import static org.junit.jupiter.api.Assertions.*;

public class UserEntityTest {

    @Test
    public void testGenerateKey() {
        final UserEntity userEntity = new UserEntity("crazydog",
                "Crazy Dog",
                "https://google.com.br",
                "Chapecó-SC-Brasil",
                "Desenvolvedor Full Stack");

        assertEquals("XxcrazydogxCrazyDogxX", userEntity.generateKey());
    }

}
