package net.arkx.userservice.web;

import lombok.AllArgsConstructor;
import net.arkx.userservice.entities.User;
import net.arkx.userservice.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController @RequestMapping("/users")
public class UserController {
    private final UserService userService;

@GetMapping
    public List<User> getUsers(){
    return userService.getAll();
}
    @GetMapping("/{id}")
    public User getById(@PathVariable Long id) {
        return userService.getById(id);
    }
}
