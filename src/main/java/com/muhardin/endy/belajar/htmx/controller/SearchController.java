package com.muhardin.endy.belajar.htmx.controller;

import com.muhardin.endy.belajar.htmx.service.SearchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/search")
    public String search(@RequestParam(required = false) String q, Model model) {
        if (q == null || q.trim().isEmpty()) {
            model.addAttribute("results", List.of());
            model.addAttribute("query", "");
            model.addAttribute("isEmpty", true);
            return "fragments/search-results";
        }

        List<SearchService.SearchResult> results = searchService.search(q);
        model.addAttribute("results", results);
        model.addAttribute("query", q);
        model.addAttribute("isEmpty", results.isEmpty());

        return "fragments/search-results";
    }
}
