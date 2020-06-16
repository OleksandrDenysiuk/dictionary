package com.portfolio.dictionary.controller;

import com.portfolio.dictionary.model.AccountDetails;
import com.portfolio.dictionary.service.TestTypeService;
import com.portfolio.dictionary.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class TestController {
    private final TestTypeService testTypeService;
    private final UserService userService;

    public TestController(TestTypeService testTypeService, UserService userService) {
        this.testTypeService = testTypeService;
        this.userService = userService;
    }

    @GetMapping("/tests")
    public String getForm(@AuthenticationPrincipal AccountDetails details,
                          Model model) {
        model.addAttribute("user", userService.findById(details.getUserId()));
        model.addAttribute("types", testTypeService.getAll());
        return "test/testForm";
    }

    @GetMapping("/tests/start")
    public String process(@AuthenticationPrincipal AccountDetails details,
                          Model model) {
        model.addAttribute("user", userService.findById(details.getUserId()));
        return "test/index";
    }

    @GetMapping("/tests/results/{resultId}")
    public String result(@AuthenticationPrincipal AccountDetails details,
                         @PathVariable Long resultId,
                         Model model) {
        model.addAttribute("user", userService.findById(details.getUserId()));
        model.addAttribute("resultId", resultId);
        return "test/result";
    }
}
