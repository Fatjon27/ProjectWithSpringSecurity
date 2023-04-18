package com.example.demo.services;

import com.example.demo.entities.Role;
import com.example.demo.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;
    public List<Role> findAll(){
        return roleRepository.findAll();
    }
    public void createRole(Role role){
        roleRepository.save(role);
    }
    public void updateRole(Role role){
        roleRepository.save(role);
    }
    public Role findByName(String name){
        return roleRepository.findByName(name);
    }
    public void deleteById(Long id){
        roleRepository.deleteById(id);
    }
}
