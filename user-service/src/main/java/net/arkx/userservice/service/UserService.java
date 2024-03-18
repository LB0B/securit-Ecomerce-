package net.arkx.userservice.service;

import lombok.*;
import net.arkx.userservice.entities.User;
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

@Getter @Setter @ToString   @Builder
@Service
public class UserService  {


   private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }




    public List<User> getAll() {
        return userRepository.findAll();
    }
    public User getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
    public User save(User user) {
        return userRepository.save(user);
    }
}
