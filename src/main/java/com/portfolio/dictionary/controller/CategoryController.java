package com.portfolio.dictionary.controller;

import com.portfolio.dictionary.dto.CategoryDto;
import com.portfolio.dictionary.dto.WordDto;
import com.portfolio.dictionary.mapper.UserMapper;
import com.portfolio.dictionary.model.AccountDetails;
import com.portfolio.dictionary.model.User;
import com.portfolio.dictionary.service.CategoryService;
import com.portfolio.dictionary.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CategoryController {
    private final CategoryService categoryService;
    private final UserService userService;

    public CategoryController(CategoryService categoryService, UserService userService) {
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @GetMapping("/category/{id}")
    public String getOne(@AuthenticationPrincipal AccountDetails accountDetails,
                         @PathVariable("id")String categoryId, Model model){
        User user = userService.findByUsername(accountDetails.getUsername());
        CategoryDto categoryDto = categoryService.findByCategoryIdAndUserId(Long.valueOf(categoryId), user.getId());
        model.addAttribute("user", UserMapper.INSTANCE.toDto(user));
        model.addAttribute("category", categoryDto);
        model.addAttribute("wordForm", new WordDto());
        return "category/index";
    }

    @GetMapping("/category/{id}/delete")
    public String delete(@AuthenticationPrincipal AccountDetails accountDetails,
                         @PathVariable("id") String id){
        User user = userService.findByUsername(accountDetails.getUsername());
        categoryService.delete(Long.valueOf(id), user.getId());
        return "redirect:/";
    }

    @PostMapping("/category/{id}/update")
    public String update(@AuthenticationPrincipal AccountDetails accountDetails,
                         @PathVariable("id")String categoryId,
                         @RequestParam("categoryName") String name){
        User user = userService.findByUsername(accountDetails.getUsername());
        categoryService.update(name,Long.valueOf(categoryId), user.getId());
        return "redirect:/category/" + categoryId;
    }


    @PostMapping("/category/create")
    public String create(@AuthenticationPrincipal AccountDetails details,
                         @RequestParam("categoryName") String name){
        categoryService.create(name,userService.findByUsername(details.getUsername()).getId());
        return "redirect:/";
    }
    @GetMapping("/api/user/{user}/category/{category}")
    public @ResponseBody CategoryDto getUserDto(@PathVariable("user") String userId,
                       @PathVariable("category") String categoryId) {
        CategoryDto categoryDto = categoryService.findByCategoryIdAndUserId(Long.valueOf(categoryId), Long.valueOf(userId));
        return categoryDto;
    }
}
