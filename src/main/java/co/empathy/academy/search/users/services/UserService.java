package co.empathy.academy.search.users.services;

import co.empathy.academy.search.users.entities.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    User getUser(int id);


    List<User> getUsers();

    boolean add(User user);

    boolean edit(User user);

    boolean delete(int id);

    void saveUsers(MultipartFile file);
}
