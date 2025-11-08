package templatequarkus.template.application.services;

import org.bson.types.ObjectId;
import templatequarkus.template.adapters.databases.entities.UserEntity;
import templatequarkus.template.adapters.databases.repositories.UserRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UserService {

    @Inject
    public UserRepository userRepository;

    public List<UserEntity> getAll(){
        return userRepository.listAll();
    }

    public UserEntity save(final UserEntity userEntity){
        return userRepository.save(userEntity);
    }

    public void update(final ObjectId id, final UserEntity userEntity) {
        UserEntity existing = findById(id);
        existing.setLogin(userEntity.getLogin());
        existing.setName(userEntity.getName());
        existing.setBlog(userEntity.getBlog());
        existing.setLocation(userEntity.getLocation());
        existing.setBio(userEntity.getBio());
        userRepository.update(existing);
    }

    public UserEntity findById(final ObjectId objectId) {
        Optional<UserEntity> optional = userRepository.findByIdOptional(objectId);
        return optional.orElseThrow(NotFoundException::new);
    }

    public UserEntity findByName(final String name) {
        return userRepository.findByName(name);
    }

    public UserEntity findByLogin(final String login) {
        return userRepository.findByLogin(login);
    }

    public void remove(final String _id) {
        userRepository.deleteById(new ObjectId(_id));
    }

}
