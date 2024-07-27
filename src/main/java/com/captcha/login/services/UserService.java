package com.captcha.login.services;

import com.captcha.login.models.LoginDto;
import com.captcha.login.models.User;
import com.captcha.login.models.UserDto;
import com.captcha.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }

    public String addUser(UserDto userDto) {
        User user = new User(
                userDto.getName(),
                userDto.getEmail(),
                userDto.getAddress(),
                userDto.getNumber(),
                passwordEncoder.encode(userDto.getPassword())
        );
        userRepository.save(user);
        return user.getEmail();
    }

    public String loginUser(LoginDto loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail());
        if (user != null) {
            String password = loginDto.getPassword();
            String encodedPassword = user.getPassword();
            boolean isPasswordRight = passwordEncoder.matches(password, encodedPassword);
            if (isPasswordRight) {
                return "Login successful!";
            } else {
                return "Incorrect password. Please try again.";
            }
        } else {
            return "User not found. Please register.";
        }
    }
    // Add this method to retrieve user details including name
    public UserDto loadUserDetailsByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            UserDto userDto = new UserDto();
            userDto.setEmail(user.getEmail());
            userDto.setName(user.getName());
            userDto.setAddress(user.getAddress());
            userDto.setNumber(user.getNumber());
            return userDto;
        }
        return null; // Or throw an exception
    }
}
