package com.usermodule;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;  // Injecting BCryptPasswordEncoder

    // Register a new user
    public User registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists!"); // Throw custom exception
        }
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword())); // Encrypt the password
        return userRepository.save(user);
    }
    // User login
    public LoginResponse loginUser(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> 
            new RuntimeException("Invalid email or password"));
        
        // Check if the password matches the encoded password in the database
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        // Return a new LoginResponse with a success message and the user object
        return new LoginResponse("Login successful", user);
    }

    // Update user details
    public User updateUser(Long id, User updatedUser) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        if (!passwordEncoder.matches(updatedUser.getPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));  // Encrypt if password changed
        }
        return userRepository.save(user);
    }

    // Delete user
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }
    
    public List<User> getallusers(){
    	return userRepository.findAll();
    }
}
