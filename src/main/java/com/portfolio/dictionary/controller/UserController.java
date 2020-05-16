package com.portfolio.dictionary.controller;

import com.portfolio.dictionary.dto.UserDto;
import com.portfolio.dictionary.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new UserDto());
        return "authentication/registrationForm";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("user") @Valid UserDto user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "authentication/registrationForm";
        }
        userService.save(user);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {

        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "authentication/loginForm";
    }

}
