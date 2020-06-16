package com.portfolio.dictionary.controller.api;

import com.portfolio.dictionary.command.ResultCommand;
import com.portfolio.dictionary.dto.ResultDto;
import com.portfolio.dictionary.model.AccountDetails;
import com.portfolio.dictionary.service.ResultService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ResultRestController {

    private final ResultService resultService;

    public ResultRestController(ResultService resultService) {
        this.resultService = resultService;
    }

    @GetMapping("/results/{resultId}")
    public ResultDto getOne(@AuthenticationPrincipal AccountDetails details,
                            @PathVariable Long resultId){
        return resultService.getOne(resultId,details.getUserId());
    }

    @PostMapping("/results")
    public ResultDto create(@AuthenticationPrincipal AccountDetails details,
            @RequestBody ResultCommand resultCommand){
        return resultService.create(resultCommand, details.getUserId());
    }
}
