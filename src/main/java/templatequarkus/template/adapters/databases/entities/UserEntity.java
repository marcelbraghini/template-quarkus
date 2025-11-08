package templatequarkus.template.adapters.databases.entities;

import io.quarkus.mongodb.panache.MongoEntity;
import io.quarkus.runtime.annotations.RegisterForReflection;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@RegisterForReflection
@MongoEntity(collection = "users")
public class UserEntity {

    @BsonId
    public ObjectId _id;

    @NotNull
    @Size(min=8, max=8)
    public String login;

    @NotNull
    @Size(max=50)
    public String name;

    @NotNull
    @Size(max=100)
    public String blog;

    @NotNull
    @Size(max=50)
    public String location;

    @NotNull
    @Size(max=500)
    public String bio;

    @JsonbDateFormat(value = "yyyy-MM-dd")
    public LocalDate data = LocalDate.now();

    public UserEntity() {
    }

    public UserEntity(final String login,
                      final String name,
                      final String blog,
                      final String location,
                      final String bio
                ) {
        this.login = login;
        this.name = name;
        this.blog = blog;
        this.location = location;
        this.bio = bio;
    }

    public ObjectId get_id() {
        return _id;
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public String getBlog() {
        return blog;
    }

    public String getLocation() {
        return location;
    }

    public String getBio() {
        return bio;
    }

    public String generateKey(){
        String key = "Xx"+login+"x"+name+"xX";
        return key.replaceAll(" ","");
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
