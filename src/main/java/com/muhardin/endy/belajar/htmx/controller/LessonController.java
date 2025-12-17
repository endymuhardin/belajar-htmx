package com.muhardin.endy.belajar.htmx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/lesson")
public class LessonController {

    @GetMapping("/{lessonNumber}")
    public String showLesson(@PathVariable int lessonNumber, Model model) {
        model.addAttribute("lessonNumber", lessonNumber);
        return "lesson-" + lessonNumber;
    }
}
