package net.arkx.userservice.repository;

import net.arkx.userservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {


    User findByUsername(String name);
    void deleteUserByUsername(String username);
}
