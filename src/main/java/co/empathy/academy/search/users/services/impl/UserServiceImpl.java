package co.empathy.academy.search.users.services.impl;

import co.empathy.academy.search.users.entities.User;
import co.empathy.academy.search.users.services.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserServiceImpl implements UserService {
    private ConcurrentHashMap<Integer, User> usuarios = new ConcurrentHashMap<>();
    private int i;
    @Override
    public User getUser(int id) {
        if(usuarios.containsKey(id)){
            return usuarios.get(id);
        }
        return null;
    }
    @Override
    public List<User> getUsers() {
        //add(new User(i, "Prueba", "Prueba", "correo@prueba"));
        //i++;
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
