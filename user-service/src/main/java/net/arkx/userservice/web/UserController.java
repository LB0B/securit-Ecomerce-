package net.arkx.userservice.web;

import net.arkx.userservice.entities.Role;
import net.arkx.userservice.entities.User;
import net.arkx.userservice.exception.userExceptions.DuplicateUsernameException;
import net.arkx.userservice.exception.userExceptions.InvalidEmailException;
import net.arkx.userservice.exception.userExceptions.InvalidPasswordException;
import net.arkx.userservice.exception.userExceptions.UserNotFoundException;
import net.arkx.userservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //Create user
    @PostMapping("/createUser")
    public User createUser(User user){
        return userService.createUser(user);
    }

    //Get all users
    @GetMapping
    public List<User> getUsers() {
        return userService.getAll();
    }

    //Get user by id
    @GetMapping("/{id}")
    public User getById(@PathVariable Long id) {
        return userService.getById(id);
    }


    //Get user by name
    @GetMapping("/{username}")
    public User getUserByName(@PathVariable String username){
        return userService.getByName(username);
    }


    //Update Username
    @PutMapping("/setUsername/{username}")
    public ResponseEntity<?> setUsername(@PathVariable String username, @RequestParam String newUsername) {
        try {
            User updatedUser = userService.updateUsername(username, newUsername);
            return ResponseEntity.ok(updatedUser);
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (DuplicateUsernameException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Update Password
    @PutMapping("/setPassword/{username}/password")
    public ResponseEntity<?> setPassword(@PathVariable String username, @RequestParam String newPassword) {
        try {
            User updatePassword = userService.updatePassword(username, newPassword);
            return ResponseEntity.ok(updatePassword);
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (InvalidPasswordException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //Update Email
    @PutMapping("/setEmail/{username}/email")
    public ResponseEntity<?> setEmail(@PathVariable String username, @RequestParam String newEmail) {
        try {
            User updateEmail = userService.updateEmail(username, newEmail);
            return ResponseEntity.ok(updateEmail);
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (InvalidEmailException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    // Update First and Last name
    @PutMapping("/setName/{username}/name")
    public ResponseEntity<?> updateFirstNameAndLastName(
            @PathVariable String username,
            @RequestParam String firstName,
            @RequestParam String lastName) {
        try {
            User updatedUser = userService.updatename(username, firstName, lastName);
            if (updatedUser != null) {
                return ResponseEntity.ok(updatedUser);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //Delete User by username

    @DeleteMapping("/deleteUser")
    public User deleteByUsername(@RequestParam String username){
        return userService.deleteUserByUsername(username);
    }
//Add Role to user
    @PostMapping("/addRole")
    public User addRoletoUser(@RequestParam String username, String role){
        return userService.addRoleToUser(username,role);

    }
    @DeleteMapping("/deleteRole")
    public User deleteRoleFromUser(@RequestParam String username, String role){
        return userService.deleteRoleFromUser(username,role);
    }
}