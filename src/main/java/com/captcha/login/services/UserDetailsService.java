package com.captcha.login.services;

import com.captcha.login.models.LoginDto;
import com.captcha.login.models.UserDto;
import org.springframework.stereotype.Service;

@Service

public interface UserDetailsService {
    String addUser(UserDto userDto);
    String loginUser(LoginDto loginDto);

}
