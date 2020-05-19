package com.portfolio.dictionary.controller;

import com.portfolio.dictionary.dto.UserDto;
import com.portfolio.dictionary.dto.WordDto;
import com.portfolio.dictionary.model.AccountDetails;
import com.portfolio.dictionary.service.UserService;
import com.portfolio.dictionary.service.WordService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WordController {
    private final WordService wordService;
    private final UserService userService;

    public WordController(WordService wordService, UserService userService) {
        this.wordService = wordService;
        this.userService = userService;
    }


    @GetMapping("/category/{categoryId}/word/{id}/delete")
    public String delete(@AuthenticationPrincipal AccountDetails details,
                         @PathVariable("categoryId")String categoryId,
                         @ModelAttribute WordDto wordDto){
        UserDto user = userService.findByUsername(details.getUsername());

        wordDto.setCategoryId(Long.valueOf(categoryId));

        wordService.delete(wordDto,user.getId());

        return "redirect:/category/" + categoryId;
    }

    @PostMapping("/category/{categoryId}/word/create")
    public String create(@AuthenticationPrincipal AccountDetails details,
                         @PathVariable("categoryId")String categoryId,
                         @ModelAttribute WordDto wordDto){

        UserDto user = userService.findByUsername(details.getUsername());

        wordDto.setCategoryId(Long.valueOf(categoryId));

        wordService.save(wordDto,user.getId());

        return "redirect:/category/" + categoryId;
    }

    @PostMapping("/category/{categoryId}/word/{id}/update")
    public String update(@AuthenticationPrincipal AccountDetails details,
                         @PathVariable("categoryId")String categoryId,
                         @ModelAttribute WordDto wordDto){

        UserDto user = userService.findByUsername(details.getUsername());

        wordDto.setCategoryId(Long.valueOf(categoryId));

        wordService.update(wordDto,user.getId());

        return "redirect:/category/" + categoryId;
    }
}
