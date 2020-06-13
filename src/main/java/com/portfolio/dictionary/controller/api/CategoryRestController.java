package com.portfolio.dictionary.controller.api;

import com.portfolio.dictionary.dto.CategoryDto;
import com.portfolio.dictionary.model.AccountDetails;
import com.portfolio.dictionary.service.CategoryService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryRestController {

    private final CategoryService categoryService;

    public CategoryRestController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public List<CategoryDto> getCategories(@AuthenticationPrincipal AccountDetails userDetails){
        return categoryService.findAll(userDetails.getUserId());
    }

    @GetMapping("/categories/{categoryId}")
    public CategoryDto getCategory(@AuthenticationPrincipal AccountDetails accountDetails,
                                         @PathVariable Long categoryId){
        return categoryService.findByCategoryIdAndUserId(categoryId, accountDetails.getUserId());
    }

    @PostMapping("/categories")
    public CategoryDto create(@AuthenticationPrincipal AccountDetails accountDetails,
                              @RequestParam String name){
        return categoryService.create(name, accountDetails.getUserId());
    }

    @PutMapping("/categories/{categoryId}")
    public CategoryDto update(@AuthenticationPrincipal AccountDetails accountDetails,
                              @PathVariable Long categoryId,
                              @RequestParam String name){
        return categoryService.update(name,categoryId,accountDetails.getUserId());
    }

    @DeleteMapping("/categories/{categoryId}")
    public void delete(@AuthenticationPrincipal AccountDetails accountDetails,
                       @PathVariable Long categoryId){
        categoryService.delete(categoryId,accountDetails.getUserId());
    }
}
