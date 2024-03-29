package net.arkx.userservice.web;

import net.arkx.userservice.entities.Address;
import net.arkx.userservice.entities.Notification;
import net.arkx.userservice.entities.User;
import net.arkx.userservice.entities.WishList;
import net.arkx.userservice.exceptions.userExceptions.DuplicateUsernameException;
import net.arkx.userservice.exceptions.userExceptions.InvalidEmailException;
import net.arkx.userservice.exceptions.userExceptions.InvalidPasswordException;
import net.arkx.userservice.exceptions.userExceptions.UserNotFoundException;
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
    @GetMapping("/id/{id}")
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
    //Delete Role from User
    @DeleteMapping("/deleteRole")
    public User deleteRoleFromUser(@RequestParam String username, String role){
        return userService.deleteRoleFromUser(username,role);
    }
    //Add Notification to User
    @PostMapping("/addNotification")
    public ResponseEntity<?> addNotificationToUser(String username, Notification notification){
         userService.addNotificationToUser(username,notification);
        return ResponseEntity.ok().build();
    }
    //Delete Notification From User
    @DeleteMapping("/deleteNotification")
    public ResponseEntity<?> deleteNotificationFromUser(String username, Long notificationId){
         userService.removeNotificationFromUser(username, notificationId);
         return ResponseEntity.ok().build();
    }

    //Add Address to User
    @PostMapping("/addAddress")
    public ResponseEntity<?> addAddressToUser(String username, Address address){
        userService.addAddressToUser(username, address);
        return ResponseEntity.ok().build();
    }
    //Remove Address from user
    @DeleteMapping("/deleteAddress")
    public ResponseEntity<?> deleteAddressFromUser(String username, Long addressId){
        userService.removeAddressFromUser(username, addressId);
        return ResponseEntity.ok().build();
    }
    //Add Wishlist to User
    @PostMapping("/addWishlist")
    public ResponseEntity <?> addWishlistToUser(String username, WishList wishList){
        userService.addWishlistToUser(username,wishList);
        return ResponseEntity.ok().build();
    }
    //Delete Wishlist From User
    @DeleteMapping("/deleteWishlist")
    public ResponseEntity <?> deleteWishlistFromUser(String username, Long wishlistId){
        userService.removeWishlistFromUser(username, wishlistId);
        return ResponseEntity.ok().build();
    }
}