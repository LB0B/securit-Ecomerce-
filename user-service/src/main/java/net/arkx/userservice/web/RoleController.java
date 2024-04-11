package net.arkx.userservice.web;

import net.arkx.userservice.entities.Role;
import net.arkx.userservice.service.RoleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @RequestMapping("/roles")
public class RoleController {
    public final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    //Create Role
    @PostMapping("/createRole")
    public Role createRole(@RequestParam String newRole){
        return roleService.createRole(newRole);
    }
    //Get All Roles
    @GetMapping
    public List<Role> getRoles(){
        return roleService.getAllRoles();
    }
    //Get Role By Name
    @GetMapping("/roleByName")
    public Role getRoleByName(@RequestBody String name){
        return roleService.getRoleByName(name);
    }
    // Get Role By Id
    public Role getRoleById(@PathVariable Long id){
        return roleService.getRoleById(id);
    }
    //Delete Role by name
    @DeleteMapping("deleteRole/{name}")
    public void deleteRoleByName(@PathVariable String name){
        roleService.deleteRoleByName(name);
    }
    @DeleteMapping("DeleteById/{id}")
    public void deleteRoleById(@PathVariable Long id){
        roleService.deleteRoleById(id);
    }
}
