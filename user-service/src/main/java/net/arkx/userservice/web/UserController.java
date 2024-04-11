package net.arkx.userservice.web;

import net.arkx.userservice.dtos.AuthenticationDTO;
import net.arkx.userservice.entities.*;
import net.arkx.userservice.exceptions.userExceptions.*;
import net.arkx.userservice.security.JwtService;
import net.arkx.userservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
@RestController @RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private static final Logger log = LoggerFactory.getLogger(User.class);

    public UserController(UserService userService, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    //Create user
    @PostMapping("/createUser")
    public User createUser(@RequestBody UserRole userRole){

        return userService.createUser(userRole);
    }
    //Compte Activation
    @PostMapping("/activation")
    public void activation (@RequestBody Map<String, String> activation){
        userService.activation(activation);
    }
    //Connexion
    @PostMapping("/connexion")
    public Map<String, String> connexion(@RequestBody AuthenticationDTO authenticationDTO) {
        User user = userService.getUserByUsername(authenticationDTO.username());
        if (user != null && user.isActif()) {
            final Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationDTO.username(), authenticationDTO.password())
            );
            if (authenticate.isAuthenticated()) {
                return jwtService.generate(authenticationDTO.username());
            }
        } else {
            throw new AccountNotActiveException("Account is not active !!");
        }
        return null;
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
    public ResponseEntity<?> setUsername(@PathVariable String username, @RequestBody String newUsername) {
        try {
            User updatedUser = userService.updateUsername(username, newUsername);
            return ResponseEntity.ok(updatedUser);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (DuplicateUsernameException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Update Password
    @PutMapping("/setPassword/{username}/password")
    public ResponseEntity<?> setPassword(@PathVariable String username, @RequestBody String newPassword) {
        try {
            User updatePassword = userService.updatePassword(username, newPassword);
            return ResponseEntity.ok(updatePassword);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (InvalidPasswordException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //Update Email
    @PutMapping("/setEmail/{username}/email")
    public ResponseEntity<?> setEmail(@PathVariable String username, @RequestBody String newEmail) {
        try {
            User updateEmail = userService.updateEmail(username, newEmail);
            return ResponseEntity.ok(updateEmail);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (InvalidEmailException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    // Update First and Last name
    @PutMapping("/setName/{username}/name")
    public ResponseEntity<?> updateFirstNameAndLastName(
            @PathVariable String username,
            @RequestBody String firstName,
            @RequestBody String lastName) {
        try {
            User updatedUser = userService.updateName(username, firstName, lastName);
            if (updatedUser != null) {
                return ResponseEntity.ok(updatedUser);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (EntityNotFoundException e) {
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
    public User addRoletoUser(@RequestParam String username, @RequestParam String role) {

        return userService.addRoleToUser(username, role);
    }

    //Delete Role from User
    @DeleteMapping("/deleteRole")
    public User deleteRoleFromUser(@RequestParam String username, @RequestParam String role){
        return userService.deleteRoleFromUser(username,role);
    }
    //Add Notification to User
    @PostMapping("/addNotification")
    public ResponseEntity<?> addNotificationToUser(@RequestBody String username, @RequestBody Notification notification){
         userService.addNotificationToUser(username,notification);
        return ResponseEntity.ok().build();
    }
    //Delete Notification From User
    @DeleteMapping("/deleteNotification")
    public ResponseEntity<?> deleteNotificationFromUser(@RequestParam String username,@RequestParam Long notificationId){
         userService.removeNotificationFromUser(username, notificationId);
         return ResponseEntity.ok().build();
    }

    //Add Address to User
    @PostMapping("/addAddress")
    public ResponseEntity<?> addAddressToUser(@RequestBody String username,@RequestBody Address address){
        userService.addAddressToUser(username, address);
        return ResponseEntity.ok().build();
    }
    //Remove Address from user
    @DeleteMapping("/deleteAddress")
    public ResponseEntity<?> deleteAddressFromUser(@RequestParam String username,@RequestParam Long addressId){
        userService.removeAddressFromUser(username, addressId);
        return ResponseEntity.ok().build();
    }
    //Add Wishlist to User
    @PostMapping("/addWishlist")
    public ResponseEntity <?> addWishlistToUser(@RequestBody String username,@RequestBody WishList wishList){
        userService.addWishlistToUser(username,wishList);
        return ResponseEntity.ok().build();
    }
    //Delete Wishlist From User
    @DeleteMapping("/deleteWishlist")
    public ResponseEntity <?> deleteWishlistFromUser(@RequestParam String username,@RequestParam Long wishlistId){
        userService.removeWishlistFromUser(username, wishlistId);
        return ResponseEntity.ok().build();
    }
}