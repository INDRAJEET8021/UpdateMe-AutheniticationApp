package com.captcha.login.controllers;

import com.captcha.login.models.LoginDto;
import com.captcha.login.models.UserDto;
import com.captcha.login.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class userController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String register(@RequestBody UserDto userDto){
        String id=userService.addUser(userDto);
     return id;
    }
    @PostMapping("/login")
    public String login(@RequestBody LoginDto loginDto){
        String log=userService.loginUser(loginDto);
        return log;
    }
}
