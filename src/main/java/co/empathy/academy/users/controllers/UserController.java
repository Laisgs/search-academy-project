package co.empathy.academy.users.controllers;

import co.empathy.academy.users.entities.User;
import co.empathy.academy.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    //No haría falta indicar que es un get ya que es el verbo http por defecto
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@PathVariable int id){
        User user = userService.getUser(id);

        if(user != null){
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/user/all", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getUsers(){
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @RequestMapping(value = "/user/add", method = RequestMethod.POST)
    public ResponseEntity<String> addUser(@ModelAttribute User user){
        if(userService.add(user)){
            return new ResponseEntity<>("User " + user.getName() + " added", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("User " + user.getName() + " already exist", HttpStatus.CONFLICT);
    }

    @RequestMapping(value = "/user/edit", method = RequestMethod.PUT)
    public ResponseEntity<String> editUser(@ModelAttribute User user){
        if(userService.edit(user)){
            return new ResponseEntity<>("User " + user.getName() + " edited", HttpStatus.OK);
        }
        return new ResponseEntity<>("User " + user.getName() + " do not exist", HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/user/{id}/delete", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteUser(@PathVariable int id){
        if(userService.delete(id)){
            return new ResponseEntity<>("User with id " + id + " deleted", HttpStatus.OK);
        }
        return new ResponseEntity<>("User with id " + id + " do not exist", HttpStatus.NOT_FOUND);
    }
}
