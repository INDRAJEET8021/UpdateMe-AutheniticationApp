package com.captcha.login.controllers;

import cn.apiclub.captcha.Captcha;
import com.captcha.login.models.CaptchaSettings;
import com.captcha.login.models.LoginDto;
import com.captcha.login.models.UserDto;
import com.captcha.login.services.CaptchaGenerator;
import com.captcha.login.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class FormController {

    @Autowired
    private UserService userService;
    @Autowired
    private HttpSession httpSession;

    @GetMapping
    public String showLoginPage(Model model) {
        model.addAttribute("loginForm", new LoginDto());
        model.addAttribute("captchaSettings", genCaptcha());
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute UserDto userDto, Model model) {
        userService.addUser(userDto); // Register the user
        model.addAttribute("success",true);
        httpSession.setAttribute("userName",userDto.getName());
        return null;
    }

    @PostMapping("/")
    public String login(@ModelAttribute LoginDto loginDto, @ModelAttribute CaptchaSettings captchaSettings, Model model) {
        if (!captchaSettings.getCaptcha().equals(captchaSettings.getHiddenCaptcha())) {
            model.addAttribute("message", "Invalid Captcha");
            model.addAttribute("captchaSettings", genCaptcha());
            return "login";
        }

        String loginResponse = userService.loginUser(loginDto);
        if (loginResponse.equals("Login successful!")) {
            UserDto userDto=userService.loadUserDetailsByEmail(loginDto.getEmail());
            if (userDto != null) {
                loginDto.setName(userDto.getName());
                httpSession.setAttribute("loginDto", loginDto);
            }
            return "redirect:/success";

        } else {
            model.addAttribute("message", loginResponse);
            model.addAttribute("captchaSettings", genCaptcha());
            return "login";
        }
    }

    @Value("${newsapi.apiKey}")
    private String apiKey;
    @GetMapping("/success")
    public String showSuccessPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        LoginDto loginDto=(LoginDto) httpSession.getAttribute("loginDto");

        String newsUrl = "https://newsapi.org/v2/top-headlines?country=in&apiKey=" + apiKey;
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        ResponseEntity<Map> response = restTemplate.getForEntity(newsUrl, Map.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            List<Map<String, Object>> articles = (List<Map<String, Object>>) response.getBody().get("articles");
            model.addAttribute("newsData", articles);
        } else {
            model.addAttribute("newsData", null);
        }

        model.addAttribute("username", loginDto.getName());

        return "success";
    }

    private CaptchaSettings genCaptcha() {
        CaptchaSettings captchaSettings = new CaptchaSettings();
        Captcha captcha = CaptchaGenerator.generateCaptcha(260, 45);
        captchaSettings.setHiddenCaptcha(captcha.getAnswer());
        captchaSettings.setCaptcha("");
        captchaSettings.setRealCaptcha(CaptchaGenerator.encodeCaptchaToBinary(captcha));
        return captchaSettings;
    }
    @GetMapping("/contact")
    public String showContactPage(){
        return "contact";
    }
    @GetMapping("/about")
    public String showAboutPage(){
        return "about";
    }
}
