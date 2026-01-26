package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.entity.User;
import com.ecommerce.ecommerce.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController()
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService=userService;
    }

    @GetMapping("/work")
    public String working(){
        return "Working";
    }

    @PostMapping("/adding")
    public User addingUser(@RequestBody User user){
     User   savedUser =userService.addUser(user);
        return  savedUser;


    }
    @GetMapping("/byid/{id}")
    public Optional<User> userById(@PathVariable("id") int id){
        Optional<User> user=userService.getById(id);
        return user;
    }

}
