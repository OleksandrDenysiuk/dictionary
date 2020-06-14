package com.portfolio.dictionary.controller;

import com.portfolio.dictionary.dto.UserDto;
import com.portfolio.dictionary.model.AccountDetails;
import com.portfolio.dictionary.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CategoryController {
    private final UserService userService;

    public CategoryController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/category/{id}")
    public String getOne(@AuthenticationPrincipal AccountDetails accountDetails,
                         @PathVariable("id")String categoryId, Model model){
        UserDto user = userService.findByUsername(accountDetails.getUsername());
        model.addAttribute("user", user);
        model.addAttribute("categoryId", categoryId);

        return "category/index";
    }
}
