package com.portfolio.dictionary.controller.api;

import com.portfolio.dictionary.dto.CategoryDto;
import com.portfolio.dictionary.model.AccountDetails;
import com.portfolio.dictionary.service.CategoryService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    private static CategoryService categoryService;

    public static void setCategoryService(CategoryService categoryService) {
        CategoryController.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public List<CategoryDto> getCategories(@AuthenticationPrincipal AccountDetails userDetails){
        return categoryService.findAll(userDetails.getUserId());
    }
}
