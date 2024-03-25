package net.arkx.userservice.service;

import net.arkx.userservice.entities.Role;
import net.arkx.userservice.entities.User;
import net.arkx.userservice.exception.userExceptions.UserAlreadyExistException;
import net.arkx.userservice.exceptions.RoleExceptions.RoleAlreadyExistUserException;
import net.arkx.userservice.exceptions.RoleExceptions.RoleNotFoundException;
import net.arkx.userservice.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    // Create Role
    public Role createRole(Role newRole){
        if (roleRepository.findByName(newRole.getName()) != null) {
            throw new RoleAlreadyExistUserException("This role " + newRole.getName() + "  already exist ");
        }
        return roleRepository.save(newRole);
    }
    //Read all Roles
    public List<Role> getAllRoles(){
        return roleRepository.findAll();
    }
    //Read Role by id
    public Role getRoleById(Long id){
        Role role = roleRepository.findById(id).orElse(null);
        if(role != null){
            return role;
        }else {
            throw new RoleNotFoundException("Can't find this Role with Id: "+id);
        }
    }
    //Read Role by name
    public Role getRoleByName(String name){
        return roleRepository.findByName(name);
    }
    // Update Role

    //Delete Role by name
    public void deleteRoleByName(String name){
        Role role = roleRepository.findByName(name);
        roleRepository.delete(role);
    }
    //Delete Role by id
    public void deleteRoleById(Long id){
        Role role = roleRepository.findById(id).orElse(null);
        if(role!=null) {
            roleRepository.delete(role);
        }
    }

}
