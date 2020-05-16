package com.portfolio.dictionary.controller;


import com.portfolio.dictionary.dto.UserDto;
import com.portfolio.dictionary.mapper.UserMapper;
import com.portfolio.dictionary.model.AccountDetails;
import com.portfolio.dictionary.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    private final UserService userService;

    public IndexController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String welcome(@AuthenticationPrincipal AccountDetails details,
                          Model model) {
        UserDto userDto = UserMapper.INSTANCE.toDto(userService.findByUsername(details.getUsername()));

        model.addAttribute("user", userDto);
        return "index";
    }
}
