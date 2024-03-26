package net.arkx.userservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.*;
import net.arkx.userservice.entities.Address;
import net.arkx.userservice.entities.Notification;
import net.arkx.userservice.entities.Role;
import net.arkx.userservice.entities.User;
import net.arkx.userservice.exceptions.addressException.AddressNotFoundException;
import net.arkx.userservice.exceptions.notificationExceptions.NotificationNotFoundException;
import net.arkx.userservice.exceptions.userExceptions.*;
import net.arkx.userservice.exceptions.RoleExceptions.RoleAlreadyExistUserException;
import net.arkx.userservice.exceptions.RoleExceptions.RoleNotFoundException;
import net.arkx.userservice.repository.RoleRepository;
import net.arkx.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter @Setter @ToString   @Builder
@Service
public class UserService  {


   private final UserRepository userRepository;
   private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public User save(User user) {
        return userRepository.save(user);
    }


    //Create User
    public User createUser(User user){
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new UserAlreadyExistException("This userName " + user.getUsername() + "  already exist ");
        }
        return userRepository.save(user);

    }

    //Get use By his id
    public User getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // Get all users
    public List<User> getAll() {
        return userRepository.findAll();
    }

    //Get user by name, first we check if the field is empty then check if the name exist
    public User getByName(String name){

        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("the name can't be empty");
        }
        User user = userRepository.findByUsername(name);
        if (user == null) {
            throw new EntityNotFoundException("Can't find this name" + name);
        }
        return user;
    }

    //Update User firstname & lastname
    public User updatename(String username, String firstName, String lastName) {
        User existingUser = userRepository.findByUsername(username);
        if (existingUser != null) {
            if (firstName != null) {
                existingUser.setFirstName(firstName);
            }
            if (lastName != null) {
                existingUser.setLastName(lastName);
            }
            existingUser = userRepository.save(existingUser);
        }
        return existingUser;
    }
    // Update username
    public User updateUsername(String username, String newUsername){
        User existingUser = userRepository.findByUsername(username);
        if (existingUser == null) {
            throw new UserNotFoundException("Cannot find user with username: " + username);
        }
        User existingNewUsernameUser = userRepository.findByUsername(newUsername);
        if (existingNewUsernameUser != null && !existingNewUsernameUser.getId().equals(existingUser.getId())) {
            throw new DuplicateUsernameException("Username " + newUsername + " is already taken");
        }
        existingUser.setUsername(newUsername);

        return userRepository.save(existingUser);

    }
    //**********************************************Update mail******************************
    public User updateEmail(String username, String newEmail) {
        User existingUser = userRepository.findByUsername(username);
        if (existingUser == null) {
            throw new UserNotFoundException("Cannot find user with username: " + username);
        }
        if (!isValidEmail(newEmail)) {
            throw new InvalidEmailException("Invalid email format: " + newEmail);
        }

        existingUser.setEmail(newEmail);
        return userRepository.save(existingUser);
    }
    //Email checker method
    private boolean isValidEmail(String email) {
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(regex);
    }

    // ********************************* Update password******************************
    public User updatePassword(String username, String newPassword){
        User existingUser = userRepository.findByUsername(username);
        if (existingUser == null) {
            throw new UserNotFoundException("Cannot find user with username: " + username);
        }
        if (!isValidPassword(newPassword)) {
            throw new InvalidPasswordException("Invalid password format");
        }
        existingUser.setPassword(newPassword);
        return userRepository.save(existingUser);

    }
    //Password checker method
    private boolean isValidPassword(String password) {
        if (password.length() < 8) {
            return false;
        }
        String regex = "^(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?])(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
/*******************************************************************************************/
    // Delete user: first, we search by their ID. If it exists, we delete the user; otherwise, we throw an exception.
    public User deleteUser(User user) {
        return userRepository.findById(user.getId())
                .map(userToDelete -> {
                    userRepository.delete(userToDelete);
                    return userToDelete;
                })
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }
    //Delete by username
    public User deleteUserByUsername(String username) {
        User userToDelete = userRepository.findByUsername(username);
        if (userToDelete != null) {
            userRepository.delete(userToDelete);
            return userToDelete;
        } else {
            throw new EntityNotFoundException("User with username " + username + " not found");
        }
    }
    //Add Role
    public User addRoleToUser(String username, String roleName) {
        User userToAddRole = userRepository.findByUsername(username);
        if (userToAddRole == null) {
            throw new UserNotFoundException("User not found with username: " + username);
        }
        Set<Role> roles = userToAddRole.getRoles();
        Role role = roleRepository.findByName(roleName);
        if (roles.contains(role)) {
            throw new RoleAlreadyExistUserException("User already has this role: " + role.getName());
        }
        roles.add(role);
        return userRepository.save(userToAddRole);
    }


    //Delete Role
    public User deleteRoleFromUser(String username, String roleName){
        User userToDeleteRole = userRepository.findByUsername(username);
        if(userToDeleteRole== null){
           throw new UserNotFoundException("User not found with username: " + username);
        }
        Role role = roleRepository.findByName(roleName);
        if(role==null){
            throw new RoleNotFoundException("Role not found with name: " + roleName);

        }
        Set<Role> roles =userToDeleteRole.getRoles();

        if(!roles.contains(role)){
            throw new RoleAlreadyExistUserException("User already has this role: " + role.getName());

        }
        roles.remove(role);
        return userRepository.save(userToDeleteRole);
    }
        // Add notification
    public void addNotificationToUser(String username, Notification notification){
        User user = userRepository.findByUsername(username);
        notification.setUser(user);
        user.getNotifications().add(notification);
         userRepository.save(user);
    }
    //Delete Notification from User
    public void removeNotificationFromUser(String username, Long notificationId){
        User user = userRepository.findByUsername(username);
            if(user==null){
                throw new UserNotFoundException("User not found with username: " + username);
            }
        Notification notificationToRemove = user.getNotifications().stream()
                .filter(notification -> notification.getId().equals(notificationId))
                .findFirst()
                .orElseThrow(()->new NotificationNotFoundException("Notification not found with ID: " + notificationId));
            user.getNotifications().remove(notificationToRemove);
            notificationToRemove.setUser(null);
            userRepository.save(user);

    }
    //Add Address To User
    public void addAddressToUser(String username, Address address){
        User user = userRepository.findByUsername(username);
        if(user==null){
            throw new UserNotFoundException("User not found with username: " + username);
        }
        address.setUser(user);
        user.getAddresses().add(address);
        userRepository.save(user);
    }

    //Delete Address from User
    public void removeAddressFromUser(String username, Long addressId){
        User user = userRepository.findByUsername(username);
        if(user==null){
            throw new UserNotFoundException("User not found with username: " + username);
        }
        Address addressToRemove = user.getAddresses().stream()
                .filter(address -> address.getId().equals(addressId))
                .findFirst()
                .orElseThrow(()->  new AddressNotFoundException("Address not found with ID: " + addressId));
        user.getAddresses().remove(addressToRemove);
        addressToRemove.setUser(null);
        userRepository.save(user);
    }





}
