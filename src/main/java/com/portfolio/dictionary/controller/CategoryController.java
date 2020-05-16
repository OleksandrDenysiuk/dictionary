package com.portfolio.dictionary.controller;

import com.portfolio.dictionary.model.AccountDetails;
import com.portfolio.dictionary.service.CategoryService;
import com.portfolio.dictionary.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CategoryController {
    private final CategoryService categoryService;
    private final UserService userService;

    public CategoryController(CategoryService categoryService, UserService userService) {
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @PostMapping("/category/create")
    public String create(@AuthenticationPrincipal AccountDetails details,
                         @RequestParam("categoryName") String name){

        categoryService.create(name,userService.findByUsername(details.getUsername()).getId());
        return "redirect:/";
    }
}
