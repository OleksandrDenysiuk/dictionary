package com.portfolio.dictionary.controller.api;

import com.portfolio.dictionary.model.AccountDetails;
import com.portfolio.dictionary.model.Test;
import com.portfolio.dictionary.service.CategoryService;
import com.portfolio.dictionary.service.TestService;
import com.portfolio.dictionary.service.TestTypeService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TestRestController {

    private final TestService testService;
    private final TestTypeService testTypeService;
    private final CategoryService categoryService;

    public TestRestController(TestService testService, TestTypeService testTypeService, CategoryService categoryService) {
        this.testService = testService;
        this.testTypeService = testTypeService;
        this.categoryService = categoryService;
    }

    @PostMapping("/tests")
    public Test generate(@AuthenticationPrincipal AccountDetails details,
                         @RequestParam("type") String testTypeName,
                         @RequestParam("categoriesId") List<String> ids){
        return testService.generate(testTypeService.findByName(testTypeName),
                categoryService.getListByIdAndUserId(ids, details.getUserId()));
    }
}
