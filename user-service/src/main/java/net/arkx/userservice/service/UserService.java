package net.arkx.userservice.service;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.*;
import net.arkx.userservice.entities.User;
import net.arkx.userservice.exception.DuplicateUsernameException;
import net.arkx.userservice.exception.InvalidEmailException;
import net.arkx.userservice.exception.InvalidPasswordException;
import net.arkx.userservice.exception.UserNotFoundException;
import net.arkx.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter @Setter @ToString   @Builder
@Service
public class UserService  {


   private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(User user) {
        return userRepository.save(user);
    }


    //Create User
    public User createUser(User user){
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new EntityExistsException("This userName " + user.getUsername() + "  already exist ");
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
    public User updateUser(String username, User updatedUser){
        User existingUser = userRepository.findByUsername(username);
        if (updatedUser != null) {
            if (updatedUser.getFirstName() != null) {
                existingUser.setFirstName(updatedUser.getFirstName());
            }
            if (updatedUser.getLastName() != null) {
                existingUser.setLastName(updatedUser.getLastName());
            }
        }
        return userRepository.save(existingUser);

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



}
