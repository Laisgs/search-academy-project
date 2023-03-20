package co.empathy.academy.search.users.services.impl;

import co.empathy.academy.search.exceptions.UserAlreadyExistException;
import co.empathy.academy.search.users.entities.User;
import co.empathy.academy.search.users.services.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserServiceImpl implements UserService {
    private ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();
    private int i;
    @Override
    public User getUser(int id) {
        if(users.containsKey(id)){
            return users.get(id);
        }
        return null;
    }
    @Override
    public List<User> getUsers() {
        //add(new User(i, "Prueba", "Prueba", "correo@prueba"));
        //i++;
        return new ArrayList<>(users.values());
    }

    @Override
    public void add(User user) throws UserAlreadyExistException {
        if(!users.containsKey(user.getId())){
            users.put(user.getId(), user);
        }else {
            throw new UserAlreadyExistException("User alredy exist");
        }
    }

    @Override
    public boolean edit(User user) {
        if(users.containsKey(user.getId())){
            users.replace(user.getId(), user);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        if(users.containsKey(id)){
            users.remove(id);
            return true;
        }
        return false;
    }

    @Override
    @Async
    public void saveUsers(MultipartFile file) {
        try {

            List<User> usersInFile = new ObjectMapper().readValue(file.getBytes(), new TypeReference<>() {
            });

            usersInFile.forEach(this::add);

            /*
            usersInFile.stream()
                    .filter(user -> !users.containsKey(user.getId()))
                    .map(user -> users.put(user.getId(), user));

             */
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
