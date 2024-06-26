package net.arkx.userservice.service;

import jakarta.inject.Singleton;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import net.arkx.userservice.entities.*;
import net.arkx.userservice.exceptions.addressException.AddressNotFoundException;
import net.arkx.userservice.exceptions.notificationExceptions.NotificationNotFoundException;
import net.arkx.userservice.exceptions.userExceptions.*;
import net.arkx.userservice.exceptions.RoleExceptions.RoleAlreadyExistUserException;
import net.arkx.userservice.exceptions.RoleExceptions.RoleNotFoundException;
import net.arkx.userservice.exceptions.wishlistExcpetions.wishlistNotFoundException;
import net.arkx.userservice.repository.RoleRepository;
import net.arkx.userservice.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {


    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final ValidationService validationService;
    private final RoleService roleService;


    public void save(User user) {

        userRepository.save(user);
    }


    //Create User
    public User createUser(UserRole userRole) {
        User user = userRole.getUser();
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            throw new EntityNotFoundException("Username can't be empty");
        }
        if (user.getEmail() == null || !isValidEmail(user.getEmail())) {
            throw new InvalidEmailException("Invalid email format: " + user.getEmail());
        }
        if (user.getPassword() == null || !isValidPassword(user.getPassword())) {
            throw new InvalidPasswordException("Invalid password format");
        }
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new EntityAlreadyExistException("This userName " + user.getUsername() + "  already exist ");
        }

        addRoles(user, userRole.getRoles().stream().filter(s -> s != null && !s.isEmpty()).collect(Collectors.toList()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);
        validationService.register(user);
        return user;
    }

    //Get use By his id
    public User getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // Get User by Username
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Get all users
    public List<User> getAll() {
        return userRepository.findAll();
    }

    //Get user by name, first we check if the field is empty then check if the name exist
    public User getByName(String name) {

        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("the name can't be empty");
        }
        User user = userRepository.findByUsername(name);
        if (user == null) {
            throw new EntityNotFoundException("Can't find this name " + name);
        }
        return user;
    }

    //Update User firstname & lastname
    public User updateName(String username, String firstName, String lastName) {
        User existingUser = userRepository.findByUsername(username);
        if (existingUser != null) {
            if (firstName != null) {
                existingUser.setFirstName(firstName);
            }
            if (lastName != null) {
                existingUser.setLastName(lastName);
            }
            userRepository.save(existingUser);
        }
        return existingUser;
    }

    // Update username
    public User updateUsername(String username, String newUsername) {
        User existingUser = userRepository.findByUsername(username);
        if (existingUser == null) {
            throw new EntityNotFoundException("Cannot find user with username: " + username);
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
            throw new EntityNotFoundException("Cannot find user with username: " + username);
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
    public User updatePassword(String username, String newPassword) {
        User existingUser = userRepository.findByUsername(username);
        if (existingUser == null) {
            throw new EntityNotFoundException("Cannot find user with username: " + username);
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
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("User not found"));
    }

    //Delete by username
    public User deleteUserByUsername(String username) {
        User userToDelete = userRepository.findByUsername(username);
        if (userToDelete != null) {
            userRepository.delete(userToDelete);
            return userToDelete;
        } else {
            throw new jakarta.persistence.EntityNotFoundException("User with username " + username + " not found");
        }
    }

    //Add Role
    public User addRoleToUser(String username, String roleName) {
        User userToAddRole = userRepository.findByUsername(username);
        if (userToAddRole == null) {
            throw new EntityNotFoundException("User not found with username: " + username);
        }

        Role role = roleRepository.findByName(roleName);
        if (role == null) {
            role = Role.builder().name(roleName).build();
            role = roleRepository.save(role);
        }

        Set<Role> roles = userToAddRole.getRoles();
        if (roles.contains(role)) {
            throw new EntityAlreadyExistException("User already has this role: " + role.getName());
        }

        roles.add(role);
        return userRepository.save(userToAddRole);
    }


    //Delete Role
    public User deleteRoleFromUser(String username, String roleName) {
        User userToDeleteRole = userRepository.findByUsername(username);
        if (userToDeleteRole == null) {
            throw new EntityNotFoundException("User not found with username: " + username);
        }
        Role role = roleRepository.findByName(roleName);
        if (role == null) {
            throw new RoleNotFoundException("Role not found with name: " + roleName);

        }
        Set<Role> roles = userToDeleteRole.getRoles();

        if (!roles.contains(role)) {
            throw new RoleAlreadyExistUserException("User already has this role: " + role.getName());

        }
        roles.remove(role);
        return userRepository.save(userToDeleteRole);
    }

    // Add notification
    public void addNotificationToUser(String username, Notification notification) {
        User user = userRepository.findByUsername(username);
        notification.setUser(user);
        user.getNotifications().add(notification);
        userRepository.save(user);
    }

    //Delete Notification from User
    public void removeNotificationFromUser(String username, Long notificationId) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new EntityNotFoundException("User not found with username: " + username);
        }
        Notification notificationToRemove = user.getNotifications().stream()
                .filter(notification -> notification.getId().equals(notificationId))
                .findFirst()
                .orElseThrow(() -> new NotificationNotFoundException("Notification not found with ID: " + notificationId));
        user.getNotifications().remove(notificationToRemove);
        notificationToRemove.setUser(null);
        userRepository.save(user);

    }

    //Add Address To User
    public void addAddressToUser(String username, Address address) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new EntityNotFoundException("User not found with username: " + username);
        }
        address.setUser(user);
        user.getAddresses().add(address);
        userRepository.save(user);
    }

    //Delete Address from User
    public void removeAddressFromUser(String username, Long addressId) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new EntityNotFoundException("User not found with username: " + username);
        }
        Address addressToRemove = user.getAddresses().stream()
                .filter(address -> address.getId().equals(addressId))
                .findFirst()
                .orElseThrow(() -> new AddressNotFoundException("Address not found with ID: " + addressId));
        user.getAddresses().remove(addressToRemove);
        addressToRemove.setUser(null);
        userRepository.save(user);
    }

    //Add WishList to User
    public void addWishlistToUser(String username, WishList wishList) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new EntityNotFoundException("User not found with username: " + username);
        }
        wishList.setUser(user);
        user.getWishLists().add(wishList);
        userRepository.save(user);
    }

    //Delete Wishlist from User
    public void removeWishlistFromUser(String username, Long wishlistId) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new EntityNotFoundException("User not found with username: " + username);
        }
        WishList wishListToRemove = user.getWishLists().stream()
                .filter(wishList -> wishList.getId().equals(wishlistId))
                .findFirst()
                .orElseThrow(() -> new wishlistNotFoundException("wishlist not found with ID: " + wishlistId));

        user.getWishLists().remove(wishListToRemove);
        wishListToRemove.setUser(null);
        userRepository.save(user);
    }


    public void activation(Map<String, String> activation) {
        Validation validation = validationService.readingBasedOnCode(activation.get("code"));
        if (Instant.now().isAfter(validation.getExpiration())) {
            throw new RuntimeException("Your code has been expired");
        }
        User actifUser = userRepository.findById(validation.getUser().getId()).orElseThrow(() -> new EntityNotFoundException("Unknown user"));
        actifUser.setActif(true);
        userRepository.save(actifUser);
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository
                .findByUsername(username);
    }

    public void addRoles(User user, List<String> roles) {
        for (String roleName : roles) {
            user.getRoles().add(roleService.createRole(roleName));
        }
    }

}
