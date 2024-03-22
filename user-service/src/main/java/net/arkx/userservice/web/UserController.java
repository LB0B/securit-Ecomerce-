package net.arkx.userservice.web;

import lombok.AllArgsConstructor;
import net.arkx.userservice.entities.User;
import net.arkx.userservice.exception.DuplicateUsernameException;
import net.arkx.userservice.exception.UserNotFoundException;
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

    @GetMapping
    public List<User> getUsers(){
    return userService.getAll();
}
    @GetMapping("/{id}")
    public User getById(@PathVariable Long id) {
        return userService.getById(id);
    }
    //Update Username
    @PostMapping("/setusername/{username}")
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

}

