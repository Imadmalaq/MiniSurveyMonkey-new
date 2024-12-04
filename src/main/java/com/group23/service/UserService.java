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
    /**
     * The repository to save users.
     */
    private final UserRepository userRepository;

    /**
     * The repository to save roles (admin and user).
     */
    private final RoleRepository roleRepository;

    /**
     * Security configuration for managing authentication and authorization settings.
     */
    private SecurityConfig securityConfig;

    /**
     * The username for the admin account, loaded from the application properties.
     */
    @Value("${spring.security.user.name}")
    private String adminUsername;

    /**
     * The password for the admin account, loaded from the application properties.
     */
    @Value("${spring.security.user.password}")
    private String adminPassword;

    /**
     * Constructs a UserService instance with the required repositories.
     *
     * @param userRepository the repository for managing user data
     * @param roleRepository the repository for managing role data
     */
    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.securityConfig = securityConfig;
    }

    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Saves the user into the repository
     * @param user User to be saved
     * @return The User
     */
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Checks if the username and password are correct.
     * @param username Username of admin or user
     * @param password Password of admin or user
     * @return Returns true if username and password match, false otherwise
     */
    public boolean validateAdminCredentials(String username, String password) {
        return adminUsername.equals(username)
                && adminPassword.equals(password);
    }
}
