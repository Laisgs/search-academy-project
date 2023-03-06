package co.empathy.academy.users.services;

import co.empathy.academy.users.entities.User;

import java.util.List;

public interface UserService {
    User getUser(int id);


    List<User> getUsers();

    boolean add(User user);
}
