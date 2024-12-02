package com.group23.service;

import com.group23.auth.SecurityConfig;
import com.group23.model.Role;
import com.group23.model.User;
import com.group23.repository.RoleRepository;
import com.group23.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private SecurityConfig securityConfig;

    @Value("${spring.security.user.name}")
    private String adminUsername;

    @Value("${spring.security.user.password}")
    private String adminPassword;


    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.securityConfig = securityConfig;
    }

    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public Role findRoleByName(String name) {
        return roleRepository.findByName(name);
    }

    public String getAdminUsername() {
        return adminUsername;
    }

    // Avoid exposing password programmatically for security reasons
    public boolean validateAdminCredentials(String username, String password) {
        return adminUsername.equals(username)
                && adminPassword.equals(password);
    }
}
