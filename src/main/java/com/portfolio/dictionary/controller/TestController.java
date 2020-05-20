package com.portfolio.dictionary.controller;

import com.portfolio.dictionary.model.AccountDetails;
import com.portfolio.dictionary.service.CategoryService;
import com.portfolio.dictionary.service.TestTypeService;
import com.portfolio.dictionary.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TestController {
    private final CategoryService categoryService;
    private final TestTypeService testTypeService;
    private final UserService userService;

    public TestController(CategoryService categoryService, TestTypeService testTypeService, UserService userService) {
        this.categoryService = categoryService;
        this.testTypeService = testTypeService;
        this.userService = userService;
    }

    @GetMapping("/test")
    public String getForm(@AuthenticationPrincipal AccountDetails details,
                          Model model){
        model.addAttribute("user", userService.findById(details.getUserId()));
        model.addAttribute("types", testTypeService.getAll());
        return "test/testForm";
    }

    @PostMapping("/test/start")
    public String generate(@AuthenticationPrincipal AccountDetails details,
                           @RequestParam("type") String testType,
                           @RequestParam("categoriesId") List<String> ids,
                           Model model) {
        model.addAttribute("user", userService.findById(details.getUserId()));
        return "test/index";
    }
}
