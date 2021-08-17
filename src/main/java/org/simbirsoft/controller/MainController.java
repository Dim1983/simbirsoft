package org.simbirsoft.controller;

import lombok.extern.slf4j.Slf4j;
import org.simbirsoft.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
@Slf4j
public class MainController {
    private final QueryService queryService;

    @Autowired
    public MainController(QueryService queryService) {
        this.queryService = queryService;
    }

    @GetMapping
    public String getIndex() {
        log.error("This is an error log entry");

        return "index";
    }

    @GetMapping(path = "/create")
    public String create(@RequestParam(name = "link") String link, Model model) {
        log.error("This is an error log entry");

        queryService.insertQuery(link);
        model.addAttribute("lists", queryService.getQueryByLink(link));

        return "result";
    }
}
