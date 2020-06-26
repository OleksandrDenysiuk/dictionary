package com.portfolio.dictionary.controller.api;

import com.portfolio.dictionary.dto.UserDto;
import com.portfolio.dictionary.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {
    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/users/{userId}")
    public @ResponseBody
    UserDto getOne(@PathVariable String userId) {
        return userService.getOneById(Long.valueOf(userId));
    }
}
