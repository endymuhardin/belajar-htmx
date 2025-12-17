package com.muhardin.endy.belajar.htmx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class HomeController {

    @GetMapping("/")
    public String dashboard() {
        return "dashboard";
    }

    @GetMapping("/stats")
    public String stats(Model model) {
        model.addAttribute("currentTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        model.addAttribute("userCount", 42);
        model.addAttribute("taskCount", 127);
        model.addAttribute("completionRate", 73);
        return "fragments/stats-widget";
    }
}
