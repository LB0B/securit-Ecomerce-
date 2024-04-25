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

import java.util.Date;
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
    //Refresh Token
    @PostMapping("/refresh-token")
    public @ResponseBody Map<String, String> refreshToken (@RequestBody Map<String, String> refreshTokenRequest){
        return jwtService.refreshToken(refreshTokenRequest);
    }

    //Create user
    @PostMapping("/signIn")
    public User signIn(@RequestBody UserRole userRole){

        return userService.createUser(userRole);
    }
    // Activation
    @PostMapping("/activation")
    public void activation (@RequestBody Map<String, String> activation){
        userService.activation(activation);
    }
    //LogOut
    @PostMapping("/logout")
    public void logout (){
        jwtService.deconnexion();
    }
    //LogIn
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody AuthenticationDTO authenticationDTO) {
        User user = userService.getUserByUsername(authenticationDTO.username());
         long currentTime = System.currentTimeMillis();
        if (user != null && user.isActif()) {
            final Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationDTO.username(), authenticationDTO.password())
            );
            if (authenticate.isAuthenticated()) {
                user.setLastLogin(new Date(currentTime));
                userService.save(user);
                return jwtService.generate(authenticationDTO.username());
            }else{
                throw new AccountNotActiveException("Adad !!");

            }
        } else {
            throw new AccountNotActiveException("Account is not active !!");
        }

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
        } catch (DuplicateEntityException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Update Password
    @PostMapping("/update-password")
    public void updatePwd (@RequestBody Map<String, String> activation){
        userService.updatePassword(activation);
    }
    //New Password
    @PostMapping("/new-password")
    public void newPwd (@RequestBody Map<String, String> activation){
        userService.newPassword(activation);
    }

    //Update Email
    @PutMapping("/setEmail/{username}/email")
    public ResponseEntity<?> setEmail(@PathVariable String username, @RequestBody String newEmail) {
        try {
            User updateEmail = userService.updateEmail(username, newEmail);
            return ResponseEntity.ok(updateEmail);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (InvalidEntityException e) {
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
    public ResponseEntity<Address> addAddressToUser(@RequestBody Address address){
        return ResponseEntity.ok(userService.addAddressToUser(address));
    }
    //Remove Address from user
    @DeleteMapping("/deleteAddress")
    public ResponseEntity<?> deleteAddressFromUser(@RequestParam Long addressId) throws Exception {

        userService.removeAddressFromUser(addressId);

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