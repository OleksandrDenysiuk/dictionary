package com.portfolio.dictionary.controller.api;

import com.portfolio.dictionary.command.WordCommand;
import com.portfolio.dictionary.dto.WordDto;
import com.portfolio.dictionary.model.AccountDetails;
import com.portfolio.dictionary.service.WordService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class WordRestController {
    private final WordService wordService;

    public WordRestController(WordService wordService) {
        this.wordService = wordService;
    }

    @GetMapping("/categories/{categoryId}/words")
    public List<WordDto> getWords(@AuthenticationPrincipal AccountDetails accountDetails,
                                 @PathVariable Long categoryId){
        return wordService.findAll(categoryId, accountDetails.getUserId());
    }

    @GetMapping("/categories/{categoryId}/words/{wordId}")
    public WordDto getWords(@AuthenticationPrincipal AccountDetails accountDetails,
                                 @PathVariable Long categoryId,
                                  @PathVariable Long wordId){
        return wordService.findOne(wordId, categoryId, accountDetails.getUserId());
    }

    @PostMapping("/categories/{categoryId}/words")
    public WordDto create(@AuthenticationPrincipal AccountDetails accountDetails,
                          @PathVariable Long categoryId,
                          @RequestBody WordCommand wordCommand){
        wordCommand.setCategoryId(categoryId);
        return wordService.create(wordCommand, accountDetails.getUserId());
    }

    @PutMapping("/categories/{categoryId}/words/{wordId}")
    public WordDto update(@AuthenticationPrincipal AccountDetails accountDetails,
                          @PathVariable Long wordId,
                          @PathVariable Long categoryId,
                          @RequestBody WordCommand wordCommand){
        wordCommand.setId(wordId);
        wordCommand.setCategoryId(categoryId);
        return wordService.update(wordCommand, accountDetails.getUserId());
    }

    @DeleteMapping("/categories/{categoryId}/words/{wordId}")
    public void delete(@AuthenticationPrincipal AccountDetails accountDetails,
                       @PathVariable Long wordId,
                       @PathVariable Long categoryId){
        wordService.delete(wordId, categoryId, accountDetails.getUserId());
    }
}
