package net.arkx.orderservice.users;

import net.arkx.orderservice.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name ="USER-SERVICE")
public interface UserRestClient {
    @GetMapping("/users/id/{id}")
    User findUserById(@PathVariable Long id);

}
