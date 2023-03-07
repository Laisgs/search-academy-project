package co.empathy.academy.users.services.impl;

import co.empathy.academy.users.entities.User;
import co.empathy.academy.users.services.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class UserServiceImpl implements UserService {
    private ConcurrentHashMap<Integer, User> usuarios = new ConcurrentHashMap<>();
    @Override
    public User getUser(int id) {
        if(usuarios.containsKey(id)){
            return usuarios.get(id);
        }
        return null;
    }
    @Override
    public List<User> getUsers() {
        return new ArrayList<>(usuarios.values());
    }

    @Override
    public boolean add(User user) {
        if(!usuarios.containsKey(user.getId())){
            usuarios.put(user.getId(), user);
            return true;
        }
        return false;
    }

    @Override
    public boolean edit(User user) {
        if(usuarios.containsKey(user.getId())){
            usuarios.replace(user.getId(), user);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        if(usuarios.containsKey(id)){
            usuarios.remove(id);
            return true;
        }
        return false;
    }
}
