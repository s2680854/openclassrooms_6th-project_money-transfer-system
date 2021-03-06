package com.openclassrooms.moneytransfersystem.controller;

import com.openclassrooms.moneytransfersystem.model.utility.Requirement;
import com.openclassrooms.moneytransfersystem.model.User;
import com.openclassrooms.moneytransfersystem.service.login.LoginService;
import com.openclassrooms.moneytransfersystem.service.user.UserCreationService;
import com.openclassrooms.moneytransfersystem.service.user.UserDeletionService;
import com.openclassrooms.moneytransfersystem.service.user.UserReadService;
import com.openclassrooms.moneytransfersystem.service.user.UserUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class UserController {

    @Autowired
    private UserCreationService userCreationService;
    @Autowired
    private UserReadService userReadService;
    @Autowired
    private UserUpdateService userUpdateService;
    @Autowired
    private UserDeletionService userDeletionService;
    @Autowired
    private LoginService loginService;

    @PostMapping("/createUser")
    public User createUser(@RequestBody User user) {

        return userCreationService.createUser(user);
    }

    @PostMapping("/createUsers")
    public Collection<User> createUsers(@RequestBody Collection<User> users) {

        return userCreationService.createUsers(users);
    }

    @GetMapping("/users/{id}")
    public User readUserById(@PathVariable Long id) {

        return userReadService.readUserById(id);
    }

    @GetMapping("/user")
    public User readUserByEmail(@RequestParam String email) {

        return userReadService.readUserByEmail(email);
    }

    @GetMapping("/users")
    public Collection<User> readUsers() {

        return userReadService.readUsers();
    }

    @PutMapping("/updateUser")
    public User updateUser(@RequestBody User user) {

        return userUpdateService.updateUser(user);
    }

    @PutMapping("/balanceBack")
    public void getBalanceBack(@RequestParam double amount) {

        Requirement requirement = new Requirement();
        requirement.setAmount(amount);
        loginService.updateBalance(requirement, false);
    }

    @PutMapping("/topup")
    public void getTopup(@RequestParam double amount) {

        Requirement requirement = new Requirement();
        requirement.setAmount(amount);
        loginService.updateBalance(requirement, true);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id) {

        userDeletionService.deleteUserById(id);
    }

    @DeleteMapping("/users")
    public void deleteUsers() {

        userDeletionService.deleteUsers();
    }
}
