package co.empathy.academy.search.users.controllers;

import co.empathy.academy.search.users.services.UserService;
import co.empathy.academy.search.users.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserControllerImpl implements UserController{
    @Autowired
    private UserService userService;

    //No haría falta indicar que es un get ya que es el verbo http por defecto
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@PathVariable int id){
        User user = userService.getUser(id);

        if(user != null){
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getUsers(){
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<String> addUser(@RequestBody User user){
        if(userService.add(user)){
            return new ResponseEntity<>("User " + user.getName() + " added", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("User " + user.getName() + " already exist", HttpStatus.CONFLICT);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity<String> editUser(@RequestBody User user){
        if(userService.edit(user)){
            return new ResponseEntity<>("User " + user.getName() + " edited", HttpStatus.OK);
        }
        return new ResponseEntity<>("User " + user.getName() + " do not exist", HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteUser(@PathVariable int id){
        if(userService.delete(id)){
            return new ResponseEntity<>("User with id " + id + " deleted", HttpStatus.OK);
        }
        return new ResponseEntity<>("User with id " + id + " do not exist", HttpStatus.NOT_FOUND);
    }


    @RequestMapping(value = "/async", method = RequestMethod.POST)
    public void addUsersAsync(@RequestParam("file") MultipartFile file){
        userService.saveUsers(file);
    }

}
