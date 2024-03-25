package net.arkx.userservice.service;

import net.arkx.userservice.entities.Role;
import net.arkx.userservice.entities.User;
import net.arkx.userservice.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    // Create Role

    public Role createRole(Role newRole){
        return roleRepository.save(newRole);
    }

}
