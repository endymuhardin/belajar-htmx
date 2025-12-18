package com.muhardin.endy.belajar.htmx.controller;

import com.muhardin.endy.belajar.htmx.model.Note;
import com.muhardin.endy.belajar.htmx.repository.NoteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NoteController {

    private final NoteRepository noteRepository;
    private static final int PAGE_SIZE = 10;

    public NoteController(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @GetMapping("/notes")
    public String notesArchive() {
        return "notes-archive";
    }

    @GetMapping("/notes/page")
    public String getNotesPage(
            @RequestParam(defaultValue = "0") int page,
            Model model) throws InterruptedException {

        // Simulate network delay for learning purposes (skip for initial page)
        if (page > 0) {
            Thread.sleep(1500); // 1.5 second delay to show loading state
        }

        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        Page<Note> notesPage = noteRepository.findAllByOrderByPinnedDescCreatedAtDesc(pageable);

        model.addAttribute("notes", notesPage.getContent());
        model.addAttribute("hasMore", notesPage.hasNext());
        model.addAttribute("nextPage", page + 1);
        model.addAttribute("currentPage", page);

        return "fragments/notes-page";
    }
}
