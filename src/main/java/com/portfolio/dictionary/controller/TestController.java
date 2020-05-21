package com.portfolio.dictionary.controller;

import com.portfolio.dictionary.model.AccountDetails;
import com.portfolio.dictionary.model.Step;
import com.portfolio.dictionary.model.Test;
import com.portfolio.dictionary.model.step.ChooseAnswerStep;
import com.portfolio.dictionary.service.CategoryService;
import com.portfolio.dictionary.service.TestService;
import com.portfolio.dictionary.service.TestTypeService;
import com.portfolio.dictionary.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@SessionAttributes("test")
public class TestController {
    private final CategoryService categoryService;
    private final TestService testService;
    private final TestTypeService testTypeService;
    private final UserService userService;

    public TestController(CategoryService categoryService, TestService testService, TestTypeService testTypeService, UserService userService) {
        this.categoryService = categoryService;
        this.testService = testService;
        this.testTypeService = testTypeService;
        this.userService = userService;
    }

    @ModelAttribute("test")
    public Test getSteps() {
        return new Test();
    }

    @GetMapping("/test")
    public String getForm(@AuthenticationPrincipal AccountDetails details,
                          Model model) {
        model.addAttribute("user", userService.findById(details.getUserId()));
        model.addAttribute("types", testTypeService.getAll());
        return "test/testForm";
    }

    @PostMapping("/test/start")
    public String generate(@AuthenticationPrincipal AccountDetails details,
                           @RequestParam("type") String testTypeName,
                           @RequestParam("categoriesId") List<String> ids,
                           @ModelAttribute("test") Test test,
                           Model model) {
        model.addAttribute("user", userService.findById(details.getUserId()));
        Test testGenerated = testService.generate(
                testTypeService.findByName(testTypeName),
                categoryService.getListByIdAndUserId(ids, details.getUserId()));
        test.setType(testGenerated.getType());
        test.setSteps(testGenerated.getSteps());
        return "test/tests/chooseAnswer";
    }

    @GetMapping("/test/steps/get")
    public @ResponseBody
    List<? extends Step> getSteps(@ModelAttribute("test") Test test) {
        return test.getSteps();
    }

    @GetMapping("/test/step/{number}/answer/{answer}")
    public @ResponseBody
    boolean setAnswer(@PathVariable("number")int stepNumber,
                 @PathVariable("answer") String answer,
                 @ModelAttribute("test") Test test) {
        ChooseAnswerStep step = (ChooseAnswerStep) test.getSteps().get(stepNumber);
        step.setAnswer(answer);
        return step.getAnswer().equals(answer);
    }
}
