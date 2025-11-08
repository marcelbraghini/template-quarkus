package templatequarkus.template.adapters.databases.repositories;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import templatequarkus.template.adapters.databases.entities.UserEntity;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepository implements PanacheMongoRepository<UserEntity> {

    public UserEntity findByName(final String name){
        return find("name", name).firstResult();
    }

    public UserEntity findByLogin(final String login) {
        return find("login", login).firstResult();
    }

    public UserEntity save(final UserEntity userEntity) {
        persist(userEntity);
        return userEntity;
    }

}
