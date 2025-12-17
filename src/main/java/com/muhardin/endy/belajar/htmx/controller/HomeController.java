package com.muhardin.endy.belajar.htmx.controller;

import com.muhardin.endy.belajar.htmx.dto.ContactForm;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Controller
public class HomeController {

    private static final List<String> QUOTES = List.of(
            "Kesuksesan adalah jumlah dari usaha kecil yang diulang hari demi hari.",
            "Hal terbaik untuk memulai adalah berhenti berbicara dan mulai melakukan.",
            "Jangan menunda sampai besok apa yang bisa kamu lakukan hari ini.",
            "Kegagalan adalah kesuksesan yang tertunda.",
            "Belajar dari kemarin, hidup untuk hari ini, berharap untuk besok."
    );

    private final Random random = new Random();

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

    @GetMapping("/quote")
    public String quote(Model model) {
        String selectedQuote = QUOTES.get(random.nextInt(QUOTES.size()));
        model.addAttribute("quote", selectedQuote);
        model.addAttribute("loadTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        return "fragments/quote-widget";
    }

    @GetMapping("/time")
    public String time(Model model) {
        LocalDateTime now = LocalDateTime.now();
        model.addAttribute("currentTime", now.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        model.addAttribute("currentDate", now.format(DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy")));
        return "fragments/time-widget";
    }

    @GetMapping("/weather")
    public String weather(Model model) {
        // Simulasi data cuaca
        int temperature = 25 + random.nextInt(10); // 25-34 derajat
        String[] conditions = {"Cerah", "Berawan", "Mendung", "Hujan Ringan"};
        String condition = conditions[random.nextInt(conditions.length)];
        int humidity = 60 + random.nextInt(30); // 60-89%

        model.addAttribute("temperature", temperature);
        model.addAttribute("condition", condition);
        model.addAttribute("humidity", humidity);
        model.addAttribute("updateTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));

        return "fragments/weather-widget";
    }

    @GetMapping("/contact-form")
    public String contactForm() {
        return "fragments/contact-form";
    }

    @PostMapping("/contact")
    public String submitContact(@Valid ContactForm form, BindingResult bindingResult, Model model) {
        // Jika ada validation errors, return form dengan errors
        if (bindingResult.hasErrors()) {
            // Convert BindingResult errors ke Map untuk kemudahan akses di template
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
            );
            model.addAttribute("errors", errors);
            model.addAttribute("form", form);
            return "fragments/contact-form";
        }

        // Jika validasi berhasil, return success message
        model.addAttribute("name", form.getName());
        model.addAttribute("submittedAt", LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        return "fragments/contact-success";
    }
}
